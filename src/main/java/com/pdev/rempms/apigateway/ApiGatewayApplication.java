package com.pdev.rempms.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);

    }

    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("location-service-route", p -> p
                        .path("/api/location/**")
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                                // circuit breaker pattern
                                .circuitBreaker(config -> config
                                        .setName("locationCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/response/message"))
                                // retry pattern
                                .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                        )
                        .uri("lb://REMPMS-LOCATION-SERVICE"))
                .route("draft-service-route", p -> p
                        .path("/api/draft/**")
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                                // circuit breaker pattern
                                .circuitBreaker(config -> config
                                        .setName("draftCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/response/message"))
                                // retry pattern
                                .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                        )
                        .uri("lb://REMPMS-DRAFT-SERVICE"))
                .route("document-service-route", p -> p
                        .path("/api/document/**")
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                                // circuit breaker pattern
                                .circuitBreaker(config -> config
                                        .setName("documentCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/response/message"))
                                // retry pattern
                                .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                        )
                        .uri("lb://REMPMS-DOCUMENT-SERVICE"))
                .route("communication-service-route", p -> p
                        .path("/api/communication/**")
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                                // circuit breaker pattern
                                .circuitBreaker(config -> config
                                        .setName("communicationCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/response/message"))
                                // retry pattern
                                .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                        )
                        .uri("lb://REMPMS-COMMUNICATION-SERVICE"))
                .route("candidate-service-route", p -> p
                        .path("/api/candidate/**")
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                                // circuit breaker pattern
                                .circuitBreaker(config -> config
                                        .setName("candidateCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/response/message"))
                                // retry pattern
                                .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                        )
                        .uri("lb://REMPMS-CANDIDATE-SERVICE"))
                .route("recruitment-service-route", p -> p
                        .path("/api/recruitment/**")
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                                // circuit breaker pattern
                                .circuitBreaker(config -> config
                                        .setName("recruitmentCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/response/message"))
                                // retry pattern
                                .retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                        )
                        .uri("lb://REMPMS-RECRUITMENT-SERVICE"))
                .build();
    }
}
