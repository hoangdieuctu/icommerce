package com.icommerce.authenticationservice.dto;

public class JwtDto {

    private String schema;
    private String token;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtDto{" +
                "schema='" + schema + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
