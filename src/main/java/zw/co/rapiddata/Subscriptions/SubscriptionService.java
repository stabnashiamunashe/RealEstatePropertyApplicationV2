package zw.co.rapiddata.Subscriptions;

import org.springframework.stereotype.Service;
import zw.co.rapiddata.Models.Property;
import zw.co.rapiddata.Models.PropertyOwner;
import zw.co.rapiddata.Services.PropertyOwnerServices;
import zw.co.rapiddata.Services.PropertyServices;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionService {

    private final PropertyOwnerServices propertyOwnerServices;

    private final PropertyServices propertyServices;

    private final SubscriptionsRepository subscriptionsRepository;

    public SubscriptionService(PropertyOwnerServices propertyOwnerServices, PropertyServices propertyServices, SubscriptionsRepository subscriptionsRepository) {
        this.propertyOwnerServices = propertyOwnerServices;
        this.propertyServices = propertyServices;
        this.subscriptionsRepository = subscriptionsRepository;
    }

    public void deactivateSubscriptions() {
        var owners = propertyOwnerServices.findAllOwnersWithExpiredSubscriptions();
        for(PropertyOwner owner: owners) {
            owner.setActive(false);
            propertyServices.findPropertiesByPropertyOwner(owner.getEmail());
            for(Property property: owner.getProperties()) {
                property.setActive(false);
                propertyServices.save(property);
            }
            owner.setSubscriptionExpiryDate(null);
            propertyOwnerServices.save(owner);
        }
    }

    public void activateSubscription(String email) {
        var properties = propertyServices.findPropertiesByPropertyOwner(email);
        var owner = propertyOwnerServices.getPropertyOwnerByEmail(email);
        for(Property property: properties) {
            property.setActive(true);
            propertyServices.save(property);
        }
        if(owner.getSubscriptionExpiryDate() == null) {
            owner.setSubscriptionExpiryDate(LocalDate.now().plusMonths(6));
        } else{
            owner.setSubscriptionExpiryDate(owner.getSubscriptionExpiryDate().plusMonths(6));
        }
        owner.setActive(true);
        propertyOwnerServices.save(owner);
    }

    public Subscriptions saveSubscriptions(Subscriptions subscriptions){
        return subscriptionsRepository.save(subscriptions);
    }

    public List<Subscriptions> getSubscriptions() {
        return subscriptionsRepository.findAll();
    }

    public Subscriptions getSubscriptionById(Long id) {
        return subscriptionsRepository.findById(id).orElseThrow();
    }

    public Subscriptions getLatestPropertyOwnerSubscription() {
        return subscriptionsRepository.findTopBySubscriptionTypeOrderByDateCreatedDesc(SubscriptionType.PROPERTY_OWNER);
    }

    public Subscriptions getLatestPropertyTenantSubscription() {
        return subscriptionsRepository.findTopBySubscriptionTypeOrderByDateCreatedDesc(SubscriptionType.TENANT);
    }
}
