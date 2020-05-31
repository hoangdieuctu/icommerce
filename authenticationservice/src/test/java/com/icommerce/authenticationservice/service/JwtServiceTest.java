package com.icommerce.authenticationservice.service;

import com.icommerce.authenticationservice.service.impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

public class JwtServiceTest {

    private JwtService jwtService = new JwtServiceImpl();

    private String jwtSecret = "secret";
    private Long jwtTtl = 3600000L;
    private String issuer = "http://icommerce.com";

    private int userId = 1;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(jwtService, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtService, "jwtTtl", jwtTtl);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken(userId);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        Claims body = claimsJws.getBody();

        Assert.assertEquals(String.valueOf(userId), body.getId());
        Assert.assertEquals(issuer, body.getIssuer());
        Assert.assertTrue(body.getExpiration().after(new Date()));
    }
}
