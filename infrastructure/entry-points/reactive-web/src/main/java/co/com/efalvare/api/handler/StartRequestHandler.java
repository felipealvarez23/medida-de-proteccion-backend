package co.com.efalvare.api.handler;

import co.com.efalvare.api.model.commons.ApiResponse;
import co.com.efalvare.api.model.commons.Error;
import co.com.efalvare.api.model.mapper.StartRequestMapper;
import co.com.efalvare.api.model.startrequest.ApiStartRequest;
import co.com.efalvare.api.model.startrequest.ApiStartRequestResponse;
import co.com.efalvare.api.utils.HandlerHelper;
import co.com.efalvare.model.api.startrequest.StartRequest;
import co.com.efalvare.model.exception.ProtectionMeasureException;
import co.com.efalvare.usecase.startrequest.StartRequestUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

import static co.com.efalvare.model.constant.ErrorCode.ERROR_RUNTIME_CODE;

@Slf4j
@Component
public class StartRequestHandler extends HandlerHelper<ApiStartRequest> {

    private final StartRequestUseCase useCase;

    private final StartRequestMapper mapper;

    public StartRequestHandler(@Autowired Validator validator, StartRequestUseCase useCase, StartRequestMapper mapper) {
        super(validator, ApiStartRequest.class);
        this.useCase = useCase;
        this.mapper = mapper;
    }

    public Mono<ServerResponse> startRequest(ServerRequest request) {
        log.info("Solicitud recibida sobre la operacion [start-request]");
        return validateRequest(request)
                .map(ApiStartRequest::getData)
                .map(mapper::mapperToEntity)
                .flatMap(startRequest -> addIP(startRequest, request))
                .flatMap(useCase::startRequest)
                .map(mapper::mapperToResponse)
                .flatMap(this::sendResponse)
                .onErrorResume(ProtectionMeasureException.class, this::sendErrorResponse);
    }

    private Mono<StartRequest> addIP(StartRequest startRequest, ServerRequest request) {
        return request.remoteAddress()
                .map(InetSocketAddress::getHostString)
                .map( ip -> {
                    startRequest.setIp(ip);
                    return Mono.just(startRequest);
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
