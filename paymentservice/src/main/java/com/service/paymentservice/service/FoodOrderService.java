package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.model.FoodOrder;
import com.service.paymentservice.repository.FoodOrderRepository;
import com.service.paymentservice.repository.WalletRepository;

public interface FoodOrderService {
    WalletRepository getWalletRepository();
    FoodOrderRepository getFoodOrderRepository();

    Claims getClaims(String token);

    boolean canCheckOrder(FoodOrder fo, String walletNumber);

    int isValid(FoodOrder fo);
    boolean isPending(FoodOrder fo);
}
