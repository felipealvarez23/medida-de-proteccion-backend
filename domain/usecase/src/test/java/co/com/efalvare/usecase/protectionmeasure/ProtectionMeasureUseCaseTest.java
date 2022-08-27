package co.com.efalvare.usecase.protectionmeasure;

import co.com.efalvare.model.exception.ProtectionMeasureException;
import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import co.com.efalvare.model.protectionmeasure.gateways.ProtectionMeasureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static co.com.efalvare.model.constant.ErrorCode.ERROR_SEARCH_PRO_MEASURE_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProtectionMeasureUseCaseTest {

    @Mock
    private ProtectionMeasureRepository repository;

    @InjectMocks
    private ProtectionMeasureUseCase useCase;

    @Test
    @DisplayName("should search for some existing protection measure related to the document number")
    public void createProtectionMeasureSearchPMTest() {
        ProtectionMeasure protectionMeasure = ProtectionMeasure.builder()
                .documentNumber("10526485")
                .build();
        when(repository.searchProtectionMeasure(anyString()))
                .thenReturn(Mono.error(() -> new ProtectionMeasureException(ERROR_SEARCH_PRO_MEASURE_CODE, "")));
        when(repository.createProtectionMeasure(any(ProtectionMeasure.class)))
                .thenReturn(Mono.just(protectionMeasure));
        Mono<ProtectionMeasure> protectionMeasureMono = useCase.createProtectionMeasure(protectionMeasure);
        StepVerifier.create(protectionMeasureMono)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
        verify(repository, times(1))
                .searchProtectionMeasure("10526485");
    }

    @Test
    @DisplayName("Given no protection measure related to the document number" +
            "then should create the protection measure ")
    public void createProtectionMeasure() {
        ProtectionMeasure protectionMeasure = ProtectionMeasure.builder()
                .documentNumber("10526486")
                .build();
        when(repository.searchProtectionMeasure(anyString()))
                .thenReturn(Mono.error(() -> new ProtectionMeasureException(ERROR_SEARCH_PRO_MEASURE_CODE, "")));
        when(repository.createProtectionMeasure(any(ProtectionMeasure.class)))
                .thenReturn(Mono.just(protectionMeasure));
        Mono<ProtectionMeasure> protectionMeasureMono = useCase.createProtectionMeasure(protectionMeasure);
        StepVerifier.create(protectionMeasureMono)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
        verify(repository, times(1))
                .createProtectionMeasure(protectionMeasure);
    }

    @Test
    @DisplayName("Given exists a protection measure related to the document number" +
            "and it was not created on the allowed time " +
            "then should return a controlled exception")
    public void createProtectionMeasureNotAllowedTime() {
        ProtectionMeasure protectionMeasure = ProtectionMeasure.builder()
                .createdDate(LocalDateTime.now())
                .documentNumber("10526489")
                .build();
        when(repository.searchProtectionMeasure(anyString()))
                .thenReturn(Mono.just(protectionMeasure));
        Mono<ProtectionMeasure> protectionMeasureMono = useCase.createProtectionMeasure(protectionMeasure);
        StepVerifier.create(protectionMeasureMono)
                .expectErrorMatches(e-> e instanceof ProtectionMeasureException &&
                        e.getMessage().equals("El cliente ya ha iniciado una solicitud recientemente, intente mas tarde"))
                .verify();
    }

    @Test
    @DisplayName("Given exists a protection measure related to the document number" +
            "and it was created on the allowed time " +
            "then should return a new protection measure")
    public void createProtectionMeasureAllowedTime() {
        ProtectionMeasure protectionMeasure = ProtectionMeasure.builder()
                .createdDate(LocalDateTime.of(2022, Month.JANUARY, 1, 7,0))
                .documentNumber("10526488")
                .build();
        when(repository.searchProtectionMeasure(anyString()))
                .thenReturn(Mono.just(protectionMeasure));
        when(repository.createProtectionMeasure(any(ProtectionMeasure.class)))
                .thenReturn(Mono.just(protectionMeasure));
        Mono<ProtectionMeasure> protectionMeasureMono = useCase.createProtectionMeasure(protectionMeasure);
        StepVerifier.create(protectionMeasureMono)
                .expectNextMatches(Objects::nonNull)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("When a unknown error is launched " +
            "then should return a controlled exception")
    public void createProtectionMeasureUnknownError() {
        ProtectionMeasure protectionMeasure = ProtectionMeasure.builder()
                .createdDate(LocalDateTime.of(2022, Month.JANUARY, 1, 7,0))
                .documentNumber("10526481")
                .build();
        when(repository.searchProtectionMeasure(anyString()))
                .thenReturn(Mono.error(NullPointerException::new));
        Mono<ProtectionMeasure> protectionMeasureMono = useCase.createProtectionMeasure(protectionMeasure);
        StepVerifier.create(protectionMeasureMono)
                .expectErrorMatches(e-> e instanceof ProtectionMeasureException &&
                        e.getMessage().equals("Error validando medidas de protección previas"))
                .verify();

    }


}