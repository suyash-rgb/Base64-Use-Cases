package com.suyash.Base64UseCases.services.usecase2;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    //method to send text email
    public void sendSimpleTextEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.setFrom("suyashbaoney58@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, false);

        javaMailSender.send(message);
    }

    public void sendHtmlMailWithAttachment (String to,
                                            String subject,
                                            String htmlBody,
                                            Map<String, InputStreamSource> attachments)throws MessagingException{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
        mimeMessageHelper.setFrom("suyashbaoney58@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlBody, true);

        //attach files
        for(Map.Entry<String, InputStreamSource> entry: attachments.entrySet()){
            String fileName = entry.getKey();
            InputStreamSource source = entry.getValue();
            mimeMessageHelper.addAttachment(fileName, source);
        }

        javaMailSender.send(message);
    }


}
