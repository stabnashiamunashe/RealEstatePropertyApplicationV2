package zw.co.rapiddata.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zw.co.rapiddata.Models.Images;
import zw.co.rapiddata.Models.TitleDeeds;
import zw.co.rapiddata.Services.AzureTitleDeedsBlobServices;
import zw.co.rapiddata.Services.TitleDeedsServices;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deeds")
public class TitleDeedsController {

    private final AzureTitleDeedsBlobServices azureTitleDeedsBlobServices;

    private final TitleDeedsServices titleDeedsServices;

    public TitleDeedsController(AzureTitleDeedsBlobServices azureTitleDeedsBlobServices, TitleDeedsServices titleDeedsServices) {
        this.azureTitleDeedsBlobServices = azureTitleDeedsBlobServices;
        this.titleDeedsServices = titleDeedsServices;
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
        return titleDeedsServices.upLoadDeed(originalFileName, savedName, propertyId);
    }

    @GetMapping("/{id}")
    public List<TitleDeeds> getTitleDeedsByPropertyId(@PathVariable Long id){
        return titleDeedsServices.getTitleDeedsByPropertyId(id);
    }

    @GetMapping("/download/{url}")
    public ResponseEntity<?> getTitleDeedByUrl(@PathVariable String url){
        return titleDeedsServices.downloadDeed(url);
    }
}
