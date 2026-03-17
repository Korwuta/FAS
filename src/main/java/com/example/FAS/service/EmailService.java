package com.example.FAS.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend-url}")
    private String frontendUrl;
    private final String companyName = "Nexus Assets";


    @Async
    public void sendPasswordResetEmail(String toEmail,String firstName, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Password Reset Request");

            Context context = new Context();
            context.setVariable("token", token);
            context.setVariable("resetUrl", frontendUrl + "/set-new-password?token=" + token);
            context.setVariable("userName", firstName);
            context.setVariable("frontendUrl", frontendUrl);

            String htmlContent = templateEngine.process("password-reset-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Password reset email sent to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage());
        }
    }

    public void sendAccountSetupMail(String email, String firstName, String resetToken) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setFrom(fromEmail);
            helper.setSubject("Welcome to " + companyName + " - Set Up Your Password");

            String setupUrl = frontendUrl + "/set-new-password?token=" + resetToken;
            Context context = new Context();
            context.setVariable("firstName", firstName);
            context.setVariable("email", email);
            context.setVariable("setupUrl", setupUrl);
            context.setVariable("frontendUrl", frontendUrl + "/login");
            context.setVariable("companyName", companyName);

            String htmlContent = templateEngine.process("password-reset-email", context);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);

            log.info("Welcome email sent to: {}", email);
        } catch (Exception e) {
            log.error("Failed to send welcome email to: {}", email, e);
        }
    }

    @Async
    public void sendEmailWithMultipleAttachments(String toEmail, String subject, String htmlContent,
                                                 Map<String, String> attachments) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            // Add multiple file attachments
            for (Map.Entry<String, String> attachment : attachments.entrySet()) {
                String attachmentName = attachment.getKey();
                String attachmentPath = attachment.getValue();
                FileSystemResource file = new FileSystemResource(new File(attachmentPath));
                helper.addAttachment(attachmentName, file);
            }

            mailSender.send(message);
            log.info("Email with {} attachments sent to: {}", attachments.size(), toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email with attachments to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendEmailWithByteArrayAttachment(String toEmail, String subject, String htmlContent,
                                                 byte[] attachmentData, String attachmentName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            // Add byte array attachment
            ByteArrayResource byteArrayResource = new ByteArrayResource(attachmentData);
            helper.addAttachment(attachmentName, byteArrayResource);

            mailSender.send(message);
            log.info("Email with byte array attachment sent to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email with byte array attachment to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendEmailWithMultipartFileAttachment(String toEmail, String subject, String htmlContent,
                                                     MultipartFile file) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            // Add MultipartFile attachment
            ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes());
            helper.addAttachment(file.getOriginalFilename(), byteArrayResource);

            mailSender.send(message);
            log.info("Email with multipart file attachment sent to: {}", toEmail);
        } catch (MessagingException | IOException e) {
            log.error("Failed to send email with multipart file attachment to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendEmailWithMultipleMultipartFiles(String toEmail, String subject, String htmlContent,
                                                    List<MultipartFile> files) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            // Add multiple MultipartFile attachments
            for (MultipartFile file : files) {
                ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes());
                helper.addAttachment(file.getOriginalFilename(), byteArrayResource);
            }

            mailSender.send(message);
            log.info("Email with {} multipart file attachments sent to: {}", files.size(), toEmail);
        } catch (MessagingException | IOException e) {
            log.error("Failed to send email with multipart file attachments to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendTemplatedEmailWithAttachment(String toEmail, String subject, String templateName,
                                                 Context templateContext, String attachmentPath,
                                                 String attachmentName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);

            // Process template
            String htmlContent = templateEngine.process(templateName, templateContext);
            helper.setText(htmlContent, true);

            // Add file attachment
            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            helper.addAttachment(attachmentName, file);

            mailSender.send(message);
            log.info("Templated email with attachment sent to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send templated email with attachment to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendEmailWithResourceAttachment(String toEmail, String subject, String htmlContent,
                                                Resource attachment, String attachmentName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            // Add Resource attachment (can be any Spring Resource)
            helper.addAttachment(attachmentName, attachment);

            mailSender.send(message);
            log.info("Email with resource attachment sent to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email with resource attachment to {}: {}", toEmail, e.getMessage());
        }
    }
}