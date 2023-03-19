package zw.co.rapiddata.Services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
@Slf4j
public class AzureTitleDeedsBlobServices {

    private final AzureBlobProperties azureBlobProperties;

    public AzureTitleDeedsBlobServices(AzureBlobProperties azureBlobProperties) {
        this.azureBlobProperties = azureBlobProperties;
    }

    private BlobContainerClient containerClient() {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(azureBlobProperties.connectionstring()).buildClient();
        BlobContainerClient container = serviceClient.getBlobContainerClient(azureBlobProperties.titledeedscontainer());
        return container;
    }

    public String storeDeed(String filename, InputStream content, Long length) {

        BlobClient client = containerClient().getBlobClient(filename);
        if (client.exists()) {
        } else {
            client.upload(content, length);
        }
        return "Deed uploaded with success!";
    }


    public byte[] downloadTitleDeed(String blobName) {
        try {
            BlobContainerClient containerClient = containerClient();
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blobClient.download(outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


}
