package com.besafx.app.report;

import com.besafx.app.component.ReportExporter;
import com.besafx.app.enums.ExportType;
import com.besafx.app.init.Initializer;
import com.besafx.app.service.ContractService;
import com.besafx.app.util.CompanyOptions;
import com.besafx.app.util.JSONConverter;
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
import java.net.MalformedURLException;
import java.net.URL;
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
    public void printContract(
            @PathVariable(value = "contractId") Long contractId,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("CONTRACT", contractService.findOne(contractId));

        CompanyOptions options = JSONConverter.toObject(Initializer.company.getOptions(), CompanyOptions.class);
        URL LOGO_URL = null;
        URL BACKGROUND_URL = null;
        try {
            LOGO_URL = new URL(options.getLogo());
            BACKGROUND_URL = new URL(options.getBackground());
        } catch (MalformedURLException ex) {
        }
        map.put("LOGO", LOGO_URL == null ? null : LOGO_URL.openStream());
        map.put("BACKGROUND", BACKGROUND_URL == null ? null : BACKGROUND_URL.openStream());

        ClassPathResource jrxmlFile = new ClassPathResource("/report/contract/Contract.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile.getInputStream());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map);
        reportExporter.export(ExportType.PDF, response, jasperPrint);
    }

    @RequestMapping(value = "/report/receipt/{contractId}", method = RequestMethod.GET, produces = "application/pdf")
    @ResponseBody
    public void printReceipt(
            @PathVariable(value = "contractId") Long contractId,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("CONTRACT", contractService.findOne(contractId));
        ClassPathResource jrxmlFile = new ClassPathResource("/report/contract/Receipt.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile.getInputStream());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map);
        reportExporter.export(ExportType.PDF, response, jasperPrint);
    }


}
