package zw.co.rapiddata.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.rapiddata.DTOs.PropertyDTO;
import zw.co.rapiddata.Models.Coordinates;
import zw.co.rapiddata.Models.Property;
import zw.co.rapiddata.Services.PropertyServices;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/coordinates")
public class CoordinatesController {

    private final PropertyServices propertyServices;

    public CoordinatesController(PropertyServices propertyServices) {
        this.propertyServices = propertyServices;
    }

    @GetMapping
    public List<Coordinates> getAllCoordinates() {
        List<PropertyDTO> properties = propertyServices.getAllProperties();
        List<Coordinates> coordinatesList = new ArrayList<>();

        for (PropertyDTO property : properties) {
            coordinatesList.add(property.getCoordinates());
        }

        return coordinatesList;
    }
}
