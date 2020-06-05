package com.icommerce.apigateway.context;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Component
public class ServiceDescriptionUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ServiceDescriptionUpdater.class);

    private static final String DEFAULT_SCHEMA = "http://";
    private static final String DEFAULT_SWAGGER_URL = "/v2/api-docs";

    @Autowired
    private ServiceDefinitionsContext definitionContext;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRate = 30000)
    public void refreshSwaggerConfigurations() {
        logger.debug("Starting service definition context refresh");
        Applications applications = eurekaClient.getApplications();

        logger.debug("Found: {} applications", applications.size());
        List<Application> registeredApplications = applications.getRegisteredApplications();
        for (Application application : registeredApplications) {
            String serviceName = application.getName();

            logger.debug("Get service swagger definition from: {}", serviceName);
            String url = DEFAULT_SCHEMA + serviceName + DEFAULT_SWAGGER_URL;

            logger.debug("Url: {}", url);
            try {
                String jsonData = restTemplate.getForObject(url, String.class);
                logger.info("Service [{}] definition context refreshed at: {}", serviceName, new Date());

                definitionContext.addServiceDefinition(serviceName, jsonData);
            } catch (Exception ex) {
                logger.warn("Error while getting swagger info from: {}, keep the old definition", serviceName);
            }
        }
    }
}
