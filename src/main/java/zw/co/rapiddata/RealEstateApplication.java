package zw.co.rapiddata;

import org.springframework.cache.annotation.EnableCaching;
import zw.co.rapiddata.Payments.PayPal.PayPalProperties;
import zw.co.rapiddata.Payments.Paynow.Configs.PaynowProperties;
import zw.co.rapiddata.SMS.TwilioProperties;
import zw.co.rapiddata.Security.Configss.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import zw.co.rapiddata.Services.AzureBlobProperties;


@EnableCaching
@EnableConfigurationProperties({RSAKeyProperties.class, AzureBlobProperties.class, TwilioProperties.class, PaynowProperties.class, PayPalProperties.class})
@SpringBootApplication
public class RealEstateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateApplication.class, args);
	}

}
