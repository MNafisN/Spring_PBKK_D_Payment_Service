package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.model.Topup;
import com.service.paymentservice.repository.TopupRepository;
import com.service.paymentservice.repository.WalletRepository;

public interface TopupService {
     WalletRepository getWalletRepository();
     TopupRepository getTopupRepository();

     Claims getClaims(String token);

     int isValid(Topup t);
     boolean isPending(Topup t);
}
