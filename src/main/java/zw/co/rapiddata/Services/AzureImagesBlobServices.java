package zw.co.rapiddata.Services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobProperties;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zw.co.rapiddata.Models.Images;
import zw.co.rapiddata.Repositories.ImagesRepository;
import zw.co.rapiddata.Repositories.PropertyRepository;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AzureImagesBlobServices {
    private final PropertyRepository propertyRepository;

    private final AzureBlobProperties azureBlobProperties;

    private final ImagesRepository imagesRepository;

    public AzureImagesBlobServices(AzureBlobProperties azureBlobProperties, ImagesRepository imagesRepository,
                                   PropertyRepository propertyRepository) {
        this.azureBlobProperties = azureBlobProperties;
        this.imagesRepository = imagesRepository    ;
        this.propertyRepository = propertyRepository;
    }

    private BlobContainerClient containerClient() {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(azureBlobProperties.connectionstring()).buildClient();
        return serviceClient.getBlobContainerClient(azureBlobProperties.imagescontainer());
    }

    public ResponseEntity<?> download(String imageIdentifier) {

        BlobContainerClient containerClient = containerClient();
        BlobClient blobClient = containerClient.getBlobClient(imageIdentifier);

        BlobProperties properties = blobClient.getProperties();
        String contentType = properties.getContentType();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClient.download(outputStream);

        String cdnUrl = azureBlobProperties.cdnEndpoint() + imageIdentifier + azureBlobProperties.sastoken();
        URI signedUri = URI.create(cdnUrl);

        // Retrieve the image bytes from the signed URL
        try {
            byte[] content = HttpClient.newHttpClient().send(HttpRequest.newBuilder(signedUri).GET().build(), HttpResponse.BodyHandlers.ofByteArray()).body();
            return  ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(content);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FOUND);
         }
    }

    public void uploadFile(MultipartFile file, String name) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            File resizedFile = File.createTempFile("resized-", "-" + file.getOriginalFilename());
            file.transferTo(resizedFile);

            Thumbnails.of(resizedFile)
                    .size(1000, 1000) // resize image to 1000x1000
                    .outputQuality(1) // set image quality to 100%
                    .toOutputStream(outputStream); // write the output to the ByteArrayOutputStream
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        byte[] imageBytes = outputStream.toByteArray();

        // upload the compressed and resized image to Azure Blob Storage
        String contentType = file.getContentType();
        BlobContainerClient containerClient = containerClient();
        BlobClient blobClient = containerClient.getBlobClient(name);
        blobClient.upload(new ByteArrayInputStream(imageBytes), imageBytes.length, true);
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(contentType);
        blobClient.setHttpHeaders(headers);
    }

    public void uploadFiles(List<MultipartFile> files,Long propertyId) throws Exception {
        String name = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for(MultipartFile file : files){
             try {
                 UUID uuid = UUID.randomUUID();
                 name = uuid.toString();

                 File resizedFile = File.createTempFile("resized-", "-" + file.getOriginalFilename());
                 file.transferTo(resizedFile);

                 Thumbnails.of(resizedFile)
                       .size(750, 750) // resize image to 750x750
                       .outputQuality(1) // set image quality to 100%
                       .toOutputStream(outputStream); // write the output to the ByteArrayOutputStream
             } catch (Exception e) {
                  log.error(e.getMessage());
             }
            byte[] imageBytes = outputStream.toByteArray();

            // upload the compressed and resized image to Azure Blob Storage
            String contentType = file.getContentType();
            BlobContainerClient containerClient = containerClient();
            BlobClient blobClient = containerClient.getBlobClient(name);
            blobClient.upload(new ByteArrayInputStream(imageBytes), imageBytes.length, true);
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(contentType);
            blobClient.setHttpHeaders(headers);
            var property = propertyRepository.findById(propertyId).orElse(null);
            Images image = new Images(property, file.getOriginalFilename(), name);
            imagesRepository.save(image);
        }

    }

}

