package zw.co.rapiddata.SMS;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class TwilioService {
    private final TwilioProperties twilioProperties;

    public TwilioService(TwilioProperties twilioProperties) {
        this.twilioProperties = twilioProperties;
    }

    public int generateVerificationCode(){
        // Generate a random 6-digit verification code
        Random rand = new Random();
        return rand.nextInt(999999);
    }

    public boolean sendSmsVerificationCode(String phoneNumber, int verificationCode) throws Exception {
        try{
            Twilio.init(twilioProperties.accountSid(),twilioProperties.authToken());
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(phoneNumber),
                            new com.twilio.type.PhoneNumber(twilioProperties.phoneNumber()),
                            "Your RapidData registration verification code is : " + verificationCode + "\nPlease do not give away this code! and ingnore it if you did not request for it. Someone must have entered a wrong number.")
                    .create();
            log.info("Sent SMS verification code to " + phoneNumber);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

    }
}