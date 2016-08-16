package org.chesscorp.club.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * A JavaMail SMTP sender implementation.
 */
@Component
@Profile("mail")
public class MailServiceSmtp implements MailService {
    private Logger logger = LoggerFactory.getLogger(MailServiceSmtp.class);

    private String sender;
    private JavaMailSender mailSender;
    private SpringTemplateEngine templateEngine;

    @Autowired
    public MailServiceSmtp(JavaMailSender mailSender,
                           @Value("${pan-discovery.reports.sender}") String sender,
                           SpringTemplateEngine templateEngine) {
        this.sender = sender;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendAccountValidationLink(String recipientName, String recipientAddress, String validationToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientAddress);
            helper.setFrom(sender);
            helper.setSubject("...");

            Locale locale = Locale.getDefault();
            Context ctx = new Context(locale);
            ctx.setVariable("name", recipientName);
            ctx.setVariable("token", validationToken);

            String htmlContent = this.templateEngine.process("email-account-validation.html", ctx);
            helper.setText(htmlContent, true); // true = isHtml

            this.mailSender.send(message);
            logger.info("Account validation token sent to {}", recipientAddress);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
    }
}