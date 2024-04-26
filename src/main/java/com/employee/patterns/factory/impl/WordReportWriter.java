package com.employee.patterns.factory.impl;

import com.employee.patterns.factory.ReportWriter;

/**
 * @author satyakaveti on 25/04/24
 */
public class WordReportWriter implements ReportWriter {
    @Override
    public void writeReport() {
        System.out.println("Writing Word report...");
    }
}
