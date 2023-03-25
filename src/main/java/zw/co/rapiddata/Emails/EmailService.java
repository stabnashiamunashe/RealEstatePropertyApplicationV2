package zw.co.rapiddata.Emails;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailService {

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean sendVerificationCode(String email, int verificationCode) throws MessagingException {

        // Set up mail server properties
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Create a session with the mail server
        Session session = Session.getDefaultInstance(properties,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("your_email_address", "your_email_password");
                    }
                });

        try {
            // Create a new message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email_address"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Verification Code");
            message.setText("Your verification code is: " + verificationCode);

            // Send the message
            Transport.send(message);

            // Return true to indicate that the verification code was sent successfully
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int generateVerificationCode(){
        // Generate a random 6-digit verification code
        Random rand = new Random();
        return rand.nextInt(999999);
    }
}