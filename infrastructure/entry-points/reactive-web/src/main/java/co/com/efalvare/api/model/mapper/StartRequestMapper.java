package co.com.efalvare.api.model.mapper;

import co.com.efalvare.api.model.startrequest.ApiStartRequest;
import co.com.efalvare.api.model.startrequest.ApiStartRequestResponse;
import co.com.efalvare.model.api.startrequest.StartRequest;
import co.com.efalvare.model.api.startrequest.StartRequestResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StartRequestMapper {

    StartRequest mapperToEntity(ApiStartRequest payload);

    ApiStartRequestResponse mapperToResponse(StartRequestResponse response);

}
