package co.com.efalvare.model.protectionmeasure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProtectionMeasure {
    private UUID requestId;
    private String type;
    private String contactInfo;
    private String state;
    private String documentNumber;
    private String documentType;
    private String step;
    private String ip;
    private LocalDateTime createdDate;
}
