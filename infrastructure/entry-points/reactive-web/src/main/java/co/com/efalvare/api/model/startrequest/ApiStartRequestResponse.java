package co.com.efalvare.api.model.startrequest;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApiStartRequestResponse {
    private UUID requestId;
    private String type;
}
