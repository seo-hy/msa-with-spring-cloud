package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
// route 설정을 java 코드로 작성한것, 사용하지 않음
public class FilterConfig {

//  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(r -> r.path("/first-service/**")
            .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                .addResponseHeader("first-response", "first-response-header"))
            .uri("http://localhost:8081"))
        .route(r -> r.path("/second-service/**")
            .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                .addResponseHeader("second-response", "second-response-header"))
            .uri("http://localhost:8082"))
        .build();
  }

}

