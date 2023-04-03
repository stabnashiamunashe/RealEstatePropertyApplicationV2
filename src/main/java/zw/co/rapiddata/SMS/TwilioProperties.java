package zw.co.rapiddata.SMS;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "twilio")
public record TwilioProperties(String accountSid, String authToken, String phoneNumber) {
}

