package com.employee.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author satyakaveti on 24/04/24
 */
@AllArgsConstructor
public enum DepartmentEnum {

    //Define Values for Enum with arguments
    IT("IT", "IT Engineering", "ENGINEERING"),
    CSE("CSE", "Computer Science Engineering", "ENGINEERING"),
    ECE("ECE", "Electronics Engineering", "ENGINEERING"),



    MBBS("MBBS", "Bachelor in Medicine", "MEDICINE"){
        @Override
        public void print() {
            System.out.println("MBBS : Override Print : "+this.getLabel());
        }
    },
    MD("MD", "Masters in Medicine", "MEDICINE"){
        @Override
        public void print() {
            System.out.println("MD : Override Print : "+this.getLabel());
        }
    };


    private static EnumSet<DepartmentEnum> engineeringSet =
            EnumSet.of(DepartmentEnum.CSE, DepartmentEnum.IT, DepartmentEnum.ECE);

    private static EnumSet<DepartmentEnum> medicineSet =
            EnumSet.of(DepartmentEnum.MBBS, DepartmentEnum.MD);


    //Define arguments for Enum
    @Getter
    private final String code;
    @Getter
    private final String label;
    @Getter
    private final String field;

    public static DepartmentEnum getDepartmentByCode(String code) {
        return DepartmentEnum.valueOf(code);
    }

    public static List<DepartmentEnum> getDepartmentValues() {
        return Stream.of(DepartmentEnum.values()).collect(Collectors.toList());
    }

    public void print() {
        System.out.println("Default Print");
    }
}
