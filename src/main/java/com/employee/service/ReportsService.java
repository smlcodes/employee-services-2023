package com.employee.service;

public interface ReportsService {
    String employeeJasperReport(String fileType);
    byte[] employeeJasperReport24(String fileType);

    byte[] employeeJasperReportInBytes(String fileType) throws Exception;

    byte[] employeeCarboneReport(String fileType);


    byte[] userJasperReport(String fileType);

    byte[] jasperSubreport(String fileType) throws Exception;
}
