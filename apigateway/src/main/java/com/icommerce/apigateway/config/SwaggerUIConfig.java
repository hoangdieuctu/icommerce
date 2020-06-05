package com.icommerce.apigateway.config;

import com.icommerce.apigateway.context.ServiceDefinitionsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Configuration
public class SwaggerUIConfig {

    @Autowired
    private ServiceDefinitionsContext definitionContext;

    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> definitionContext.getSwaggerDefinitions();
    }
}
