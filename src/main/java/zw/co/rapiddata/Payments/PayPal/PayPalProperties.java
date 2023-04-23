package zw.co.rapiddata.Payments.PayPal;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "paypal")
public record PayPalProperties(String clientId, String clientSecret, String mode) {
}
