package zw.co.rapiddata.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Models.OwnershipDocuments;
import zw.co.rapiddata.Repositories.OwnershipDocumentsRepository;

import java.util.List;

@Service
public class OwnershipDocumentsServices {

    private final OwnershipDocumentsRepository ownershipDocumentsRepository;

    private final PropertyServices propertyServices;

    private final AzureTitleDeedsBlobServices azureTitleDeedsBlobServices;

    public OwnershipDocumentsServices(OwnershipDocumentsRepository ownershipDocumentsRepository, PropertyServices propertyServices, AzureTitleDeedsBlobServices azureTitleDeedsBlobServices) {
        this.ownershipDocumentsRepository = ownershipDocumentsRepository;
        this.propertyServices = propertyServices;
        this.azureTitleDeedsBlobServices = azureTitleDeedsBlobServices;
    }


    public OwnershipDocuments saveTitleDeed(OwnershipDocuments ownershipDocuments) {
        return ownershipDocumentsRepository.save(ownershipDocuments);
    }

    public String upLoadDeed(String originalFileName, String blobName, Long propertyId) {
        OwnershipDocuments ownershipDocuments;
        ownershipDocuments = new OwnershipDocuments(originalFileName, blobName, propertyServices.getPropertyWithToken(propertyId));
        ownershipDocumentsRepository.save(ownershipDocuments);
        return "Ownership Document was Saved";
    }

    public ResponseEntity<?> downloadDeed(String imageIdentifier) {

        byte[] imageData = azureTitleDeedsBlobServices.downloadTitleDeed(imageIdentifier);
        return  ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE))
                .body(imageData);
    }

    public List<OwnershipDocuments> getTitleDeedsByPropertyId(Long id) {
        return ownershipDocumentsRepository.findByProperty_Id(id);
    }
}
