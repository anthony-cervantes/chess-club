package org.chesscorp.club.service;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

public class MailServiceSmtpTest {


    @Test
    public void mailSending() throws IOException {
        MimeMessage messageMock = Mockito.mock(MimeMessage.class);

        JavaMailSender mailSenderMock = Mockito.mock(JavaMailSender.class);
        Mockito.when(mailSenderMock.createMimeMessage()).thenReturn(messageMock);

        TemplateService templateServiceMock = Mockito.mock(TemplateService.class);
        Mockito.when(templateServiceMock.buildEmailAccountValidation(Mockito.anyString(), Mockito.anyString())).thenReturn("<html></html>");

        String token = "####TOKEN###";

        MailServiceSmtp mailServiceSmtp = new MailServiceSmtp(mailSenderMock, "sender@dumbo.com", templateServiceMock);
        mailServiceSmtp.sendAccountValidationLink("Brian May", "brian.may@queen.co.uk", token);
    }
}

