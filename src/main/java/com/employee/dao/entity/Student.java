package com.employee.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private LocalDate dob;

    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phoneNumber;
    private String email;
    private String gender;


    private String admissionDate;
    private String studentClass;
    private String rollNumber;
    private double height;
    private double weight;

    // Annual subject marks for 6 main subjects
    private double teluguMarks;
    private double hindiMarks;
    private double englishMarks;
    private double mathematicsMarks;
    private double scienceMarks;
    private double socialStudiesMarks;

    private double totalMarks;
    private String percentage;
    private String remarks;
    private boolean isPassed;

    // Add getters and setters (omitted for brevity)

    // Constructors, hashCode, equals, and toString methods (optional, for completeness)
}
