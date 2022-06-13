package co.com.efalvare.protectionmeasure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProtectionMeasureReactiveRepository extends ReactiveCrudRepository<ProtectionMeasureData, UUID> {
}
