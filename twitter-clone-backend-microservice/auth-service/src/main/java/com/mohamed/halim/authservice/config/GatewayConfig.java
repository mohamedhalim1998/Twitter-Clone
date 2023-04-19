package com.mohamed.halim.authservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/users/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/${segment}"))
                        .uri("lb://profile-service/"))
                .route(r -> r.path("/api/v1/media/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/${segment}"))
                        .uri("lb://media-service/"))
                .route(r -> r.path("/api/v1/tweets/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/${segment}"))
                        .uri("lb://tweet-service/"))
                .build();
    }

    @LoadBalanced
    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}
