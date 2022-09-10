package co.com.efalvare.api.model.startrequest;

import lombok.*;

import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApiStartRequest {
    @Valid
    private ApiStartRequestData data;
}
