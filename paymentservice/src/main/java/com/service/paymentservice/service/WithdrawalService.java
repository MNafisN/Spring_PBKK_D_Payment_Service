package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.model.Withdrawal;
import com.service.paymentservice.repository.WalletRepository;
import com.service.paymentservice.repository.WithdrawalRepository;

public interface WithdrawalService {
    WalletRepository getWalletRepository();
    WithdrawalRepository getWithdrawalRepository();

    Claims getClaims(String token);

    int isValid(Withdrawal wd);
    boolean isPending(Withdrawal wd);
}
