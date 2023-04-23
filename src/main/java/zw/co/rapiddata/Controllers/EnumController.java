package zw.co.rapiddata.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.rapiddata.Models.*;

@RestController
@RequestMapping("/enums")
public class EnumController {

    @GetMapping("/city")
    public City[] getAllCities() {
        return City.values();
    }

    @GetMapping("/property_types")
    public PropertyType[] getAllPropertyTypes(){
        return PropertyType.values();
    }

    @GetMapping("/densities")
    public Density[] getAllDensities(){
        return Density.values();
    }

    @GetMapping("/onwership-types")
    public PropertyOwnershipType[] getAllPropertyOwnershipTypes(){
        return PropertyOwnershipType.values();
    }

    @GetMapping("/conditions")
    public Conditions[] getAllConditions(){
        return Conditions.values();
    }
}
