package com.icommerce.swaggerservice.config;

import com.icommerce.swaggerservice.swagger.ServiceDefinitionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private ServiceDefinitionHolder definitionHolder;

    @Primary
    @Lazy
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> definitionHolder.getSwaggerDefinitions();
    }

}

