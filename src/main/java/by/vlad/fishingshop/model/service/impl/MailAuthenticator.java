package by.vlad.fishingshop.model.service.impl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class MailAuthenticator extends Authenticator {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(
                MailSenderServiceImpl.getEmailSender(),
                MailSenderServiceImpl.getEmailPassword());
    }
}
