package zw.co.rapiddata.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.rapiddata.Models.OwnershipDocuments;
import zw.co.rapiddata.Services.AzureTitleDeedsBlobServices;
import zw.co.rapiddata.Services.OwnershipDocumentsServices;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deeds")
public class TitleDeedsController {

    private final AzureTitleDeedsBlobServices azureTitleDeedsBlobServices;

    private final OwnershipDocumentsServices ownershipDocumentsServices;

    public TitleDeedsController(AzureTitleDeedsBlobServices azureTitleDeedsBlobServices, OwnershipDocumentsServices ownershipDocumentsServices) {
        this.azureTitleDeedsBlobServices = azureTitleDeedsBlobServices;
        this.ownershipDocumentsServices = ownershipDocumentsServices;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
    public String addTitleDeed(@RequestParam Long propertyId,
                               @RequestParam MultipartFile file) throws IOException {

        String savedName;
        String originalFileName;
        UUID uuid = UUID.randomUUID();
        originalFileName = file.getOriginalFilename();
        savedName = uuid.toString();
        azureTitleDeedsBlobServices.storeDeed(savedName , file.getInputStream(), file.getSize());
        return ownershipDocumentsServices.upLoadDeed(originalFileName, savedName, propertyId);
    }

    @GetMapping("/{id}")
    public List<OwnershipDocuments> getTitleDeedsByPropertyId(@PathVariable Long id){
        return ownershipDocumentsServices.getTitleDeedsByPropertyId(id);
    }

    @GetMapping("/download/{url}")
    public ResponseEntity<?> getTitleDeedByUrl(@PathVariable String url){
        return ownershipDocumentsServices.downloadDeed(url);
    }
}
