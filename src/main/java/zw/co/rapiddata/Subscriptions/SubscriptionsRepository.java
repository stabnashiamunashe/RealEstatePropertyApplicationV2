package zw.co.rapiddata.Subscriptions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {

    Subscriptions findTopBySubscriptionTypeOrderByDateCreatedDesc(SubscriptionType subscriptionType);

}
