package com.besafx.app.rest;

import com.besafx.app.async.TransactionalService;
import com.besafx.app.auditing.PersonAwareUserDetails;
import com.besafx.app.config.CustomException;
import com.besafx.app.entity.*;
import com.besafx.app.entity.projection.BankTransactionAmount;
import com.besafx.app.init.Initializer;
import com.besafx.app.search.SellerSearch;
import com.besafx.app.service.*;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationDegree;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/seller/")
public class SellerRest {

    private final static Logger LOG = LoggerFactory.getLogger(SellerRest.class);

    private final String FILTER_TABLE = "" +
            "**," +
            "-productPurchases," +
            "-contracts," +
            "-bankTransactions," +
            "seller[id]";

    private final String FILTER_DETAILS = "" +
            "**," +
            "productPurchases[**,product[id,name],-bankTransaction,-seller,-contractProducts,person[id,contact[id,shortName]]]," +
            "-contracts," +
            "-bankTransactions," +
            "seller[id]";

    private final String FILTER_COMBO = "" +
            "id," +
            "code," +
            "contact[id,shortName,mobile]";

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerSearch sellerSearch;

    @Autowired
    private ContactService contactService;

    @Autowired
    private BankTransactionService bankTransactionService;

    @Autowired
    private ContractProductService contractProductService;

    @Autowired
    private ContractPaymentService contractPaymentService;

    @Autowired
    private ContractPremiumService contractPremiumService;

    @Autowired
    private ProductPurchaseService productPurchaseService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private TransactionalService transactionalService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping(value = "create/{openCash}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SELLER_CREATE')")
    @Transactional
    public String create(@PathVariable(value = "openCash") Double openCash, @RequestBody Seller seller) {
        Seller topSeller = sellerService.findTopByOrderByCodeDesc();
        if (topSeller == null) {
            seller.setCode(1);
        } else {
            seller.setCode(topSeller.getCode() + 1);
        }
        Person caller = ((PersonAwareUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson();
        seller.setSeller(caller.getCompany().getSeller());
        seller.setRegisterDate(new DateTime().toDate());
        seller.setEnabled(true);
        seller.setContact(contactService.save(seller.getContact()));
        seller = sellerService.save(seller);
        notificationService.notifyAll(Notification
                                              .builder()
                                              .message("تم انشاء حساب مستثمر جديد بنجاح")
                                              .type(NotificationDegree.success).build());
        LOG.info("إنشاء الرصيد الافتتاحي وعمل عملية إيداع بالمبلغ");
        if (openCash > 0) {
            BankTransaction bankTransaction = new BankTransaction();
            bankTransaction.setAmount(openCash);
            bankTransaction.setBank(Initializer.bank);
            bankTransaction.setSeller(seller);
            bankTransaction.setTransactionType(Initializer.transactionTypeDeposit);
            bankTransaction.setDate(new DateTime().toDate());
            bankTransaction.setPerson(caller);
            StringBuilder builder = new StringBuilder();
            builder.append("إيداع مبلغ نقدي بقيمة ");
            builder.append(openCash);
            builder.append("ريال سعودي، ");
            builder.append(" لـ / ");
            builder.append(seller.getShortName());
            builder.append("، رصيد افتتاحي");
            bankTransaction.setNote(builder.toString());
            bankTransactionService.save(bankTransaction);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message(builder.toString())
                                                  .type(NotificationDegree.success).build());
        }
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), seller);
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SELLER_UPDATE')")
    @Transactional
    public String update(@RequestBody Seller seller) {
        if (sellerService.findByCodeAndIdIsNot(seller.getCode(), seller.getId()) != null) {
            throw new CustomException("هذا الكود مستخدم سابقاً، فضلاً قم بتغير الكود.");
        }
        Seller object = sellerService.findOne(seller.getId());
        if (object != null) {
            seller.setContact(contactService.save(seller.getContact()));
            seller = sellerService.save(seller);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم تعديل بيانات المستثمر بنجاح")
                                                  .type(NotificationDegree.success).build());
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), seller);
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SELLER_DELETE')")
    @Transactional
    public void delete(@PathVariable Long id) {
        Seller seller = sellerService.findOne(id);
        if (seller != null) {

            LOG.info("رفض العملية فى حال كان هو المستثمر الرئيسي");
            if (Initializer.company.getSeller().getId().equals(seller.getId())) {
                throw new CustomException("لا يمكن حذف المستثمر الرئيسي للبرنامج");
            }

            LOG.info("حذف كل سلع العقود");
            contractProductService.delete(seller.getContracts()
                                                .stream()
                                                .flatMap(contract -> contract.getContractProducts().stream())
                                                .collect(Collectors.toList()));

            LOG.info("حذف كل معاملات البنك لدفعات العقود");
            bankTransactionService.delete(
                    seller.getContracts()
                          .stream()
                          .flatMap(contract -> contract.getContractPayments().stream())
                          .map(ContractPayment::getBankTransaction)
                          .collect(Collectors.toList()));

            LOG.info("حذف كل دفعات العقود");
            contractPaymentService.delete(seller.getContracts()
                                                .stream()
                                                .flatMap(contract -> contract.getContractPayments().stream())
                                                .collect(Collectors.toList()));

            LOG.info("حذف كل أقساط العقود");
            contractPremiumService.delete(seller.getContracts()
                                                .stream()
                                                .flatMap(contract -> contract.getContractPremiums().stream())
                                                .collect(Collectors.toList()));

            LOG.info("حذف العقود");
            contractService.delete(seller.getContracts());

            LOG.info("حذف حركات الشراء لهذا المستثمر");
            bankTransactionService.delete(seller.getProductPurchases()
                                                .stream()
                                                .map(ProductPurchase::getBankTransaction)
                                                .collect(Collectors.toList()));

            LOG.info("حذف المشتريات لهذا المستثمر");
            productPurchaseService.delete(seller.getProductPurchases());

            LOG.info("تفريغ كل المعاملات المالية لهذا المستثمر");
            transactionalService.setBankTransactionsSellerToNull(seller);

            LOG.info("حذف بيانات الاتصال");
            contactService.delete(seller.getContact());

            LOG.info("حذف المستثمر");
            sellerService.delete(seller);

            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم حذف المستثمر وكل ما يتعلق به من عقود وحسابات بنجاح")
                                                  .type(NotificationDegree.error).build());
        }
    }

    @GetMapping(value = "findOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOne(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_DETAILS),
                                       sellerService.findOne(id));
    }

    @GetMapping(value = "findAllCombo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findAllCombo() {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_COMBO),
                                       sellerService.findAll(new Sort(Sort.Direction.ASC, "contact.shortName")));
    }

    @GetMapping(value = "findSellerBalance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findSellerBalance(@PathVariable(value = "id") Long id) {
        Seller seller = sellerService.findOne(id);

        Double depositAmount = bankTransactionService
                .findBySellerAndTransactionTypeIn(seller,
                                                  Lists.newArrayList(
                                                          Initializer.transactionTypeDeposit,
                                                          Initializer.transactionTypeDepositPayment,
                                                          Initializer.transactionTypeDepositTransfer),
                                                  BankTransactionAmount.class)
                .stream().mapToDouble(BankTransactionAmount::getAmount).sum();

        Double withdrawAmount = bankTransactionService
                .findBySellerAndTransactionTypeIn(seller,
                                                  Lists.newArrayList(
                                                          Initializer.transactionTypeWithdraw,
                                                          Initializer.transactionTypeWithdrawCash,
                                                          Initializer.transactionTypeWithdrawPurchase,
                                                          Initializer.transactionTypeWithdrawTransfer),
                                                  BankTransactionAmount.class)
                .stream().mapToDouble(BankTransactionAmount::getAmount).sum();

        LOG.info("مجموع الإيداعات = " + depositAmount);
        seller.setTotalDeposits(depositAmount);

        LOG.info("مجموع السحبيات = " + withdrawAmount);
        seller.setTotalWithdraws(withdrawAmount);

        seller.setBalance(depositAmount - withdrawAmount);

        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), seller);
    }

    @GetMapping(value = "findAllSellerBalance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findAllSellerBalance() {
        List<Seller> sellers = Lists.newArrayList(sellerService.findAll());
        ListIterator<Seller> bankListIterator = sellers.listIterator();
        while (bankListIterator.hasNext()) {

            Seller seller = bankListIterator.next();

            Double depositAmount = bankTransactionService
                    .findBySellerAndTransactionTypeIn(seller,
                                                      Lists.newArrayList(
                                                              Initializer.transactionTypeDeposit,
                                                              Initializer.transactionTypeDepositPayment,
                                                              Initializer.transactionTypeDepositTransfer),
                                                      BankTransactionAmount.class)
                    .stream().mapToDouble(BankTransactionAmount::getAmount).sum();

            Double withdrawAmount = bankTransactionService
                    .findBySellerAndTransactionTypeIn(seller,
                                                      Lists.newArrayList(
                                                              Initializer.transactionTypeWithdraw,
                                                              Initializer.transactionTypeWithdrawCash,
                                                              Initializer.transactionTypeWithdrawPurchase,
                                                              Initializer.transactionTypeWithdrawTransfer),
                                                      BankTransactionAmount.class)
                    .stream().mapToDouble(BankTransactionAmount::getAmount).sum();

            LOG.info("مجموع الإيداعات = " + depositAmount);
            seller.setTotalDeposits(depositAmount);

            LOG.info("مجموع السحبيات = " + withdrawAmount);
            seller.setTotalWithdraws(withdrawAmount);

            seller.setBalance(depositAmount - withdrawAmount);
        }
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), sellers);
    }

    @GetMapping(value = "filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filter(
            @RequestParam(value = "codeFrom", required = false) final Integer codeFrom,
            @RequestParam(value = "codeTo", required = false) final Integer codeTo,
            @RequestParam(value = "registerDateFrom", required = false) final Long registerDateFrom,
            @RequestParam(value = "registerDateTo", required = false) final Long registerDateTo,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "mobile", required = false) final String mobile,
            @RequestParam(value = "phone", required = false) final String phone,
            @RequestParam(value = "nationality", required = false) final String nationality,
            @RequestParam(value = "identityNumber", required = false) final String identityNumber,
            @RequestParam(value = "qualification", required = false) final String qualification,
            @RequestParam(value = "filterCompareType", required = false) final String filterCompareType,
            Pageable pageable) {
        return SquigglyUtils.stringify(
                Squiggly.init(
                        new ObjectMapper(),
                        "**,".concat("content[").concat(FILTER_TABLE).concat("]")),
                sellerSearch.filter(
                        codeFrom,
                        codeTo,
                        registerDateFrom,
                        registerDateTo,
                        name,
                        mobile,
                        phone,
                        nationality,
                        identityNumber,
                        qualification,
                        filterCompareType,
                        pageable));
    }
}
