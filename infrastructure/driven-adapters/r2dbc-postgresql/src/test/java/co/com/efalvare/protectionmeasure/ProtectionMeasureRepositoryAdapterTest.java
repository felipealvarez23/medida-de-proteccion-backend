package co.com.efalvare.protectionmeasure;

import co.com.efalvare.model.exception.ProtectionMeasureException;
import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static co.com.efalvare.model.constant.ErrorCode.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProtectionMeasureRepositoryAdapterTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ProtectionMeasureReactiveRepository repository;

    @InjectMocks
    private ProtectionMeasureRepositoryAdapter adapter;

    @Test
    @DisplayName("should create protection measure")
    void createProtectionMeasure() {
        ProtectionMeasureData protectionMeasureData = new ProtectionMeasureData();
        when(objectMapper.map(any(),any())).thenReturn(protectionMeasureData);
        when(repository.save(any())).thenReturn(Mono.just(protectionMeasureData));
        when(objectMapper.mapBuilder(any(),any())).thenReturn(ProtectionMeasure.builder());
        Mono<ProtectionMeasure> protectionMeasureMono = adapter.createProtectionMeasure(new ProtectionMeasure());
        StepVerifier.create(protectionMeasureMono)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
        verify(repository, times(1)).save(any(ProtectionMeasureData.class));
    }

    @Test
    @DisplayName("should return exception")
    void createProtectionMeasureException() {
        ProtectionMeasureData protectionMeasureData = new ProtectionMeasureData();
        when(objectMapper.map(any(),any())).thenReturn(protectionMeasureData);
        when(repository.save(any())).thenReturn(Mono.error(Exception::new));
        Mono<ProtectionMeasure> protectionMeasureMono = adapter.createProtectionMeasure(new ProtectionMeasure());
        StepVerifier.create(protectionMeasureMono)
                .expectErrorMatches(e-> e instanceof ProtectionMeasureException &&
                        ((ProtectionMeasureException) e).getCode().equals(ERROR_CREATE_PRO_MEASURE_CODE))
                .verify();
    }

    @Test
    @DisplayName("when the protection measure is found " +
            "then should return the protection measure")
    void searchProtectionMeasure() {
        String documentNumber = "1052397500";
        when(repository.searchByDocumentNumber(anyString()))
                .thenReturn(Mono.just(new ProtectionMeasureData()));
        when(objectMapper.mapBuilder(any(),any()))
                .thenReturn(ProtectionMeasure.builder());
        Mono<ProtectionMeasure> protectionMeasureMono = adapter.searchProtectionMeasure(documentNumber);
        StepVerifier.create(protectionMeasureMono)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("when the protection measure is not found " +
            "then should return a exception")
    void searchProtectionMeasureError() {
        String documentNumber = "1052397500";
        when(repository.searchByDocumentNumber(anyString()))
                .thenReturn(Mono.empty());
        Mono<ProtectionMeasure> protectionMeasureMono = adapter.searchProtectionMeasure(documentNumber);
        StepVerifier.create(protectionMeasureMono)
                .expectErrorMatches(e -> e instanceof ProtectionMeasureException &&
                        ((ProtectionMeasureException) e).getCode().equals(ERROR_SEARCH_PRO_MEASURE_CODE))
                .verify();
    }

    @Test
    @DisplayName("when the unknown error is launched " +
            "then should return a exception")
    void searchProtectionMeasureUnknownError() {
        String documentNumber = "1052397500";
        when(repository.searchByDocumentNumber(anyString()))
                .thenReturn(Mono.error(NullPointerException::new));
        Mono<ProtectionMeasure> protectionMeasureMono = adapter.searchProtectionMeasure(documentNumber);
        StepVerifier.create(protectionMeasureMono)
                .expectErrorMatches(e -> e instanceof ProtectionMeasureException &&
                        ((ProtectionMeasureException) e).getCode().equals(ERROR_RUNTIME_CODE))
                .verify();
    }
}