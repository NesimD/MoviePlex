package fact.it.subscriptionservice;

import fact.it.subscriptionservice.dto.SubscriptionResponse;
import fact.it.subscriptionservice.model.Subscription;
import fact.it.subscriptionservice.repository.SubscriptionRepository;
import fact.it.subscriptionservice.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceApplicationTests {

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void testGetSubscriptionById() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setName("Basis");
        subscription.setDescription("1 user + HD");
        subscription.setPrice(9);

        when(subscriptionRepository.findSubscriptionById(1L)).thenReturn(subscription);

        // Act
        SubscriptionResponse subscriptions = subscriptionService.getSubscriptionById(1L);

        // Assert
        assertEquals("Basis", subscriptions.getName());

        verify(subscriptionRepository, times(1)).findSubscriptionById(1L);
    }

    @Test
    public void testGetAllSubscriptions() {
        // Arrange
        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        subscription1.setName("Basis");
        subscription1.setDescription("1 user + HD");
        subscription1.setPrice(9);

        Subscription subscription2 = new Subscription();
        subscription2.setId(2L);
        subscription2.setName("Standard");
        subscription2.setDescription("2 users + full HD");
        subscription2.setPrice(12);

        when(subscriptionRepository.findAll()).thenReturn(Arrays.asList(subscription1, subscription2));

        // Act
        List<SubscriptionResponse> subscriptions = subscriptionService.getSubscriptions();

        // Assert
        assertEquals(2, subscriptions.size());
        assertEquals("Basis", subscriptions.get(0).getName());
        assertEquals("Standard", subscriptions.get(1).getName());

        verify(subscriptionRepository, times(1)).findAll();
    }
}
