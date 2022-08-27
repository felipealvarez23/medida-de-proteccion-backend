package co.com.efalvare.protectionmeasure;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProtectionMeasureReactiveRepository extends ReactiveCrudRepository<ProtectionMeasureData, UUID> {

    @Query("SELECT * FROM pm_schema.protection_measure pm " +
            "WHERE document_number=:documentNumber " +
            "ORDER BY created_date DESC LIMIT 1")
    Mono<ProtectionMeasureData> searchByDocumentNumber(String documentNumber);

}
