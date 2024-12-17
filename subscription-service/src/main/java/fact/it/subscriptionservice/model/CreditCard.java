package fact.it.subscriptionservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cardHolderFirstName;
    private String cardHolderLastName;
    private String securityCode;
    private Long userId;
}
