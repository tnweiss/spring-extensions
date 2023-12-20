package dev.tdub.springext.email;

import java.util.Properties;

import dev.tdub.springext.error.exceptions.InternalServerException;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


public class SmptEmailClient implements EmailClient {
  private final String from;
  private final Session session;

  public SmptEmailClient(String from, String host, String port, String username, String password) {
    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.auth", "true");
    this.session = Session.getDefaultInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
    this.from = from;
  }
  @Override
  public void send(Email recipient, String subject, String body) {
    Message msg = new MimeMessage(session);
    try {
      msg.setFrom(new InternetAddress(from));
      msg.setRecipient(Message.RecipientType.TO, recipient.getInternetAddress());
      msg.setSubject(subject);
      msg.setContent(body, "text/html; charset=utf-8");
      Transport.send(msg);
    } catch (MessagingException e) {
      throw new InternalServerException(e);
    }
  }

}
