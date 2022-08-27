package co.com.efalvare.api.model.startrequest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApiStartRequest {
    private String type;
    private String documentType;
    private String documentNumber;
    private String contactInfo;
    private String ip;
}
