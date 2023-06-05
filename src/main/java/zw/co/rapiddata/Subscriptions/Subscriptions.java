package zw.co.rapiddata.Subscriptions;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscriptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer durationInMonths;

    private Double price;

    private String currency;

    private String description;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    private LocalDateTime dateCreated;
}
