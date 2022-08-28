package co.com.efalvare.usecase.startrequest;

import co.com.efalvare.model.api.startrequest.StartRequest;
import co.com.efalvare.model.api.startrequest.StartRequestResponse;
import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import co.com.efalvare.usecase.protectionmeasure.ProtectionMeasureUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StartRequestUseCaseTest {

    @Mock
    private ProtectionMeasureUseCase protectionMeasureUseCase;

    @InjectMocks
    private StartRequestUseCase useCase;

    @Test
    @DisplayName("should create a protection measure")
    void startRequest() {
        StartRequest request = StartRequest.builder()
                .documentNumber("1052397500")
                .contactInfo("3058507458")
                .ip("10.12.15.21")
                .type("01")
                .build();
        when(protectionMeasureUseCase.createProtectionMeasure(any(ProtectionMeasure.class)))
                .thenReturn(Mono.just(new ProtectionMeasure()));
        Mono<StartRequestResponse> requestResMono = useCase.startRequest(request);
        StepVerifier.create(requestResMono)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
    }

}