package br.com.tcia.eficienciaenergetica.email;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailAdapter {

    private final JavaMailSender mailSender;

    public void enviarEmail(String from, String to, String subject, String message) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);

            mailSender.send(mimeMessage);
            log.info("E-mail enviado para {}", to);
        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail para {}", to, e);
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }

    public void enviarComAnexo(String from, List<String> to, String subject, String message, File attachment) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to.toArray(String[]::new));
            helper.setSubject(subject);
            helper.setText(message, true);

            if (Objects.nonNull(attachment)) {
                helper.addAttachment(attachment.getName(), attachment);
            }

            mailSender.send(mimeMessage);
            log.info("E-mail com anexo enviado para {}", to);
        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail com anexo para {}", to, e);
            throw new RuntimeException("Erro ao enviar e-mail com anexo", e);
        }
    }
}
