package fact.it.subscriptionservice.service;

import fact.it.subscriptionservice.dto.SubscriptionResponse;
import fact.it.subscriptionservice.model.Subscription;
import fact.it.subscriptionservice.repository.SubscriptionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @PostConstruct
    public void loadData() {
        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        subscription1.setName("Basic");
        subscription1.setDescription("720p (HD) + 1 user");
        subscription1.setPrice(8);

        Subscription subscription2 = new Subscription();
        subscription2.setId(2L);
        subscription2.setName("Standard");
        subscription2.setDescription("1080p (Full HD) + 2 users");
        subscription2.setPrice(10);

        Subscription subscription3 = new Subscription();
        subscription3.setId(3L);
        subscription3.setName("Premium");
        subscription3.setDescription("4K (Ultra HD) + 3 users");
        subscription3.setPrice(12);

        subscriptionRepository.save(subscription1);
        subscriptionRepository.save(subscription2);
        subscriptionRepository.save(subscription3);
    }

    public List<SubscriptionResponse> getSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream().map(this::mapToSubscriptionResponse).toList();
    }

    public SubscriptionResponse getSubscriptionById(long subscriptionId) {
        Subscription subscription = subscriptionRepository.findSubscriptionById(subscriptionId);
        return mapToSubscriptionResponse(subscription);
    }

    private SubscriptionResponse mapToSubscriptionResponse(Subscription subscription) {
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .name(subscription.getName())
                .build();
    }
}
