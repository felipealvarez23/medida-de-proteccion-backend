package co.com.efalvare.api;

import co.com.efalvare.api.mapper.ProtectionMeasureMapper;
import co.com.efalvare.api.model.commons.ApiRequest;
import co.com.efalvare.api.model.commons.ApiResponse;
import co.com.efalvare.api.model.protectionmeasure.ProtectionMeasureRequest;
import co.com.efalvare.usecase.protectionmeasure.ProtectionMeasureUseCase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler {

    private final ProtectionMeasureUseCase useCase;

    private final ProtectionMeasureMapper mapper;

    public Handler(ProtectionMeasureUseCase useCase, ProtectionMeasureMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    public Mono<ServerResponse> startRequest(ServerRequest serverRequest) {
        ParameterizedTypeReference<ApiRequest<ProtectionMeasureRequest>> requestType = new ParameterizedTypeReference<>() {};
        return serverRequest.bodyToMono( requestType )
                .map(ApiRequest::getData)
                .map(mapper::mapperToEntity)
                .flatMap(useCase::createProtectionMeasure)
                .map(mapper::mapperToResponse)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(ApiResponse.builder()
                                        .data(response)
                                .build()), ApiResponse.class));
    }

}
