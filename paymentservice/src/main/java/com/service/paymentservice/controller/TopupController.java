package com.service.paymentservice.controller;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.aop.JwtToken;
import com.service.paymentservice.aop.TokenAuth;
import com.service.paymentservice.exception.ConflictException;
import com.service.paymentservice.exception.NotFoundException;
import com.service.paymentservice.model.Topup;
import com.service.paymentservice.model.Wallet;
import com.service.paymentservice.service.TopupService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(TopupController.BASE_URL)
public class TopupController {
    public static final String BASE_URL = "/api/v1/transaction/topup";

    private TopupService topupService;

    public TopupController(TopupService topupService)
    {
        this.topupService = topupService;
    }

    @TokenAuth(strict = false)
    @GetMapping
    public List<Topup> getAllTopup(@JwtToken String token) {
        Claims claims = this.topupService.getClaims(token);

        // if admin, show all topup transactions
        if (claims.get("rol").equals("ADMIN"))
            return topupService.getTopupRepository().findAll();

        return topupService.getTopupRepository().findByWalletNumber(claims.getSubject());
    }

    @TokenAuth(auth_role = "USER", account_type = "customer")
    @PostMapping
    public ResponseEntity<Topup> requestTopup(@RequestBody Topup t,
                                              @JwtToken String token) {
        Claims claims = topupService.getClaims(token);
        this.topupService.isValid(t);

        t.setWalletNumber(claims.getSubject());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", TopupController.BASE_URL + "/" + t.getTransactionId());
        topupService.getTopupRepository().insert(t);

        return new ResponseEntity<>(t, headers, HttpStatus.CREATED);
    }

    @TokenAuth
    @PatchMapping("/confirm/{topupId}")
    public Topup confirmTopup(@PathVariable("topupId") String topupId) {
        Topup t = topupService.getTopupRepository().findByTransactionId(topupId);
        if (t == null)
            throw new NotFoundException("Topup with ID " + topupId + " is non-existent");
        if (!this.topupService.isPending(t))
            throw new ConflictException("Topup with ID " + topupId + " is already confirmed/canceled");

        Wallet w = topupService.getWalletRepository().findByWalletNumber(t.getWalletNumber());
        w.setBalance(w.getBalance() + t.getTopupBalance());
        t.setStatus("confirmed");
        topupService.getWalletRepository().save(w);
        topupService.getTopupRepository().save(t);

        return t;
    }

    @TokenAuth
    @PatchMapping("/cancel/{topupId}")
    public Topup cancelTopup(@PathVariable("topupId") String topupId) {
        Topup t = topupService.getTopupRepository().findByTransactionId(topupId);

        if (!this.topupService.isPending(t))
            throw new ConflictException("Topup with ID " + topupId + " is already confirmed/canceled");

        t.setStatus("canceled");
        topupService.getTopupRepository().save(t);

        return t;
    }


}
