package com.icommerce.swaggerservice.service;

import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ServiceDefinitionHolder {

    private final Map<String, String> definitions;

    public ServiceDefinitionHolder() {
        definitions = new ConcurrentHashMap<>();
    }

    public void addServiceDefinition(String serviceId, String serviceDescription) {
        definitions.put(serviceId, serviceDescription);
    }

    public String getSwaggerDefinition(String serviceId) {
        return this.definitions.get(serviceId);
    }

    public List<SwaggerResource> getSwaggerDefinitions() {
        return definitions.entrySet().stream().map(serviceDefinition -> {
            SwaggerResource resource = new SwaggerResource();
            resource.setLocation("/api/swagger/" + serviceDefinition.getKey());
            resource.setName(serviceDefinition.getKey());
            resource.setSwaggerVersion("2.0");
            return resource;
        }).collect(Collectors.toList());
    }

}
