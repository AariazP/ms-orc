package org.arias.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Set;

@Configuration
public class GatewayConf {

    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("companies", r -> r.path("/companies-crud/company/**")
                        .filters(filter ->{
                                    filter.circuitBreaker(c -> c.setName("companies-circuit-breaker")
                                        .setFallbackUri("forward:/companies-crud-fallback/company/**")
                                        .setStatusCodes(Set.of("400", "500"))
                                        .setRouteId("companies-circuit-breaker"));
                                return filter;
                        }
                        )
                        .uri("lb://companies-crud"))
                .route("reports", r -> r.path("/report-ms/reports/**")
                        .uri("lb://report-ms"))
                .route("fallback-companies", r -> r.path("/companies-crud-fallback/company/**")
                        .uri("lb://companies-crud-fallback"))
                .build();
    }
}
