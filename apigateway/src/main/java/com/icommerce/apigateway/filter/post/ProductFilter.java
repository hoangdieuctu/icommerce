package com.icommerce.apigateway.filter.post;

import com.icommerce.apigateway.model.ProductAudit;
import com.icommerce.apigateway.repository.ProductAuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
public class ProductFilter extends AbstractGatewayFilterFactory<ProductFilter.Config> {

    private static Logger logger = LoggerFactory.getLogger(ProductFilter.class);

    @Autowired
    private ProductAuditRepository auditRepository;

    public ProductFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            try {
                storeAudit(request, response);
            } catch (Exception ex) {
                logger.warn("Error while saving audit.", ex);
            }
        })));
    }

    private void storeAudit(ServerHttpRequest request, ServerHttpResponse response) {
        HttpHeaders headers = request.getHeaders();
        String uri = request.getURI().toString();
        String method = request.getMethod().name();
        int status = response.getStatusCode().value();
        String userId = headers.get("userId").get(0);

        ProductAudit audit = new ProductAudit();
        audit.setCreatedTime(new Date());
        audit.setMethod(method);
        audit.setStatus(status);
        audit.setUri(uri);
        audit.setUserId(Integer.parseInt(userId));

        auditRepository.save(audit);
        logger.info("Stored audit: {}", audit);
    }


    public static class Config {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
