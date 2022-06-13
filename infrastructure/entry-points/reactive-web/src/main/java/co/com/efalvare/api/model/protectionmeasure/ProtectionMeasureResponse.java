package co.com.efalvare.api.model.protectionmeasure;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProtectionMeasureResponse {
    private UUID requestId;
}
