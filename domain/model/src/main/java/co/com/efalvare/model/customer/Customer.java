package co.com.efalvare.model.customer;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
public class Customer {
    private UUID customerId;
    private String documentNumber;
    private String email;
    private String cellPhone;
    private String state;
    private LocalDateTime createdDate;
}
