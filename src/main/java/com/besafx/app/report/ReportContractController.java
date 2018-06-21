package com.besafx.app.report;

import com.besafx.app.component.ReportExporter;
import com.besafx.app.enums.ExportType;
import com.besafx.app.service.ContractService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ReportContractController {

    private final static Logger log = LoggerFactory.getLogger(ReportContractController.class);

    @Autowired
    private ContractService contractService;

    @Autowired
    private ReportExporter reportExporter;

    @RequestMapping(value = "/report/contract/{contractId}", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public void printAccountByBranches(
            @PathVariable(value = "contractId") Long contractId,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("CONTRACT", contractService.findOne(contractId));
        ClassPathResource jrxmlFile = new ClassPathResource("/report/contract/Contract.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile.getInputStream());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map);
        reportExporter.export(ExportType.PDF, response, jasperPrint);
    }


}
