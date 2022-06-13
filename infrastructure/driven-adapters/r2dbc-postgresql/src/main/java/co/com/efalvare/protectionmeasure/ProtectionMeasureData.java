package co.com.efalvare.protectionmeasure;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("protection_measure")
public class ProtectionMeasureData {

    @Id()
    @Column("request_id")
    private UUID requestId;

    @Column("request_type")
    private String requestType;

    private String state;

    @Column("created_date")
    @CreatedDate
    private LocalDateTime createdDate;

}
