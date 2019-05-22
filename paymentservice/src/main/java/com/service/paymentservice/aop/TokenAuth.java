package com.service.paymentservice.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TokenAuth {
    /*
     if strict then auth_role is enforced
     */
    boolean strict() default true;

    /*
     auth role is either ADMIN or USER
     */
    String auth_role() default "ADMIN";

    /*
     account type:
        auth_role ADMIN is always root,
        auth_role USER can be a driver, customer, or restaurant
     */
    String account_type() default "root";
}