package com.employee.utils;

import com.employee.api.v1.model.dto.EmailDto;
import com.querydsl.core.util.ArrayUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author satyakaveti
 */
@Slf4j
@Component
public class EmailUtil {


    @Autowired
    private JavaMailSender mailSender;


    public MimeMessage getMessage(EmailDto emailDto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.toString());
        messageHelper.setSubject(emailDto.getSubject());
        messageHelper.setText(emailDto.getBody(), true);

        if (!StringUtils.isEmpty(emailDto.getFrom())) {
            messageHelper.setFrom(emailDto.getFrom());
        }

        String[] to = emailDto.getTo();
        messageHelper.setTo(to);

        String[] cc = emailDto.getCc();

        if (!ArrayUtils.isEmpty(cc)) {
            messageHelper.setCc(cc);
        }
        return message;
    }


    public void sendMail(EmailDto emailDto) throws JobExecutionException {

        try {
            log.info("Scheduler Triggered at : {}", LocalDateTime.now());
            //mailSender.send(getMessage(emailDto));
            log.info(" ===== > mail sent....: \n {} \n \n", emailDto);
        } catch (Exception ex) {
            log.error("Failed to send email notification.", ex);
            throw new JobExecutionException(ex);
        }
    }

}
