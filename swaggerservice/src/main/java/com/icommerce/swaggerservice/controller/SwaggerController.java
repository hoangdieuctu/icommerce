package com.icommerce.swaggerservice.controller;

import com.icommerce.swaggerservice.service.ServiceDefinitionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/swagger")
public class SwaggerController {

    @Autowired
    private ServiceDefinitionHolder serviceDefinitionHolder;

    @ResponseBody
    @GetMapping("/{service}")
    public String getServiceDefinition(@PathVariable("service") String service) {
        return serviceDefinitionHolder.getSwaggerDefinition(service);
    }


}
