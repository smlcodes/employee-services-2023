package com.employee.service;

public interface ReportsService {
    String employeeJasperReport(String fileType);

    byte[] employeeCarboneReport(String fileType);
}
