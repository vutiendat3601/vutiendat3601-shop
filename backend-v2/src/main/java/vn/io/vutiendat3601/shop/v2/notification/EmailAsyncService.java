package vn.io.vutiendat3601.shop.v2.notification;

import java.util.concurrent.CompletableFuture;

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

  public CompletableFuture<Boolean> sendHtmlMail(
      @NonNull String to, @NonNull String subject, @NonNull String content) {
    final MimeMessage message = mailSender.createMimeMessage();
    try {
      final MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
      messageHelper.setTo(to);
      messageHelper.setSubject(subject);
      messageHelper.setText(content, true);
      mailSender.send(message);
    } catch (MessagingException e) {
      log.error(e.getMessage(), e);
      return CompletableFuture.completedFuture(true);
    }
    return CompletableFuture.completedFuture(false);
  }
}
