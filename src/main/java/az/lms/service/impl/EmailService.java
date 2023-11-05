package az.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;


/**
 * @author Mehman Osmanov on 04.11.23
 * @project LMS
 */
@Service
@RequiredArgsConstructor
public class EmailService {

   private final JavaMailSender javaMailSender;

   public void sendEmail(String to, String subject, String text) throws MessagingException {

      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(to);
      message.setSubject(subject);
      message.setText(text);

      javaMailSender.send(message);
   }
}