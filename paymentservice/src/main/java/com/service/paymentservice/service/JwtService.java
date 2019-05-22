package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    Claims getBody(String authToken);
}
