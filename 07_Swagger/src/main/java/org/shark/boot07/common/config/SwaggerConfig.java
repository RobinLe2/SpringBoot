package org.shark.boot07.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * Swagger End Point URL
 * http://localhost:8080/swagger-ui/index.html
 */

@Configuration
public class SwaggerConfig {

  @Bean
  OpenAPI openAPI() {
    
    Info info = new Info();
    info.setTitle("Project Name API");
    info.setDescription("사용자 처리 REST API");
    info.setVersion("1.0.0");
    
    return new OpenAPI().info(info);
    
  }
  
}
