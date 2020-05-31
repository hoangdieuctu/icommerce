package com.icommerce.productservice.dto.request;

public class Sort {

    private String sortBy;
    private Order orderBy;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Order getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Order orderBy) {
        this.orderBy = orderBy;
    }
}
