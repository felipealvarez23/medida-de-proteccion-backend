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

    private String type;

    private String state;

    private String step;

    private String ip;

    @Column("contact_info")
    private String contactInfo;

    @Column("document_type")
    private String documentType;

    @Column("document_number")
    private String documentNumber;

    @CreatedDate
    @Column("created_date")
    private LocalDateTime createdDate;

}
