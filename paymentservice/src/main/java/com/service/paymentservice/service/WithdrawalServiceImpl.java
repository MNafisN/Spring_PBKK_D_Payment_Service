package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.exception.BadRequestException;
import com.service.paymentservice.exception.NotFoundException;
import com.service.paymentservice.model.Wallet;
import com.service.paymentservice.model.Withdrawal;
import com.service.paymentservice.repository.WalletRepository;
import com.service.paymentservice.repository.WithdrawalRepository;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalServiceImpl implements WithdrawalService{
    private WalletRepository walletRepository;
    private WithdrawalRepository withdrawalRepository;
    private JwtService jwtService;

    public WithdrawalServiceImpl(WalletRepository walletRepository,
                                 WithdrawalRepository withdrawalRepository,
                                 JwtService jwtService) {
        this.walletRepository = walletRepository;
        this.withdrawalRepository = withdrawalRepository;
        this.jwtService = jwtService;
    }

    @Override
    public WalletRepository getWalletRepository() {
        return walletRepository;
    }

    @Override
    public WithdrawalRepository getWithdrawalRepository() {
        return withdrawalRepository;
    }

    @Override
    public Claims getClaims(String token) {
        return jwtService.getBody(token);
    }

    public int isValid(Withdrawal wd) {
        // incomplete fields
        if (wd.getAmount() <= 0 || wd.getBankName() == null || wd.getBankAccount() == null)
            throw new BadRequestException("Some or all fields are empty");

        Wallet w = walletRepository.findByWalletNumber(wd.getWalletNumber());
        // wallet not found
        if (w == null)
            throw new NotFoundException("Wallet Number for this withdrawal is non-existent");
        // wallet insufficient funds
        else if (w.getBalance() - wd.getAmount() < 0)
            throw new BadRequestException("Wallet Balance is insufficient to complete withdrawal");

        return 0;
    }

    public boolean isPending(Withdrawal wd) {
        return wd.getStatus().equals("pending");
    }
}
