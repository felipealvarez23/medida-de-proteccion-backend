package co.com.efalvare.api;

import co.com.efalvare.api.model.commons.ApiRequest;
import co.com.efalvare.api.model.commons.ApiResponse;
import co.com.efalvare.api.model.commons.Error;
import co.com.efalvare.api.model.mapper.StartRequestMapper;
import co.com.efalvare.api.model.startrequest.ApiStartRequest;
import co.com.efalvare.api.model.startrequest.ApiStartRequestResponse;
import co.com.efalvare.model.exception.ProtectionMeasureException;
import co.com.efalvare.usecase.startrequest.StartRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

import static co.com.efalvare.model.constant.ErrorCode.ERROR_RUNTIME_CODE;

@Component
@RequiredArgsConstructor
public class Handler {

    private final StartRequestUseCase useCase;

    private final StartRequestMapper mapper;

    public Mono<ServerResponse> startRequest(ServerRequest request) {
        ParameterizedTypeReference<ApiRequest<ApiStartRequest>> requestType = new ParameterizedTypeReference<>() {};
        return request.bodyToMono( requestType )
                .map(ApiRequest::getData)
                .flatMap(apiStartRequest -> addIP(apiStartRequest, request))
                .map(mapper::mapperToEntity)
                .flatMap(useCase::startRequest)
                .map(mapper::mapperToResponse)
                .flatMap(this::sendResponse)
                .onErrorResume(ProtectionMeasureException.class, this::sendErrorResponse);
    }

    private Mono<ApiStartRequest> addIP(ApiStartRequest apiStartRequest, ServerRequest request) {
        return request.remoteAddress()
                .map(InetSocketAddress::getHostString)
                .map( ip -> {
                    apiStartRequest.setIp(ip);
                    return Mono.just(apiStartRequest);
                }).orElseGet(()-> Mono.error(()-> new ProtectionMeasureException(ERROR_RUNTIME_CODE,"Runtime exception")));

    }

    private Mono<ServerResponse> sendResponse(ApiStartRequestResponse delivery) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(ApiResponse.builder()
                        .data(delivery)
                        .build()), ApiResponse.class);
    }

    private  Mono<ServerResponse> sendErrorResponse(ProtectionMeasureException error) {
        return ServerResponse.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(ApiResponse.builder()
                        .error(Error.builder()
                                .title(error.getTitle())
                                .code(error.getCode())
                                .description(error.getMessage())
                                .build())
                        .build()), ApiResponse.class);
    }

}
