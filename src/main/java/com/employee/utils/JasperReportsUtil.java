package com.employee.utils;

import com.employee.constants.ReportTypeEnum;
import com.employee.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author satyakaveti
 */
@Slf4j
@Component
public class JasperReportsUtil {


    public static JasperReport getSubReport(String jrxml) throws FileNotFoundException {
        String path = ResourceUtils.getFile("classpath:" + jrxml).getAbsolutePath();
        try {
            return JasperCompileManager.compileReport(path);
        } catch (JRException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public byte[] generateJasperReportBytes(String jrxml, Map<String, Object> parameters, List dataSource, String fileType) {

        try {
            //1.Set Parameters
            parameters.put("createdBy","Satya Kaveti");

            //1.Create DataSource
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);

            //2.Compile .jrmxl template, stored in JasperReport object
            String path = ResourceUtils.getFile("classpath:" + jrxml).getAbsolutePath();
            JasperReport jasperReport = JasperCompileManager.compileReport(path);

            //3.Fill Report - by passing complied .jrxml object, paramters, datasource
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);

            //4.Export Report - by using JasperExportManager
            ReportTypeEnum reportType = ReportTypeEnum.getReportTypeByCode(fileType);
            return exportJasperReportBytes(jasperPrint, reportType);

        } catch (JRException e) {
            log.error("JRException : ", e);
            return null;
        } catch (Exception e) {
            log.error("Exception : ", e);
            return null;
        }
    }

    public String generateJasperReportToFile(String template, List dataSource, String fileType) {
        try {
            File file = ResourceUtils.getFile("classpath:" + template);
            InputStream input = new FileInputStream(file);

            // Compile the Jasper report from .jrxml to .japser
            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            // Get your data source
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Satya Kaveti");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
            String fileName = "Employee_report_" + LocalDateTime.now();

            return exportJasperReportToFile(jasperPrint, fileName, fileType);
        } catch (Exception e) {
            log.error("Error Occurred", e);
            throw new BusinessException("Error generateJasperReport");
        }
    }


    private String exportJasperReportToFile(JasperPrint jasperPrint, String fileName, String fileType) throws Exception {
        String reportPath = "download";
        String outputPath = "";
        switch (fileType) {
            case "csv":
                outputPath = reportPath + "//" + fileName + ".csv";
                JRCsvExporter csvExporter = new JRCsvExporter();
                csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputPath));
                SimpleCsvExporterConfiguration csvConfiguration = new SimpleCsvExporterConfiguration();
                csvConfiguration.setFieldDelimiter(",");
                csvExporter.setConfiguration(csvConfiguration);
                csvExporter.exportReport();
                break;
            case "xlsx":
                outputPath = reportPath + "//" + fileName + ".xlsx";
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));
                SimpleXlsxReportConfiguration xlsxConfiguration = new SimpleXlsxReportConfiguration();
                xlsxConfiguration.setOnePagePerSheet(true);
                xlsxConfiguration.setRemoveEmptySpaceBetweenColumns(true);
                xlsxConfiguration.setDetectCellType(true);
                xlsxExporter.setConfiguration(xlsxConfiguration);
                xlsxExporter.exportReport();
                break;
            case "html":
                outputPath = reportPath + "//" + fileName + ".html";
                JasperExportManager.exportReportToHtmlFile(jasperPrint, outputPath);
                break;
            case "xml":
                outputPath = reportPath + "//" + fileName + ".xml";
                JasperExportManager.exportReportToXmlFile(jasperPrint, outputPath, true);
                break;
            default:
                outputPath = reportPath + "//" + fileName + ".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        }

        log.info(fileType.toUpperCase() + " File Generated at " + outputPath);
        return outputPath;
    }



    public byte[] exportJasperReportBytes(JasperPrint jasperPrint, ReportTypeEnum reportType) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        switch (reportType) {
            case CSV:
                // Export to CSV
                JRCsvExporter csvExporter = new JRCsvExporter();
                csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
                csvExporter.exportReport();
                break;
            case XLSX:
                // Export to XLSX
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                xlsxExporter.exportReport();
                break;
            case HTML:
                // Export to HTML
                HtmlExporter htmlExporter = new HtmlExporter();
                htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
                htmlExporter.exportReport();
                break;
            case XML:
                // Export to XML
                JRXmlExporter xmlExporter = new JRXmlExporter();
                xmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xmlExporter.setExporterOutput(new SimpleXmlExporterOutput(outputStream));
                xmlExporter.exportReport();
                break;
            case DOC:
                // Export to DOCX (RTF format)
                JRRtfExporter docxExporter = new JRRtfExporter();
                docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                docxExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
                docxExporter.exportReport();
                break;
            default:
                // Export to PDF by default
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                break;
        }
        return outputStream.toByteArray();
    }


}
