package co.com.efalvare.api.utils;

import co.com.efalvare.model.exception.ProtectionMeasureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import static co.com.efalvare.model.constant.ErrorCode.ERROR_RUNTIME_CODE;

@Slf4j
public class HandlerHelper<T> {

    private Validator validator;

    private Class<T> tClass;

    public HandlerHelper(Validator validator, Class<T> tClass) {
        this.tClass = tClass;
        this.validator = validator;
    }

    protected Mono<T> validateRequest(final ServerRequest request) {
        return request.bodyToMono(tClass)
                .flatMap(this::checkConstraintFields);
    }

    private Mono<T> checkConstraintFields(T body) {
        Errors errors = new BeanPropertyBindingResult(body, tClass.getName());
        validator.validate(body, errors);
        if(errors.getAllErrors().isEmpty()) {
            return Mono.just(body);
        } else {
            log.error("Error validando campos de entrada:[{}]", errors.getAllErrors().get(0).getDefaultMessage());
            return Mono.error(()-> new ProtectionMeasureException(ERROR_RUNTIME_CODE,"Error validando campos de entrada"));
        }
    }

}
