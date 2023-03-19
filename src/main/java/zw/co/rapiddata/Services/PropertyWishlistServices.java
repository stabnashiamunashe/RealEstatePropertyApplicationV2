package zw.co.rapiddata.Services;

import org.springframework.stereotype.Service;
import zw.co.rapiddata.Config.Models.Users;
import zw.co.rapiddata.Config.Repositories.UserRepository;
import zw.co.rapiddata.Models.PropertyWishlist;
import zw.co.rapiddata.Repositories.PropertyWishlistRepository;

import java.security.Principal;
import java.util.List;

@Service
public class PropertyWishlistServices {

    private final PropertyWishlistRepository propertyWishlistRepository;
    private final UserRepository userRepository;

    public PropertyWishlistServices(PropertyWishlistRepository propertyWishlistRepository, UserRepository userRepository) {
        this.propertyWishlistRepository = propertyWishlistRepository;
        this.userRepository = userRepository;
    }

    public String addPropertyToWishlist(Long propertyId, Principal principal) {
        PropertyWishlist propertyWishlist = new PropertyWishlist();
        propertyWishlist.setPropertyId(propertyId);
        Users user = userRepository.findByEmail(principal.getName()).orElse(null);
        assert user != null;
        propertyWishlist.setTenantId(user.getId());
        propertyWishlistRepository.save(propertyWishlist);
        return "Saved to WishList!";
    }

    public List<PropertyWishlist> getPropertyWishlistOfTenant(Long id) {
        return propertyWishlistRepository.findByTenantId(id);
    }
}
