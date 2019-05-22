package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.exception.BadRequestException;
import com.service.paymentservice.model.Topup;
import com.service.paymentservice.model.Wallet;
import com.service.paymentservice.repository.TopupRepository;
import com.service.paymentservice.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class TopupServiceImpl implements TopupService{

    private WalletRepository walletRepository;
    private TopupRepository topupRepository;
    private JwtService jwtService;

    public TopupServiceImpl(WalletRepository walletRepository,
                            TopupRepository topupRepository,
                            JwtService jwtService) {
        this.walletRepository = walletRepository;
        this.topupRepository = topupRepository;
        this.jwtService = jwtService;
    }

    public WalletRepository getWalletRepository() {
        return this.walletRepository;
    }

    public TopupRepository getTopupRepository() {
        return this.topupRepository;
    }

    public Claims getClaims(String token) {
        return this.jwtService.getBody(token);
    }

    public int isValid(Topup t) {
        if (t.getTopupBalance() <= 25000)
            throw new BadRequestException("Minimum topup balance is 25000");

        return 0;
    }

    public boolean isPending(Topup t) {
        return t.getStatus().equals("pending");
    }

    private boolean checkWalletType(Topup t) {
        Wallet w = this.walletRepository.findByWalletNumber(t.getWalletNumber());

        return w.getType().equals("customer");
    }
}
