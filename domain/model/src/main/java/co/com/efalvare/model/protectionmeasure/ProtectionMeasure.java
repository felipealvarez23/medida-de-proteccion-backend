package co.com.efalvare.model.protectionmeasure;
import co.com.efalvare.model.customer.Customer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ProtectionMeasure {
    private UUID requestId;
    private String requestType;
    private Customer customer;
    private String state;
    private LocalDateTime createdDate;
}
