package vn.io.vutiendat3601.shop.v2.notification;

import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Async
@Service
public class EmailAsyncService {
  private final JavaMailSender mailSender;

  public void sendEmail(@NonNull String to, @NonNull String subject, @NonNull String content) {
    final MimeMessage message = mailSender.createMimeMessage();
    try {
      final MimeMessageHelper messageHelper = new MimeMessageHelper(message, false);
      messageHelper.setTo(to);
      messageHelper.setSubject(subject);
      messageHelper.setText(content);
      mailSender.send(message);
    } catch (MessagingException e) {
      log.error(e.getMessage(), e);
    }
  }
}
