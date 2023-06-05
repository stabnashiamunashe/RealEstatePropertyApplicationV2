package zw.co.rapiddata.Subscriptions;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zw.co.rapiddata.Services.PropertyOwnerServices;

@Component
public class SubscriptionJob {

    private final SubscriptionService subscriptionService;

    public SubscriptionJob(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
    public void deactivateExpiredSubscriptions() {
        subscriptionService.deactivateSubscriptions();
    }
}