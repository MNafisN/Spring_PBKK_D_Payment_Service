package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.model.Wallet;
import com.service.paymentservice.repository.WalletRepository;

public interface WalletService {
    int isValidWallet(Wallet w);
    WalletRepository repository();
    Claims getClaims(String token);
}