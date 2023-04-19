package com.example.soundcloudfinalprojectittalentss15.services;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user, String siteURL) {
        new Thread(() -> {
            try {
                String toAddress = user.getEmail();
                String fromAddress = "vasilmomchiltalents@gmail.com";
                String senderName = "Sound Cloud";
                String subject = "Please verify your registration";
                String content = "Dear [[name]],<br>"
                        + "Please click the link below to verify your registration:<br>"
                        + "<h3><a href=\"[[URL]]\" target=\"_self\">CLICK HERE TO VERIFY</a></h3>"
                        + "Thank you,<br>"
                        + "Sound Cloud.";

                //MimeMessage is a class in the JavaMail API, which represents a more complex and flexible email message than the basic Message class.
                // MIME (Multipurpose Internet Mail Extensions) is an extension of the original Internet email protocol that enables the support of non-text content,
                // such as images, audio, or non-ASCII text characters in email messages.
                //MimeMessage allows you to create an email message with multiple parts, such as plain text, HTML, inline images, or attachments,
                // which can be organized in a tree structure.
                // It provides methods to set various email properties, such as the sender, recipients, subject, and content.
                MimeMessage message = mailSender.createMimeMessage();
                //The MimeMessageHelper is a helper class provided by the Spring Framework to simplify the creation of MIME messages.
                // It allows you to set the sender, recipient, subject, and content of the email more easily.
                MimeMessageHelper helper = new MimeMessageHelper(message);
                helper.setFrom(fromAddress, senderName);
                helper.setTo(toAddress);
                helper.setSubject(subject);
                content = content.replace("[[name]]", toAddress.substring(0, toAddress.indexOf('@')));
                helper.setText(content, true);
                String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
                content = content.replace("[[URL]]", verifyURL);
                helper.setText(content, true);
                mailSender.send(message);
            }  catch (MessagingException | UnsupportedEncodingException e) {
                throw new BadRequestException(e.getMessage());
            }
        }).start();
    }

    public void sendResetPasswordEmail(User user, String siteURL) {
        String fromAddress = "vasilmomchiltalents@gmail.com";
        String subject = "Reset Password";
        String resetURL = siteURL + "/reset-password";
        String content = "Your reset password code is : " + user.getResetCode() + "\n" +
                "To reset your password, please click the link below:\n" + resetURL;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromAddress);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(content);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending reset password email.", e);
        }
    }
}
