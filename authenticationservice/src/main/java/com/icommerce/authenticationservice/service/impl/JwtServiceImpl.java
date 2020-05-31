package com.icommerce.authenticationservice.service.impl;

import com.icommerce.authenticationservice.service.JwtService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.ttl}")
    private Long jwtTtl;

    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public String generateToken(Integer userId) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(userId))
                .setIssuedAt(now)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + jwtTtl;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }
}
