package io.danielegradassai.service.impl;

import io.danielegradassai.entity.User;
import io.danielegradassai.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${email.link}")
    private String link;

    @Value("${email.link2}")
    private String link2;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendConfirmationEmail(User recipient){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(sender);
        mail.setTo(recipient.getEmail());
        mail.setSubject("Confirmation Email");
        mail.setText("Account generato correttamente di seguito le credenziali per il primo accesso: " +
                "\nEmail: " + recipient.getEmail() +
                "\nPassword: " + recipient.getPassword() +
                "\nReimposta la password per completare l'attivazione dell'account!"+
                "\nLink di attivazione account: " + link + recipient.getId());
        mailSender.send(mail);
    }

    @Override
    public void sendNotificationEmail(User recipient, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(sender);
        mail.setTo(recipient.getEmail());
        mail.setSubject("Nuova notifica");
        mail.setText(message + link2);
        mailSender.send(mail);
    }

}
