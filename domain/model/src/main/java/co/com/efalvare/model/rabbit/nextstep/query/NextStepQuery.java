package co.com.efalvare.model.rabbit.nextstep.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class NextStepQuery {
    private NextStepQueryData data;
}
