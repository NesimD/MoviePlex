package fact.it.subscriptionservice.controller;

import fact.it.subscriptionservice.dto.SubscriptionResponse;
import fact.it.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public SubscriptionResponse getSubscriptionById(@PathVariable("id") long subscriptionId) {
        return subscriptionService.getSubscriptionById(subscriptionId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubscriptionResponse> getSubscriptions() {
        return subscriptionService.getSubscriptions();
    }
}
