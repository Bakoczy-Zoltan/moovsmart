package com.progmasters.moovsmart.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;

@Service
@Transactional
public class MailSenderService {

    private final Logger logger = LoggerFactory.getLogger(MailSenderService.class);
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailSenderAddress;

    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMailByRegistration(String name, String mail, Long id){
        String originalId = ""+ id;
        String encodedString = Base64.getEncoder().encodeToString(originalId.getBytes());

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            String link="http://localhost/4200/"+encodedString;


            message.setSubject("Regisztráció megerősítése");
            message.setFrom(mailSenderAddress);
            message.setTo(mail);
            message.setText("Kedves " + name + "!\n" +
                    "Kérlek kattints az alábbi linkre a regisztrációhoz!" + "\n" + link
                    );

            javaMailSender.send(message);
            String logInfo = "Mail send to: #" + mail;
            logger.info(logInfo);
        }catch (Exception e){
            String logInfo = "Mail sending rejected to address of: " + mail;
            logger.debug(logInfo);
        }
    }
}
