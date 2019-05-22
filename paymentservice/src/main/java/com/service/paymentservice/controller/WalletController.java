package com.service.paymentservice.controller;

import com.mongodb.lang.Nullable;
import com.querydsl.core.BooleanBuilder;
import io.jsonwebtoken.Claims;
import com.service.paymentservice.aop.JwtToken;
import com.service.paymentservice.aop.TokenAuth;
import com.service.paymentservice.exception.NotFoundException;
import com.service.paymentservice.exception.UnauthorizedException;
import com.service.paymentservice.model.QWallet;
import com.service.paymentservice.model.Wallet;
import com.service.paymentservice.service.WalletService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WalletController.BASE_URL)
public class WalletController {
    public static final String BASE_URL = "/api/v1/wallet";

    private WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @TokenAuth(strict = false)
    @GetMapping
    public Object getWallet(@Nullable @RequestParam("type") String type,
                            @Nullable @RequestParam("balance_range") String balanceRange,
                            @JwtToken String token) {
        Claims claims = walletService.getClaims(token);

        // for admin
        if (claims.get("rol").equals("ADMIN")) {
            QWallet wallet = new QWallet("wallet");
            BooleanBuilder builder = new BooleanBuilder();
            if (type != null)
                builder.and(wallet.type.eq(type));
            if (balanceRange != null) {
                int low, high;
                String[] val = balanceRange.split(",");
                System.out.println(val[0] + " " + val[1]);
                val[0] = (val[0].equals("")) ? "0" : val[0];
                val[1] = (val[0].equals("")) ? "9999999999" : val[1];
                low = Integer.parseInt(val[0]);
                high = Integer.parseInt(val[1]);
                builder.and(wallet.balance.between(low, high));
            }

            return (builder.getValue() == null ? walletService.repository().findAll() :
                    (List<Wallet>) walletService.repository().findAll(builder.getValue()));
        }

        Wallet w = walletService.repository().findByWalletNumber(claims.getSubject());
        return w;
    }

    @TokenAuth
    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet w) {
        this.walletService.isValidWallet(w);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", WalletController.BASE_URL + "/" + w.getWalletNumber());
        walletService.repository().insert(w);
        return new ResponseEntity<>(w, headers, HttpStatus.CREATED);
    }

    @TokenAuth(strict = false)
    @GetMapping("/{walletNumber}")
    public Wallet getWalletDetail(@PathVariable("walletNumber") String walletNumber,
                                  @JwtToken String token) {
        Claims claims = walletService.getClaims(token);
        Wallet w = walletService.repository().findByWalletNumber(walletNumber);

        // non-existent wallet
        if (w == null)
            throw new NotFoundException("Wallet number " + walletNumber + " is non-existent");

        // if admin, always return the wallet detail
        if (claims.get("rol").equals("ADMIN"))
            return w;

        // if user, only it's own wallet
        if (!walletNumber.equals(claims.getSubject()))
            throw new UnauthorizedException("Cannot view other's wallet's detail");

        return w;
    }

}
