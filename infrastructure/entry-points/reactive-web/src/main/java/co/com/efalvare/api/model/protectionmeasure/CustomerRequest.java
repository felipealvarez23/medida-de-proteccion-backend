package co.com.efalvare.api.model.protectionmeasure;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CustomerRequest {
    private String documentNumber;
    private String email;
}
