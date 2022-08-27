package co.com.efalvare.model.protectionmeasure.gateways;

import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import reactor.core.publisher.Mono;

public interface ProtectionMeasureRepository {

    Mono<ProtectionMeasure> createProtectionMeasure(ProtectionMeasure protectionMeasure);

    Mono<ProtectionMeasure> searchProtectionMeasure(String documentNumber);

}
