package co.com.efalvare.api;

import co.com.efalvare.api.handler.StartRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(StartRequestHandler handler) {
        return route(POST("/medida-de-proteccion/api/v1/start-request"), handler::startRequest);
    }
}
