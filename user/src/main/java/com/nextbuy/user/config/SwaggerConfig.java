package com.nextbuy.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(
                objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy()));
    }
}
