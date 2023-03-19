package zw.co.rapiddata;

import zw.co.rapiddata.Config.Configss.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import zw.co.rapiddata.Services.AzureBlobProperties;

@EnableConfigurationProperties({RSAKeyProperties.class, AzureBlobProperties.class})
@SpringBootApplication
public class RealEstateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateApplication.class, args);
	}

}
