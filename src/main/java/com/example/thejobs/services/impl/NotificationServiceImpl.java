package com.example.thejobs.services.impl;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.services.NotificationService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender mailSender;
    private final Configuration config;

    @Override
    public ResponsePayload sendBookingConfirmEmail(String toEmail, Map<String, Object> model){


        MimeMessage message = mailSender.createMimeMessage();
        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            Template t = config.getTemplate("booking-confirm-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(toEmail);
            helper.setText(html, true);
            helper.setSubject("One to One Session Confirmation Email");
            helper.setFrom("navodsachinth@gmail.com");
            mailSender.send(message);


            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Email Send Successfully",HttpStatus.OK);
        } catch (MessagingException | IOException | TemplateException e) {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Email Failed",HttpStatus.BAD_REQUEST);

        }
    }

    @Override
    public ResponsePayload sendBookingPendingEmail(String toEmail, Map<String, Object> model) {
        MimeMessage message = mailSender.createMimeMessage();
        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            Template t = config.getTemplate("booking-pending-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(toEmail);
            helper.setText(html, true);
            helper.setSubject("Thank You for Connecting with The Jobs");
            helper.setFrom("navodsachinth@gmail.com");
            mailSender.send(message);


            return new ResponsePayload(HttpStatus.OK.getReasonPhrase(), "Email Send Successfully",HttpStatus.OK);
        } catch (MessagingException | IOException | TemplateException e) {
            return new ResponsePayload(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Email Failed",HttpStatus.BAD_REQUEST);

        }
    }
}
