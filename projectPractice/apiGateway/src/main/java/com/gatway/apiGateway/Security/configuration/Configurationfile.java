package com.gatway.apiGateway.Security.configuration;

import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;

@Configuration
public class Configurationfile {

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        RouterFunction<ServerResponse> response= GatewayRouterFunctions.route("user_service_route")
                .route(RequestPredicates.path("/user-service/**"), HandlerFunctions.http())
                .before(uri("lb://user-service"))
                .filter(FilterFunctions.stripPrefix(1))
                .filter(lb("user-service"))
                .build();
        System.out.println("response-------->"+response);
        return response;
    }

    @Bean
    public RouterFunction<ServerResponse> authservice(){
        RouterFunction<ServerResponse> response=GatewayRouterFunctions.route("auther_service_route")
                .route(RequestPredicates.path("/authservice/**"),HandlerFunctions.http())
                .before(uri("lb://authservice"))
                .filter(FilterFunctions.stripPrefix(1))
                .filter(lb("authservice"))
                .build();
        return response;
    }
}