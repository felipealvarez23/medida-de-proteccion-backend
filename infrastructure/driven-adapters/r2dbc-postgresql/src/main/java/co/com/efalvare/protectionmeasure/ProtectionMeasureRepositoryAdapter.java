package co.com.efalvare.protectionmeasure;

import co.com.efalvare.helper.AdapterOperations;
import co.com.efalvare.model.exception.ApiError;
import co.com.efalvare.model.exception.ProtectionMeasureException;
import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import co.com.efalvare.model.protectionmeasure.gateways.ProtectionMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static co.com.efalvare.model.constant.ErrorCode.*;
import static co.com.efalvare.model.constant.ErrorDescription.ERROR_CREATE_PRO_MEASURE;
import static co.com.efalvare.model.constant.ErrorDescription.ERROR_SEARCH_PRO_MEASURE;

@Slf4j
@Repository
public class ProtectionMeasureRepositoryAdapter extends AdapterOperations<ProtectionMeasure, ProtectionMeasureData, UUID, ProtectionMeasureReactiveRepository>
        implements ProtectionMeasureRepository {

    protected ProtectionMeasureRepositoryAdapter(ProtectionMeasureReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper,
                (data-> mapper.mapBuilder(data, ProtectionMeasure.ProtectionMeasureBuilder.class).build()));
    }

    @Override
    public Mono<ProtectionMeasure> createProtectionMeasure(ProtectionMeasure protectionMeasure) {
        return Mono.just(toData(protectionMeasure))
                .flatMap(repository::save)
                .doOnSuccess(createdPm -> log.info("Protection measure [{}] init successful", createdPm.getRequestId()))
                .doOnError(e -> log.error("Error creating protection measure: [{}]", e.getMessage()))
                .map(this::toEntity)
                .onErrorMap(e -> new ProtectionMeasureException(ERROR_CREATE_PRO_MEASURE_CODE,
                        ERROR_CREATE_PRO_MEASURE));
    }

    @Override
    public Mono<ProtectionMeasure> searchProtectionMeasure(String documentNumber) {
        return repository.searchByDocumentNumber(documentNumber)
                .map(this::toEntity)
                .switchIfEmpty(Mono.error(()-> new ProtectionMeasureException(ERROR_SEARCH_PRO_MEASURE_CODE,
                        ERROR_SEARCH_PRO_MEASURE)))
                .doOnError(e-> !(e instanceof ApiError),
                        e -> log.error("Error al consultar la medida de proteccion: [{}]", e.getMessage()))
                .onErrorMap(e-> !(e instanceof ApiError), e -> new ProtectionMeasureException(ERROR_RUNTIME_CODE,
                        "Error al consultar la medida de proteccion"));
    }

}
