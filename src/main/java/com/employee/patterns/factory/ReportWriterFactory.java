package com.employee.patterns.factory;

import com.employee.patterns.factory.impl.PDFReportWriter;
import com.employee.patterns.factory.impl.WordReportWriter;
import com.employee.patterns.factory.impl.XMLReportWriter;

/**
 * @author satyakaveti on 25/04/24
 */
public class ReportWriterFactory {

    public static ReportWriter createReportWriter(ReportTypeEnum type) {
        switch (type) {
            case PDF:
                return new PDFReportWriter();
            case XML:
                return new XMLReportWriter();
            case WORD:
                return new WordReportWriter();
            default:
                throw new IllegalArgumentException("Unsupported report type: " + type);
        }
    }
}

