package co.com.efalvare.model.rabbit.nextstep.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
public class NextStepQueryData {
    private UUID requestId;
    private String type;
    private String step;
}
