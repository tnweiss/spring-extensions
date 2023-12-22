package dev.tdub.springext.email;

public interface EmailClient {
    void send(Email recipient, String subject, String body);
}
