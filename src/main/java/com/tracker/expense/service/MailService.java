package com.tracker.expense.service;

import com.tracker.expense.model.EmailNotification;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender ;
    private final MailBuilder mailBuilder ;

    @Async
    public void sendMail(EmailNotification emailNotification){
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setSubject(emailNotification.getSubject());
            mimeMessageHelper.setTo(emailNotification.getRecipient());
            mimeMessageHelper.setFrom("expensetracker@yaho.com");
            mimeMessageHelper.setText(mailBuilder.build(emailNotification.getMessage()));
        };

        try{
            javaMailSender.send(mimeMessagePreparator);
            log.info("Mail send Successfully");


        }catch(MailException mailException){
            log.error("Something went wrong !! Mail not send");
            mailException.printStackTrace();
        }


    }
}
