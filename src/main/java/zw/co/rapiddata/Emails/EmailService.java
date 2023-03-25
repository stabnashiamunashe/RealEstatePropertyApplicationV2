package zw.co.rapiddata.Emails;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

//    public boolean isValidEmail(String email) {
//        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean sendVerificationCode(String email, int verificationCode) {

        SimpleMailMessage message= new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        // Send the message

        javaMailSender.send(message);

        // Return true to indicate that the verification code was sent successfully
        return true;
    }

    public int generateVerificationCode(){
        // Generate a random 6-digit verification code
        Random rand = new Random();
        return rand.nextInt(999999);
    }
}