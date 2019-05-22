package com.service.paymentservice.aop;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.exception.UnauthorizedException;
import com.service.paymentservice.service.JwtService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class TokenAuthAspect {

    private JwtService jwtService;

    public TokenAuthAspect(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Before("@annotation(tokenAuth)")
    public void tokenAuth(TokenAuth tokenAuth) throws Throwable {
        ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader == null)
            throw new UnauthorizedException("Token required. Make sure 'Authorization' key is in request header");
        Claims claims = jwtService.getBody(authHeader);

        // if not strict, ADMIN and USER can have the access
        if (tokenAuth.strict() == false) {
            return;
        }

        // get token auth_role
        if (!tokenAuth.auth_role().equals(claims.get("rol"))) {
            throw new UnauthorizedException("Unauthorized bro!");
        }

        // get token account_type if not admin
        if ( tokenAuth.auth_role().equals("USER") ) {
            String[] types = tokenAuth.account_type().split(",");
            boolean exists = false;
            for(String type: types) {
                System.out.println(type);
                if (type.equals(claims.get("atp"))) exists = true;
            }
            if (!exists) throw new UnauthorizedException("Unauthorized bro!");
        }
    }
}
