package org.arias.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConf {

    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("companies", r -> r.path("/companies-crud/company/**")
                        .uri("lb://companies-crud"))
                .route("reports", r -> r.path("/report-ms/reports/**")
                        .uri("lb://report-ms"))
                .build();
    }
}
