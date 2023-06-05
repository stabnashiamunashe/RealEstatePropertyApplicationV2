package zw.co.rapiddata.Subscriptions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/subscriptions")
public class SubscriptionsController {

    private final SubscriptionService subscriptionService;

    public SubscriptionsController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    @PostMapping("/save")
    public Subscriptions saveSubscriptions(Subscriptions subscriptions){
        return subscriptionService.saveSubscriptions(subscriptions);
    }

    @GetMapping("/get")
    public List<Subscriptions> getSubscriptions(){
        return subscriptionService.getSubscriptions();
    }

}
