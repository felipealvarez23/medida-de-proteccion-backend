package co.com.efalvare.api.model.protectionmeasure;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProtectionMeasureRequest {
    private String requestType;
    private CustomerRequest customer;
}
