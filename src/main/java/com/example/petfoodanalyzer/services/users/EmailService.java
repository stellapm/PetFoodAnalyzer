package com.example.petfoodanalyzer.services.users;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender,
                        TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendRegistrationEmail(String userEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom("admin@petfoodanalyzer.com");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Welcome to Pet Food Analyzer!");
            mimeMessageHelper.setText(generateWelcomeText(), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateWelcomeText() {
        Context ctx = new Context();
        ctx.setLocale(Locale.getDefault());

        return templateEngine.process("email/welcome-email", ctx);
    }
}