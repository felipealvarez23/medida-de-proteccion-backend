package co.com.efalvare.api.mapper;

import co.com.efalvare.api.model.protectionmeasure.ProtectionMeasureRequest;
import co.com.efalvare.api.model.protectionmeasure.ProtectionMeasureResponse;
import co.com.efalvare.model.protectionmeasure.ProtectionMeasure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProtectionMeasureMapper {

    ProtectionMeasure mapperToEntity(ProtectionMeasureRequest request);

    ProtectionMeasureResponse mapperToResponse(ProtectionMeasure entity);

}
