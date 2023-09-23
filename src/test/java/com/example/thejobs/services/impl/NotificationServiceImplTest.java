package com.example.thejobs.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.thejobs.advice.ResponsePayload;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.StringReader;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NotificationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class NotificationServiceImplTest {
    @MockBean
    private Configuration configuration;

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Test
    void testSendBookingConfirmEmail() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl(mailSender,
                Configuration.getDefaultConfiguration());
        ResponsePayload actualSendBookingConfirmEmailResult = notificationServiceImpl
                .sendBookingConfirmEmail("jane.doe@example.org", new HashMap<>());
        assertEquals("Email Failed", actualSendBookingConfirmEmailResult.getData());
        assertEquals(HttpStatus.BAD_REQUEST, actualSendBookingConfirmEmailResult.getStatus());
        assertEquals("Bad Request", actualSendBookingConfirmEmailResult.getMessage());
        assertEquals(actualSendBookingConfirmEmailResult,
                notificationServiceImpl.sendBookingPendingEmail("jane.doe@example.org", null));
    }

    @Test
    void testSendBookingPendingEmail() throws IOException, MailException {
        doNothing().when(javaMailSender).send(Mockito.<MimeMessage>any());
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        when(configuration.getTemplate(Mockito.<String>any())).thenReturn(new Template("Name", new StringReader("foo")));
        ResponsePayload actualSendBookingPendingEmailResult = notificationServiceImpl
                .sendBookingPendingEmail("jane.doe@example.org", new HashMap<>());
        assertEquals("Email Send Successfully", actualSendBookingPendingEmailResult.getData());
        assertEquals(HttpStatus.OK, actualSendBookingPendingEmailResult.getStatus());
        assertEquals("OK", actualSendBookingPendingEmailResult.getMessage());
        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(Mockito.<MimeMessage>any());
        verify(configuration).getTemplate(Mockito.<String>any());
    }
}

