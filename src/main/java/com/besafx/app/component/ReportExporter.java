package com.besafx.app.component;

import com.besafx.app.enums.ExportType;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.List;

@Component
public class ReportExporter {

    private final Logger log = LoggerFactory.getLogger(ReportExporter.class);

    public void export(final ExportType exportType, final HttpServletResponse response, final JasperPrint jasperPrint) {
        export("Report", exportType, response, jasperPrint);
    }

    public void export(final String fileName, final ExportType exportType, final HttpServletResponse response, final JasperPrint jasperPrint) {
        ServletOutputStream servletOutputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            servletOutputStream = response.getOutputStream();
            baos = new ByteArrayOutputStream();
            Exporter exporter = null;
            switch (exportType) {
                case PDF:
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + URLEncoder.encode(fileName, "utf-8") + ".pdf\"");
                    exporter = new JRPdfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    SimplePdfExporterConfiguration configurationPdf = new SimplePdfExporterConfiguration();
                    configurationPdf.setCreatingBatchModeBookmarks(true);
                    exporter.setConfiguration(configurationPdf);
                    break;
                case RTF:
                    response.setContentType("application/rtf");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".rtf\"");
                    exporter = new JRRtfExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleWriterExporterOutput(servletOutputStream));
                    break;
                case HTML:
                    response.setContentType("application/html");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".html\"");
                    exporter = new HtmlExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleHtmlExporterOutput(servletOutputStream));
                    SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();
                    reportExportConfiguration.setWhitePageBackground(false);
                    reportExportConfiguration.setRemoveEmptySpaceBetweenRows(true);
                    exporter.setConfiguration(reportExportConfiguration);
                    break;
                case XHTML:
                    response.setContentType("application/xhtml");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".xhtml\"");
                    exporter = new HtmlExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleHtmlExporterOutput(servletOutputStream));
                    break;
                case XLSX:
                    response.setContentType("application/xlsx");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".xlsx\"");
                    exporter = new JRXlsxExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    SimpleXlsxReportConfiguration configurationXlsx = new SimpleXlsxReportConfiguration();
                    configurationXlsx.setOnePagePerSheet(false);
                    exporter.setConfiguration(configurationXlsx);
                    break;
                case CSV:
                    response.setContentType("application/csv");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".csv\"");
                    exporter = new JRCsvExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleWriterExporterOutput(servletOutputStream));
                    break;
                case PPTX:
                    response.setContentType("application/pptx");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".pptx\"");
                    exporter = new JRPptxExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    break;
                case DOCX:
                    response.setContentType("application/docx");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".docx\"");
                    exporter = new JRDocxExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    break;
                case ODS:
                    response.setContentType("application/ods");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".ods\"");
                    exporter = new JROdsExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    SimpleOdsReportConfiguration configurationOds = new SimpleOdsReportConfiguration();
                    configurationOds.setOnePagePerSheet(true);
                    exporter.setConfiguration(configurationOds);
                    break;
                case ODT:
                    response.setContentType("application/odt");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + ".odt\"");
                    exporter = new JROdtExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
                    break;
            }
            exporter.exportReport();
            response.setContentLength(baos.size());
            baos.writeTo(servletOutputStream);

            servletOutputStream.flush();
            servletOutputStream.close();
            baos.close();

        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

    public void exportMultiple(final String fileName, final HttpServletResponse response, final List<JasperPrint> jasperPrint) {
        ServletOutputStream servletOutputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            servletOutputStream = response.getOutputStream();
            baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = null;

            response.setContentType("application/pdf");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("content-disposition", "inline; filename=\"" + URLEncoder.encode(fileName, "utf-8") + ".pdf\"");

            exporter = new JRPdfExporter();
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));

            SimplePdfExporterConfiguration configurationPdf = new SimplePdfExporterConfiguration();
            configurationPdf.setCreatingBatchModeBookmarks(true);
            exporter.setConfiguration(configurationPdf);

            exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrint));

            exporter.exportReport();
            response.setContentLength(baos.size());
            baos.writeTo(servletOutputStream);

            servletOutputStream.flush();
            servletOutputStream.close();
            baos.close();

        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }

}
