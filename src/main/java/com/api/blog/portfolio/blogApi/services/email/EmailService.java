package com.api.blog.portfolio.blogApi.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendSimpleMessage(EmailDto email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@email.com");
        message.setTo(email.to());
        message.setSubject(email.subject());
        message.setText(email.body());
        javaMailSender.send(message);
    }

    public void sendHtmlMessageValidateUser(EmailDto email, Context context, String emailTemplate) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom("noreply@email.com");
        helper.setTo(email.to());
        helper.setSubject(email.subject());

        // Processamento do template
        String htmlContent = templateEngine.process(emailTemplate, context);
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }

}
