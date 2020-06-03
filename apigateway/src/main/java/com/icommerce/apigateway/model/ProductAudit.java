package com.icommerce.apigateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "product_audit")
public class ProductAudit {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private String uri;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private int status;

    private Date createdTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ProductAudit{" +
                "id=" + id +
                ", userId=" + userId +
                ", uri='" + uri + '\'' +
                ", method='" + method + '\'' +
                ", status=" + status +
                ", createdTime=" + createdTime +
                '}';
    }
}
