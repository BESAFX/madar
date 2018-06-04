package com.besafx.app.init;

import com.besafx.app.entity.*;
import com.besafx.app.service.*;
import com.besafx.app.util.JSONConverter;
import com.besafx.app.util.Options;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final static Logger LOG = LoggerFactory.getLogger(Initializer.class);

    public static Company company;

    public static Bank bank;

    public static TransactionType transactionTypeDeposit;

    public static TransactionType transactionTypeDepositTransfer;

    public static TransactionType transactionTypeDepositPayment;

    public static TransactionType transactionTypeWithdraw;

    public static TransactionType transactionTypeWithdrawTransfer;

    public static TransactionType transactionTypeWithdrawPurchase;

    public static TransactionType transactionTypeWithdrawCash;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BankService bankService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeamService teamService;

    @Override
    public void run(String... args) {
        start();
        init();
    }

    private void start() {

        LOG.info("بداية العمل على إنشاء الحسابات الافتراضية والاساسية لتشغيل البرنامج لأول مرة");

        Company company = null;

        if (Lists.newArrayList(companyService.findAll()).isEmpty()) {

            LOG.info("إنشاء الحساب البنكي");
            Bank bank = new Bank();
            bank.setName("الصندوق");
            bankService.save(bank);

            LOG.info("إنشاء حساب مستثمر لصاحب المؤسسة");
            Seller seller = new Seller();
            seller.setCode(1);
            seller.setRegisterDate(new DateTime().toDate());
            seller.setEnabled(true);
            Contact contact = new Contact();
            contact.setName("مؤسسة المدار للتقسيط");
            seller.setContact(contactService.save(contact));

            LOG.info("إنشاء حساب الشركة");
            company = new Company();
            company.setName(seller.getContact().getName());
            company.setSeller(sellerService.save(seller));
            companyService.save(company);

            LOG.info("إنشاء أنواع العمليات المالية");

            LOG.info("عملية الإيداع");
            TransactionType transactionTypeDeposit = new TransactionType();
            transactionTypeDeposit.setCode("Deposit");
            transactionTypeDeposit.setName("إيداع");
            transactionTypeService.save(transactionTypeDeposit);

            LOG.info("عملية الإيداع[تحويل]");
            TransactionType transactionTypeDepositTransfer = new TransactionType();
            transactionTypeDepositTransfer.setCode("Deposit_Transfer");
            transactionTypeDepositTransfer.setName("إيداع[تحويل]");
            transactionTypeDepositTransfer.setTransactionType(transactionTypeDeposit);
            transactionTypeService.save(transactionTypeDepositTransfer);

            LOG.info("عملية الإيداع[سداد]");
            TransactionType transactionTypeDepositPayment = new TransactionType();
            transactionTypeDepositPayment.setCode("Deposit_Payment");
            transactionTypeDepositPayment.setName("إيداع[سداد]");
            transactionTypeDepositPayment.setTransactionType(transactionTypeDeposit);
            transactionTypeService.save(transactionTypeDepositPayment);

            LOG.info("عملية السحب");
            TransactionType transactionTypeWithdraw = new TransactionType();
            transactionTypeWithdraw.setCode("Withdraw");
            transactionTypeWithdraw.setName("سحب");
            transactionTypeService.save(transactionTypeWithdraw);

            LOG.info("عملية السحب[تحويل]");
            TransactionType transactionTypeWithdrawTransfer = new TransactionType();
            transactionTypeWithdrawTransfer.setCode("Withdraw_Transfer");
            transactionTypeWithdrawTransfer.setName("سحب[تحويل]");
            transactionTypeWithdrawTransfer.setTransactionType(transactionTypeWithdraw);
            transactionTypeService.save(transactionTypeWithdrawTransfer);

            LOG.info("عملية السحب[شراء]");
            TransactionType transactionTypeWithdrawPurchase = new TransactionType();
            transactionTypeWithdrawPurchase.setCode("Withdraw_Purchase");
            transactionTypeWithdrawPurchase.setName("سحب[شراء]");
            transactionTypeWithdrawPurchase.setTransactionType(transactionTypeWithdraw);
            transactionTypeService.save(transactionTypeWithdrawPurchase);

            LOG.info("عملية السحب[مصروفات]");
            TransactionType transactionTypeWithdrawCash = new TransactionType();
            transactionTypeWithdrawCash.setCode("Withdraw_Cash");
            transactionTypeWithdrawCash.setName("سحب[مصروفات]");
            transactionTypeWithdrawCash.setTransactionType(transactionTypeWithdraw);
            transactionTypeService.save(transactionTypeWithdrawCash);

        }

        if (Lists.newArrayList(personService.findAll()).isEmpty()) {
            Person person = new Person();
            Contact contact = new Contact();
            contact.setNickname("المهندس");
            contact.setName("بسام المهدي");
            contact.setPhoto("");
            contact.setQualification("Web Developer");
            person.setContact(contactService.save(contact));
            person.setEmail("islamhaker@gmail.com");
            person.setPassword(passwordEncoder.encode("besa2009"));
            person.setHiddenPassword("besa2009");
            person.setEnabled(true);
            person.setTokenExpired(false);
            person.setActive(false);
            person.setTechnicalSupport(true);
            Team team = new Team();
            team.setCode(1);
            team.setName("الدعم الفني");
            team.setAuthorities(String.join(",", "ROLE_COMPANY_UPDATE",
                                            "ROLE_DEPOSIT_CREATE",
                                            "ROLE_WITHDRAW_CREATE",
                                            "ROLE_TRANSFER_CREATE",
                                            "ROLE_WITHDRAW_CASH_CREATE",
                                            "ROLE_SMS_SEND",
                                            "ROLE_CUSTOMER_CREATE",
                                            "ROLE_CUSTOMER_UPDATE",
                                            "ROLE_CUSTOMER_DELETE",
                                            "ROLE_CUSTOMER_NOTE_CREATE",
                                            "ROLE_CUSTOMER_NOTE_UPDATE",
                                            "ROLE_CUSTOMER_NOTE_DELETE",
                                            "ROLE_SELLER_CREATE",
                                            "ROLE_SELLER_UPDATE",
                                            "ROLE_SELLER_DELETE",
                                            "ROLE_PRODUCT_CREATE",
                                            "ROLE_PRODUCT_UPDATE",
                                            "ROLE_PRODUCT_DELETE",
                                            "ROLE_PRODUCT_PURCHASE_CREATE",
                                            "ROLE_CONTRACT_CREATE",
                                            "ROLE_CONTRACT_UPDATE",
                                            "ROLE_CONTRACT_DELETE",
                                            "ROLE_CONTRACT_PRODUCT_CREATE",
                                            "ROLE_CONTRACT_PRODUCT_DELETE",
                                            "ROLE_CONTRACT_PREMIUM_CREATE",
                                            "ROLE_CONTRACT_PREMIUM_DELETE",
                                            "ROLE_CONTRACT_PAYMENT_CREATE",
                                            "ROLE_CONTRACT_PAYMENT_DELETE",
                                            "ROLE_PERSON_CREATE",
                                            "ROLE_PERSON_UPDATE",
                                            "ROLE_PERSON_DELETE",
                                            "ROLE_TEAM_CREATE",
                                            "ROLE_TEAM_UPDATE",
                                            "ROLE_TEAM_DELETE"
                                           ));
            person.setTeam(teamService.save(team));
            person.setOptions(JSONConverter
                                      .toString(Options.builder()
                                                       .lang("AR")
                                                       .style("mdl-style")
                                                       .dateType("G")
                                                       .iconSet("icon-set-2")
                                                       .iconSetType("png")
                                                       .build())
                             );
            person.setCompany(company);
            personService.save(person);
        }
    }

    private void init() {

        LOG.info("تعريف بعض المتغيرات الهامة");

        company = companyService.findFirstBy();

        bank = bankService.findFirstBy();

        transactionTypeDeposit = transactionTypeService.findByCode("Deposit");

        transactionTypeDepositTransfer = transactionTypeService.findByCode("Deposit_Transfer");

        transactionTypeDepositPayment = transactionTypeService.findByCode("Deposit_Payment");

        transactionTypeWithdraw = transactionTypeService.findByCode("Withdraw");

        transactionTypeWithdrawTransfer = transactionTypeService.findByCode("Withdraw_Transfer");

        transactionTypeWithdrawPurchase = transactionTypeService.findByCode("Withdraw_Purchase");

        transactionTypeWithdrawCash = transactionTypeService.findByCode("Withdraw_Cash");

    }
}
