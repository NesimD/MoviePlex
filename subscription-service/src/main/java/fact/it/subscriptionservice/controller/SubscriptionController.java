package fact.it.subscriptionservice.controller;

import fact.it.subscriptionservice.dto.SubscriptionResponse;
import fact.it.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("{id}")
    public ResponseEntity<SubscriptionResponse> getSubscriptionById(@PathVariable("id") long subscriptionId) {
        Optional<SubscriptionResponse> subscriptionResponseOptional = subscriptionService.getSubscriptionById(subscriptionId);

        if (subscriptionResponseOptional.isPresent()) {
            SubscriptionResponse subscriptionResponse = subscriptionResponseOptional.get();

            return new ResponseEntity<>(subscriptionResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubscriptionResponse> getSubscriptions() {
        return subscriptionService.getSubscriptions();
    }
}
