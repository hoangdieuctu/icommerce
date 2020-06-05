package com.icommerce.apigateway.context;

import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ServiceDefinitionsContext {

    private final Map<String, String> serviceDescriptions;

    private ServiceDefinitionsContext() {
        serviceDescriptions = new ConcurrentHashMap<>();
    }

    public void addServiceDefinition(String serviceId, String serviceDescription) {
        serviceDescriptions.put(serviceId, serviceDescription);
    }

    public String getSwaggerDefinition(String serviceId) {
        return this.serviceDescriptions.get(serviceId);
    }

    public List<SwaggerResource> getSwaggerDefinitions() {
        return serviceDescriptions.entrySet().stream().map(serviceDefinition -> {
            SwaggerResource resource = new SwaggerResource();
            resource.setLocation("/service/" + serviceDefinition.getKey());
            resource.setName(serviceDefinition.getKey());
            resource.setSwaggerVersion("2.0");
            return resource;
        }).collect(Collectors.toList());
    }
}
