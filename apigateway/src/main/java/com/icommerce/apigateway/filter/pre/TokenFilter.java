package com.icommerce.apigateway.filter.pre;

import com.icommerce.apigateway.exception.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {

    private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private static final String HEADER_AUTH_KEY = "Authorization";

    @Value("${jwt.secret}")
    private String jwtSecret;


    public TokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            Claims claims;
            try {
                claims = extractJwtToken(exchange.getRequest());
            } catch (JwtException ex) {
                logger.info("Jwt exception: {}", ex.getMessage());
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            String userId = claims.getId();

            // add custom header
            ServerHttpRequest request = exchange.getRequest().mutate().header("userId", userId).build();
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    private Claims extractJwtToken(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey(HEADER_AUTH_KEY)) {
            throw new JwtException("Authorization header is missing");
        }

        List<String> headers = request.getHeaders().get(HEADER_AUTH_KEY);
        if (headers == null || headers.isEmpty()) {
            throw new JwtException("Authorization header is empty");
        }

        String token = headers.get(0).trim();
        if (token.isEmpty()) {
            throw new JwtException("Authorization token is empty");
        }

        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Authorization token is expired");
        }
    }

    public static class Config {
    }

}
