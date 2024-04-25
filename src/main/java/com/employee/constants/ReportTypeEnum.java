package com.employee.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author satyakaveti on 24/04/24
 */
@AllArgsConstructor
public enum ReportTypeEnum {

    PDF("pdf", "", ".pdf"),
    XLSX("xlsx", "", ".xlsx"),
    CSV("csv", "", ".csv"),
    DOC("doc", "", ".doc"),
    XML("xml", "", ".xml"),
    HTML("html", "", ".html");

    @Getter
    private String type;

    @Getter
    private String contentType;

    @Getter
    private String extension;

    public static ReportTypeEnum getReportTypeByCode(String name) {
        try {
            return ReportTypeEnum.valueOf(name.toUpperCase());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<ReportTypeEnum> getReportTypeValues() {
        return Stream.of(ReportTypeEnum.values()).collect(Collectors.toList());
    }
}
