package me.pyradian.ojackpayment.controller;

import com.mongodb.lang.Nullable;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import me.pyradian.ojackpayment.exception.BadRequestException;
import me.pyradian.ojackpayment.exception.ConflictException;
import me.pyradian.ojackpayment.exception.NotFoundException;
import me.pyradian.ojackpayment.model.QWallet;
import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.repository.WalletRepository;
import me.pyradian.ojackpayment.service.WalletService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WalletController.BASE_URL)
public class WalletController {
    public static final String BASE_URL = "/api/v1/wallet";

    private WalletRepository walletRepository;
    private WalletService walletService;

    public WalletController(WalletRepository walletRepository, WalletService walletService) {
        this.walletRepository = walletRepository;
        this.walletService = walletService;
    }

    @GetMapping
    public List<Wallet> getAllWallet(@Nullable @RequestParam("type") String type,
                                     @Nullable @RequestParam("balance_range") String balanceRange) {
        QWallet wallet = new QWallet("wallet");
        BooleanBuilder builder = new BooleanBuilder();
        if (type != null)
            builder.and(wallet.type.eq(type));
        if (balanceRange != null) {
            int low, high;
            String[] val = balanceRange.split(",");
            System.out.println(val[0] + " " + val[1]);
            val[0] = (val[0].equals(""))? "0": val[0];
            val[1] = (val[0].equals(""))? "9999999999": val[1];
            low = Integer.parseInt(val[0]);
            high = Integer.parseInt(val[1]);
            builder.and(wallet.balance.between(low, high));
        }

        return (builder.getValue() == null? this.walletRepository.findAll():
                (List<Wallet>) this.walletRepository.findAll(builder.getValue()));
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet w) {
        int res = this.walletService.isValidWallet(w);

        if (res == -1) // incomplete field
            throw new BadRequestException("Some or all fields are empty");
        else if (res == -2) // wallet number already exists
            throw new ConflictException("Wallet number is already registered");
        else if (res == -3) // invalid wallet type
            throw new BadRequestException("Invalid wallet type");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", WalletController.BASE_URL + "/" + w.getWalletNumber());
        this.walletRepository.insert(w);
        return new ResponseEntity<>(w, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{walletNumber}")
    public Wallet getWallet(@PathVariable("walletNumber") String walletNumber) {
        Wallet w = this.walletRepository.findByWalletNumber(walletNumber);

        if (w == null)
            throw new NotFoundException("Wallet number " + walletNumber + " is non-existent");

        return w;
    }

}
