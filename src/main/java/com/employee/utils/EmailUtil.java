package com.employee.utils;

import com.employee.api.v1.model.dto.EmailRequestDto;
import com.employee.api.v1.model.dto.EmployeeDto;
import com.employee.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author satyakaveti
 */
@Slf4j
@Component
public class EmailUtil {



    public static String employeeMailTemplate(List<EmployeeDto> employees) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Employee Table</title>");
        html.append("</head>");
        html.append("<body>");
        html.append("<table style=\"border-collapse: collapse;\">");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th style=\"background-color: lightgrey; border: 1px solid black;\">ID</th>");
        html.append("<th style=\"background-color: lightgrey; border: 1px solid black;\">Name</th>");
        html.append("<th style=\"background-color: lightgrey; border: 1px solid black;\">Salary</th>");
        html.append("<th style=\"background-color: lightgrey; border: 1px solid black;\">City</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");

        for (EmployeeDto employee : employees) {
            html.append("<tr>");
            html.append("<td style=\"border: 1px solid black;\">").append(employee.getId()).append("</td>");
            html.append("<td style=\"border: 1px solid black;\">").append(employee.getName()).append("</td>");
            html.append("<td style=\"border: 1px solid black;\">").append(employee.getSalary()).append("</td>");
            html.append("<td style=\"border: 1px solid black;\">").append(employee.getCity()).append("</td>");
            html.append("</tr>");
        }

        html.append("</tbody>");
        html.append("</table>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    public void sendMail(EmailRequestDto emailRequestDto) {

    }

    public static EmailRequestDto getEmailDtoFromEmployee(EmployeeDto emp){
            if(ObjectUtils.isEmpty(emp.getAccount().getEmail()))
                emp.getAccount().setEmail("smlcodes@gmail.com");
            return EmailRequestDto.builder()
                    .subject(emp.getName() + " Account Created")
                    .body("BODY : "+emp)
                    .to(emp.getAccount().getEmail())
                    .cc(emp.getAccount().getEmail())
                    .build();

    }


}
