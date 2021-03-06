package com.apzuopenmrs.mailservice.dataentry.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String[] recipients,String[] copyRecipients, String subject, String text, String senderEmail) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        message.setSubject(subject);
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(senderEmail);
        helper.setTo(recipients);
        for (String recipient:copyRecipients) {
            helper.addCc(recipient);
        }
        helper.setText(text, true);
        emailSender.send(message);
      /*  SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);*/
    }
}