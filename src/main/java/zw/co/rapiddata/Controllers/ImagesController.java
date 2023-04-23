package zw.co.rapiddata.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.rapiddata.Models.Images;
import zw.co.rapiddata.Services.AzureImagesBlobServices;
import zw.co.rapiddata.Services.ImagesServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {

    private final ImagesServices imagesServices;

    private final AzureImagesBlobServices azureBlobServices;

    public ImagesController(ImagesServices imagesServices, AzureImagesBlobServices azureBlobServices) {
        this.imagesServices = imagesServices;
        this.azureBlobServices = azureBlobServices;
    }

    @GetMapping("/entity/{id}")
    public List<Images> getImagesByPropertyId(@PathVariable Long id){
        return imagesServices.getImagesByPropertyId(id);
    }

    @GetMapping("/download/{url}")
    public ResponseEntity<?> downloadImageByUrl(@PathVariable String url){
        return azureBlobServices.download(url);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long propertyId) {
        try {
            String savedName;
            String originalFileName;
            UUID uuid = UUID.randomUUID();
            originalFileName = file.getOriginalFilename();
            savedName = uuid.toString();
            azureBlobServices.uploadFile(file, savedName);
            return ResponseEntity.status(HttpStatus.CREATED).body(imagesServices.upLoadImage(originalFileName, savedName, propertyId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file.");
        }
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") List<MultipartFile> files, @RequestParam Long propertyId) {
        try {
            azureBlobServices.uploadFiles(files, propertyId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Property Images added!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading files.");
        }
    }
}
