package com.icommerce.apigateway.repository;

import com.icommerce.apigateway.model.ProductAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAuditRepository extends CrudRepository<ProductAudit, Integer> {
}
