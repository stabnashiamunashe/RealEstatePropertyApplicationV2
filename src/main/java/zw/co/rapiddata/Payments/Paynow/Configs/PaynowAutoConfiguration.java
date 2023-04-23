package zw.co.rapiddata.Payments.Paynow.Configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.paynow.core.Paynow;

@Configuration
@ConditionalOnClass(Paynow.class)
@ConditionalOnMissingBean(Paynow.class)
@Slf4j
public class PaynowAutoConfiguration {

    private final PaynowProperties paynowProperties;

    public PaynowAutoConfiguration(PaynowProperties paynowProperties) {
        this.paynowProperties = paynowProperties;
    }

    @Bean
    public Paynow paynow() {

        try {
            if (isNotNullOrEmpty(paynowProperties.integrationId()) && isNotNullOrEmpty(paynowProperties.integrationKey())) {
                log.info("Paynow auto-configuration has been effected with " + "integration Id: " + paynowProperties.integrationId());
                return new Paynow(paynowProperties.integrationId(), paynowProperties.integrationKey());
            } else {
                throw new BeanCreationException("Failed to create Paynow bean. Please specify a value for integration ID and integration key in your application properties file.");
            }
        } catch (NullPointerException ex) {
            throw new BeanCreationException("Failed to create Paynow bean. Please add the require properties of an integration ID and integration key in your application properties file.");
        }

    }

    private static boolean isNotNullOrEmpty(String str) {
        return str != null || !str.trim().isEmpty();
    }

}