package co.com.efalvare.protectionmeasure;

import co.com.efalvare.helper.AdapterOperations;
import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import co.com.efalvare.model.protectionmeasure.gateways.ProtectionMeasureRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ProtectionMeasureRepositoryAdapter extends AdapterOperations<ProtectionMeasure, ProtectionMeasureData, UUID, ProtectionMeasureReactiveRepository>
        implements ProtectionMeasureRepository {

    protected ProtectionMeasureRepositoryAdapter(ProtectionMeasureReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, (data-> mapper.mapBuilder(data, ProtectionMeasure.ProtectionMeasureBuilder.class).build()));
    }

    @Override
    public Mono<ProtectionMeasure> createProtectionMeasure(ProtectionMeasure protectionMeasure) {
        return Mono.just(toData(protectionMeasure))
                .flatMap(repository::save)
                .map(this::toEntity);
    }

}
