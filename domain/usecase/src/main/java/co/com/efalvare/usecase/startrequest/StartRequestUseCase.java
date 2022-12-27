package co.com.efalvare.usecase.startrequest;

import co.com.efalvare.model.api.startrequest.StartRequest;
import co.com.efalvare.model.api.startrequest.StartRequestResponse;
import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import co.com.efalvare.usecase.protectionmeasure.ProtectionMeasureUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class StartRequestUseCase {

    private final ProtectionMeasureUseCase useCase;

    public Mono<StartRequestResponse> startRequest(StartRequest request) {
        return Mono.just(request)
                .map(this::buildEntity)
                .flatMap(useCase::createProtectionMeasure)
                .map(this::buildResponse);
    }

    private StartRequestResponse buildResponse(ProtectionMeasure protectionMeasure) {
        return StartRequestResponse.builder()
                .requestId(protectionMeasure.getRequestId())
                .type(protectionMeasure.getType())
                .build();
    }

    private ProtectionMeasure buildEntity(StartRequest request) {
        return ProtectionMeasure.builder()
                .type(request.getType())
                .ip(request.getIp())
                .documentNumber(request.getDocumentNumber())
                .documentType(request.getDocumentType())
                .build();
    }

}
