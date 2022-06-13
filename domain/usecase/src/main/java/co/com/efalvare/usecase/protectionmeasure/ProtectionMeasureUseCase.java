package co.com.efalvare.usecase.protectionmeasure;

import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import co.com.efalvare.model.protectionmeasure.gateways.ProtectionMeasureRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ProtectionMeasureUseCase {

    private final ProtectionMeasureRepository repository;

    public Mono<ProtectionMeasure> createProtectionMeasure(ProtectionMeasure protectionMeasure) {
        protectionMeasure.setCreatedDate(LocalDateTime.now());
        protectionMeasure.setState("active");
        return this.repository.createProtectionMeasure(protectionMeasure);
    }

}
