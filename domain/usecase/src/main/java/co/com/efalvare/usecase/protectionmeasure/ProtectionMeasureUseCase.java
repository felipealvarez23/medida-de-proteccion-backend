package co.com.efalvare.usecase.protectionmeasure;

import co.com.efalvare.model.exception.ProtectionMeasureException;
import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import co.com.efalvare.model.protectionmeasure.gateways.ProtectionMeasureRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static co.com.efalvare.model.constant.ErrorCode.*;
import static co.com.efalvare.model.constant.ErrorDescription.ERROR_ALREADY_EXIST;

@RequiredArgsConstructor
public class ProtectionMeasureUseCase {

    private static final Logger LOGGER = Logger.getLogger(ProtectionMeasureUseCase.class.getName());
    private final ProtectionMeasureRepository repository;

    public Mono<ProtectionMeasure> createProtectionMeasure(ProtectionMeasure protectionMeasure) {
        return Mono.just(protectionMeasure)
                .flatMap(this::validPreviousProMeasure)
                .map(this::prepareProMeasure)
                .flatMap(repository::createProtectionMeasure);
    }

    private Mono<ProtectionMeasure> validPreviousProMeasure(ProtectionMeasure protectionMeasure) {
        final Predicate<ProtectionMeasure> onAllowedTime = proMeasure -> proMeasure.getCreatedDate()
                .isBefore(LocalDateTime.now().minusHours(12));
        return repository.searchProtectionMeasure(protectionMeasure.getDocumentNumber())
                .filter(onAllowedTime)
                .map(proMeasure -> protectionMeasure)
                .switchIfEmpty(Mono.error(() -> new ProtectionMeasureException(ERROR_ALREADY_EXIST_CODE,ERROR_ALREADY_EXIST)))
                .doOnError(e -> e instanceof ProtectionMeasureException && ERROR_ALREADY_EXIST_CODE.equals(((ProtectionMeasureException) e).getCode()),
                        e -> LOGGER.log(Level.SEVERE, "El cliente[{0}] ya ha iniciado una solicitud recientemente",
                                protectionMeasure.getDocumentNumber()))
                .onErrorResume(e -> e instanceof ProtectionMeasureException &&
                                ERROR_SEARCH_PRO_MEASURE_CODE.equals(((ProtectionMeasureException) e).getCode()),
                        e -> Mono.just(protectionMeasure))
                .onErrorMap(e-> !(e instanceof ProtectionMeasureException),
                        e -> new ProtectionMeasureException(ERROR_RUNTIME_CODE,"Error validando medidas de protección previas"));
    }

    private ProtectionMeasure prepareProMeasure(ProtectionMeasure protectionMeasure) {
        protectionMeasure.setCreatedDate(LocalDateTime.now());
        protectionMeasure.setStep("start-request");
        return protectionMeasure;
    }

}
