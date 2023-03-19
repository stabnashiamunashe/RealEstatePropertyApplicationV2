package zw.co.rapiddata.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Models.TitleDeeds;
import zw.co.rapiddata.Repositories.TitleDeedsRepository;
import java.util.List;

@Service
public class TitleDeedsServices {

    private final TitleDeedsRepository titleDeedsRepository;

    private final PropertyServices propertyServices;

    private final AzureTitleDeedsBlobServices azureTitleDeedsBlobServices;

    public TitleDeedsServices(TitleDeedsRepository titleDeedsRepository, PropertyServices propertyServices, AzureTitleDeedsBlobServices azureTitleDeedsBlobServices) {
        this.titleDeedsRepository = titleDeedsRepository;
        this.propertyServices = propertyServices;
        this.azureTitleDeedsBlobServices = azureTitleDeedsBlobServices;
    }


    public TitleDeeds saveTitleDeed(TitleDeeds deed) {
        return titleDeedsRepository.save(deed);
    }

    public String upLoadDeed(String originalFileName, String blobName, Long propertyId) {
        TitleDeeds deed;
        deed = new TitleDeeds(originalFileName, blobName, propertyServices.getProperty(propertyId));
        titleDeedsRepository.save(deed);
        return "Title Deed was Saved";
    }

    public ResponseEntity<?> downloadDeed(String imageIdentifier) {

        byte[] imageData = azureTitleDeedsBlobServices.downloadTitleDeed(imageIdentifier);
        return  ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE))
                .body(imageData);
    }

    public List<TitleDeeds> getTitleDeedsByPropertyId(Long id) {
        return titleDeedsRepository.findByProperty_Id(id);
    }
}
