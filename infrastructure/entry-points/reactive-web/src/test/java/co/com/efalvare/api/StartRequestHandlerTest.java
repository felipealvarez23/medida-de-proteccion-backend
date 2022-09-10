package co.com.efalvare.api;

import co.com.efalvare.api.handler.StartRequestHandler;
import co.com.efalvare.api.model.commons.ApiRequest;
import co.com.efalvare.api.model.mapper.StartRequestMapper;
import co.com.efalvare.api.model.startrequest.ApiStartRequestData;
import co.com.efalvare.api.model.startrequest.ApiStartRequestResponse;
import co.com.efalvare.model.api.startrequest.StartRequest;
import co.com.efalvare.model.api.startrequest.StartRequestResponse;
import co.com.efalvare.model.exception.ProtectionMeasureException;
import co.com.efalvare.usecase.startrequest.StartRequestUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;
import utils.SetRemoteAddressWebFilter;

import static co.com.efalvare.model.constant.ErrorCode.ERROR_RUNTIME_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StartRequestHandlerTest {

    private WebTestClient client;

    @Mock
    private StartRequestMapper mapper;

    @Mock
    private StartRequestUseCase startRequestUseCase;

    @Mock
    private Validator validator;

    @InjectMocks
    private StartRequestHandler handler;

    private ApiRequest<ApiStartRequestData> apiRequest;

    private static final String ENDPOINT = "/medida-de-proteccion/api/v1/start-request";

    @BeforeEach
    public void setUp() {
        ApiStartRequestData startRequest = ApiStartRequestData.builder()
                .documentNumber("1052397500")
                .documentType("DOCUMENT_TYPE_CC")
                .contactInfo("3058507458")
                .type("01")
                .build();
        apiRequest = ApiRequest.<ApiStartRequestData>builder()
                .data(startRequest)
                .build();
        RouterRest router = new RouterRest();
        client = WebTestClient
                .bindToRouterFunction(router.routerFunction(handler))
                .webFilter(new SetRemoteAddressWebFilter("127.0.0.1"))
                .build();
    }

    @Test
    @DisplayName("should return success response")
    void startRequest() {
        when(startRequestUseCase.startRequest(any(StartRequest.class)))
                .thenReturn(Mono.just(new StartRequestResponse()));
        when(mapper.mapperToEntity(any(ApiStartRequestData.class)))
                .thenReturn(new StartRequest());
        when(mapper.mapperToResponse(any(StartRequestResponse.class)))
                .thenReturn(ApiStartRequestResponse.builder().build());
        client.post()
                .uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(apiRequest), ApiRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiStartRequestResponse.class);
    }

    @Test
    @DisplayName("should return bad response")
    void startRequestError() {
        when(startRequestUseCase.startRequest(any(StartRequest.class)))
                .thenReturn(Mono.error(()-> new ProtectionMeasureException(ERROR_RUNTIME_CODE,"")));
        when(mapper.mapperToEntity(any(ApiStartRequestData.class)))
                .thenReturn(new StartRequest());
        client.post()
                .uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(apiRequest), ApiRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiStartRequestResponse.class);
    }

    @Test
    @DisplayName("should return bad response")
    void startRequestIPError() {
        client = client.mutateWith((builder, httpHandlerBuilder, connector) ->
                httpHandlerBuilder.filter(new SetRemoteAddressWebFilter(null)));
        when(mapper.mapperToEntity(any(ApiStartRequestData.class)))
                .thenReturn(new StartRequest());
        client.post()
                .uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(apiRequest), ApiRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ApiStartRequestResponse.class);
    }

}