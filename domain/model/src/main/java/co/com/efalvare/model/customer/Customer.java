package co.com.efalvare.model.customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class Customer {
    private UUID customerId;
    private String documentNumber;
    private String email;
    private LocalDateTime createDate;
}
