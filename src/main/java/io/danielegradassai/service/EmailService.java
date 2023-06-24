package io.danielegradassai.service;

import io.danielegradassai.entity.User;

public interface EmailService {
    public void sendConfirmationEmail(User recipient);
}
