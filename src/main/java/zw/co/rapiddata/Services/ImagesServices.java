package zw.co.rapiddata.Services;

import jakarta.persistence.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.rapiddata.Models.Images;
import zw.co.rapiddata.Repositories.ImagesRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ImagesServices {

    private final PropertyServices propertyServices;

    private final ImagesRepository imagesRepository;

    private final AzureImagesBlobServices azureBlobServices;

    public ImagesServices(PropertyServices propertyServices, ImagesRepository imagesRepository, AzureImagesBlobServices azureBlobServices) {
        this.propertyServices = propertyServices;
        this.imagesRepository = imagesRepository;
        this.azureBlobServices = azureBlobServices;
    }

    public Images createImage(Images image) {
        return imagesRepository.save(image);
    }

    public Images updateImage(Long imageId, Images imageDetails) {
        Images image = imagesRepository.findById(imageId).orElseThrow(() -> new EntityNotFoundException("Image not found with id " + imageId));
        image.setImageUrl(imageDetails.getImageUrl());
        return imagesRepository.save(image);
    }

    public void deleteImage(Long imageId) {
        imagesRepository.deleteById(imageId);
    }

    public Images getImage(Long imageId) {
        return imagesRepository.findById(imageId).orElseThrow(() -> new EntityNotFoundException("Image not found with id " + imageId));
    }

    public Images upLoadImage(String originalFileName, String savedName, Long propertyId) {
        Images image = new Images(propertyServices.FindPropertyById(propertyId),originalFileName, savedName);
        return imagesRepository.save(image);
    }

    public List<Images> getImagesByPropertyId(Long propertyId) {
        return imagesRepository.findImagesByPropertyId(propertyId);
    }
}
