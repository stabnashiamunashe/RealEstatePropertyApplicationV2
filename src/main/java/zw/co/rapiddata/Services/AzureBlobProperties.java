package zw.co.rapiddata.Services;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("azure.properties")
public record AzureBlobProperties(String connectionstring, String imagescontainer, String titledeedscontainer, String cdnEndpoint, String sastoken) {
}
