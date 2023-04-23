package zw.co.rapiddata.Payments.Paynow.Configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "paynow")
public record PaynowProperties(String integrationId, String integrationKey, String returnUrl, String resultUrl) {
}
