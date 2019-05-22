package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;

@Service
public class JwtServiceImpl implements JwtService{
    public static final String secretKey = "EmYTCuhxT3$FpXUVXDj*f0e4LbcpDAk^apes1eAuUtUrC3V%1XPSGD^2KPj*u^L&";

    @Override
    public Claims getBody(String authToken) {
        String token = authToken.split(" ")[1];
        Claims claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                        .parseClaimsJws(token).getBody();

        return claims;
    }
}
