package com.besafx.app.rest;

import com.besafx.app.auditing.EntityHistoryListener;
import com.besafx.app.config.GatewaySMS;
import com.besafx.app.entity.ContractPayment;
import com.besafx.app.entity.ContractPremium;
import com.besafx.app.search.ContractPremiumSearch;
import com.besafx.app.service.BankTransactionService;
import com.besafx.app.service.ContractPaymentService;
import com.besafx.app.service.ContractPremiumService;
import com.besafx.app.util.DateConverter;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationDegree;
import com.besafx.app.ws.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/contractPremium/")
public class ContractPremiumRest {

    private final static Logger LOG = LoggerFactory.getLogger(ContractPremiumRest.class);

    private final String FILTER_TABLE = "" +
            "**," +
            "contract[id,code,customer[id,contact,shortName],seller[id,contact,shortName]]," +
            "-contractPayments";

    @Autowired
    private ContractPremiumService contractPremiumService;

    @Autowired
    private ContractPremiumSearch contractPremiumSearch;

    @Autowired
    private ContractPaymentService contractPaymentService;

    @Autowired
    private BankTransactionService bankTransactionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GatewaySMS gatewaySMS;

    @Autowired
    private EntityHistoryListener entityHistoryListener;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_PREMIUM_CREATE')")
    @Transactional
    public String create(@RequestBody ContractPremium contractPremium) {
        contractPremium = contractPremiumService.save(contractPremium);
        notificationService.notifyAll(Notification
                                              .builder()
                                              .message("تم اضافة قسط للعقد بنجاح")
                                              .type(NotificationDegree.success).build());
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), contractPremium);
    }

    @PutMapping(value = "update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_PREMIUM_UPDATE')")
    @Transactional
    public String update(@RequestBody ContractPremium contractPremium) {
        ContractPremium object = contractPremiumService.findOne(contractPremium.getId());
        if (object != null) {
            contractPremium = contractPremiumService.save(contractPremium);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم تعديل بيانات القسط بنجاح")
                                                  .type(NotificationDegree.success).build());
            return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), contractPremium);
        } else {
            return null;
        }
    }

    @DeleteMapping(value = "delete/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTRACT_PREMIUM_DELETE')")
    @Transactional
    public void delete(@PathVariable Long id) {
        ContractPremium contractPremium = contractPremiumService.findOne(id);
        if (contractPremium != null) {
            contractPaymentService.delete(contractPremium.getContractPayments());
            bankTransactionService.delete(
                    contractPremium.getContractPayments()
                                   .stream()
                                   .map(ContractPayment::getBankTransaction)
                                   .collect(Collectors.toList())
                                         );
            contractPremiumService.delete(id);
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message("تم حذف القسط من العقد وكل الدفعات المرتبطة بهذا القسط بنجاح")
                                                  .type(NotificationDegree.error).build());
        }
    }

    @PostMapping(value = "sendMessage/{contractPremiumIds}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_SMS_SEND')")
    @Transactional
    public void sendMessage(
            @RequestBody String content,
            @PathVariable List<Long> contractPremiumIds) throws Exception {
        ListIterator<Long> listIterator = contractPremiumIds.listIterator();
        while (listIterator.hasNext()) {
            Long id = listIterator.next();
            ContractPremium contractPremium = contractPremiumService.findOne(id);
            String message = content.replaceAll("#amount#", contractPremium.getAmount().toString())
                                    .replaceAll("#dueDate#", DateConverter.getDateInFormat(contractPremium.getDueDate()));
            String mobile = "966" + contractPremium.getContract().getCustomer().getContact().getMobile().substring(1);
            Future<String> task = gatewaySMS.sendSMS(mobile, message);
            String taskResult = task.get();
            StringBuilder builder = new StringBuilder();
            builder.append("الرقم / ");
            builder.append(mobile);
            builder.append("<br/>");
            builder.append(" محتوى الرسالة : ");
            builder.append(message);
            builder.append("<br/>");
            builder.append(" ، نتيجة الإرسال: ");
            builder.append(new JSONObject(taskResult).getString("ErrorMessage"));
            notificationService.notifyAll(Notification
                                                  .builder()
                                                  .message(builder.toString())
                                                  .type(NotificationDegree.information).build());
            entityHistoryListener.perform(builder.toString());
        }
    }

    @GetMapping(value = "findOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findOne(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       contractPremiumService.findOne(id));
    }

    @GetMapping(value = "findByContract/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findByContract(@PathVariable Long id) {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       contractPremiumService.findByContractId(id, new Sort(Sort.Direction.ASC, "dueDate")));
    }

    @GetMapping(value = "findLatePremiums", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findLatePremiums() {
        Specifications specifications = Specifications.where((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), new DateTime().toDate()));
        Sort sort = new Sort(Sort.Direction.ASC, "dueDate");
        List<ContractPremium> contractPremiums = contractPremiumService.findAll(specifications, sort);
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       contractPremiums
                                               .stream()
                                               .filter(contractPremium -> contractPremium.getState().equalsIgnoreCase("غير مسدد")).collect(Collectors.toList()));
    }

    @GetMapping(value = "findRequiredThisMonth", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String findRequiredThisMonth() {
        DateTime startMonth = new DateTime().withDayOfMonth(1).withTimeAtStartOfDay();
        DateTime endMonth = new DateTime().plusMonths(1).withDayOfMonth(1).withTimeAtStartOfDay();
        LOG.info("GETTING REQUIRED PREMIUMS THAT MUST BE PAID WITHIN THIS MONTH...");
        LOG.info("Start Month: " + startMonth.toString());
        LOG.info("End Month  : " + endMonth.toString());
        Specifications specifications = Specifications
                .where((root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("dueDate"), startMonth.toDate()))
                .and((root, cq, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), endMonth.toDate()));
        Sort sort = new Sort(Sort.Direction.ASC, "dueDate");
        List<ContractPremium> contractPremiums = contractPremiumService.findAll(specifications, sort);
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE),
                                       contractPremiums.stream()
                                                       .filter(contractPremium -> contractPremium.getRemain() > 0)
                                                       .collect(Collectors.toList()));
    }

    @GetMapping(value = "filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String filter(
            //ContractPremium Filters
            @RequestParam(value = "dueDateFrom", required = false) final Long dueDateFrom,
            @RequestParam(value = "dueDateTo", required = false) final Long dueDateTo,
            //Contract Filters
            @RequestParam(value = "contractCodeFrom", required = false) final Integer contractCodeFrom,
            @RequestParam(value = "contractCodeTo", required = false) final Integer contractCodeTo,
            @RequestParam(value = "contractDateFrom", required = false) final Long contractDateFrom,
            @RequestParam(value = "contractDateTo", required = false) final Long contractDateTo,
            //Customer Filters
            @RequestParam(value = "customerName", required = false) final String customerName,
            @RequestParam(value = "customerMobile", required = false) final String customerMobile,
            //Seller Filters
            @RequestParam(value = "sellerName", required = false) final String sellerName,
            @RequestParam(value = "sellerMobile", required = false) final String sellerMobile,
            @RequestParam(value = "filterCompareType", required = false) final String filterCompareType,
            Pageable pageable) {
        return SquigglyUtils.stringify(
                Squiggly.init(
                        new ObjectMapper(),
                        "**,".concat("content[").concat(FILTER_TABLE).concat("]")),
                contractPremiumSearch.filter(
                        dueDateFrom,
                        dueDateTo,
                        contractCodeFrom,
                        contractCodeTo,
                        contractDateFrom,
                        contractDateTo,
                        customerName,
                        customerMobile,
                        sellerName,
                        sellerMobile,
                        filterCompareType,
                        pageable));
    }
}
