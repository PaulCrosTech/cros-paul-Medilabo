package com.medilabo.ms_gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining custom routes in the Medilabo Gateway Service.
 */
@Configuration
public class CustomRouteLocator {

    /**
     * Defines custom routes for the gateway service.
     *
     * @param builder the RouteLocatorBuilder used to build the routes
     * @return a RouteLocator containing the defined routes
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("patient_route", r -> r.path("/patients/**")
                        .uri("http://localhost:9010/"))
                .build();
    }

}
