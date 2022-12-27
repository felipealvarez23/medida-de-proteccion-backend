package co.com.efalvare.model.api.startrequest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StartRequest {
    private String type;
    private String documentType;
    private String documentNumber;
    private String ip;
}
