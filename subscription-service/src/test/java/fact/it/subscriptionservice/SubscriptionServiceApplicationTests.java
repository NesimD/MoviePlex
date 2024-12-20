package fact.it.subscriptionservice;

import fact.it.subscriptionservice.dto.SubscriptionResponse;
import fact.it.subscriptionservice.model.Subscription;
import fact.it.subscriptionservice.repository.SubscriptionRepository;
import fact.it.subscriptionservice.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceApplicationTests {

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @BeforeEach
    public void setUp() {
        subscriptionService.loadData();
    }

    @Test
    public void testLoadData() {
        verify(subscriptionRepository, times(1)).save(argThat(subscription -> subscription.getName().equals("Basic")));
        verify(subscriptionRepository, times(1)).save(argThat(subscription -> subscription.getName().equals("Standard")));
        verify(subscriptionRepository, times(1)).save(argThat(subscription -> subscription.getName().equals("Premium")));
    }

    @Test
    public void testGetSubscriptionByIdSubscriptionFound() {
        // Arrange
        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        subscription1.setName("Basis");
        subscription1.setDescription("1 user + HD");
        subscription1.setPrice(9);

        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription1));

        // Act
        Optional<SubscriptionResponse> subscriptionResponseOptional = subscriptionService.getSubscriptionById(1L);

        // Assert
        assertTrue(subscriptionResponseOptional.isPresent());
        assertEquals("Basis", subscriptionResponseOptional.get().getName());

        verify(subscriptionRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetSubscriptionByIdSubscriptionNotFound() {
        // Arrange
        when(subscriptionRepository.findById(10L)).thenReturn(Optional.empty());

        // Act
        Optional<SubscriptionResponse> subscriptionResponseOptional = subscriptionService.getSubscriptionById(10L);

        // Assert
        assertEquals(Optional.empty(), subscriptionResponseOptional);
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
