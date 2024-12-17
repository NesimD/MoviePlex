package fact.it.subscriptionservice.repository;

import fact.it.subscriptionservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findSubscriptionById(Long id);
}
