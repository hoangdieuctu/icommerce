package com.icommerce.productservice.dto.request;

public class ProductCriteria {

    private String name;
    private String color;
    private String branch;

    private PriceCriteria price;
    private Sort sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public PriceCriteria getPrice() {
        return price;
    }

    public void setPrice(PriceCriteria price) {
        this.price = price;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
