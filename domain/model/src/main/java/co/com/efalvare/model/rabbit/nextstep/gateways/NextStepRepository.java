package co.com.efalvare.model.rabbit.nextstep.gateways;

import co.com.efalvare.model.rabbit.nextstep.replay.NextStepReplay;
import co.com.efalvare.model.rabbit.nextstep.query.NextStepQuery;
import reactor.core.publisher.Mono;

public interface NextStepRepository {

    Mono<NextStepReplay> getNextStep(NextStepQuery query);

}
