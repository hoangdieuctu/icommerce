package com.icommerce.productservice.dto.response;

public class ProductResponse {

    private int productId;
    private int qty;
    private int remainingQty;
    private Long price;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(int remainingQty) {
        this.remainingQty = remainingQty;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "productId=" + productId +
                ", qty=" + qty +
                ", remainingQty=" + remainingQty +
                ", price=" + price +
                '}';
    }
}
