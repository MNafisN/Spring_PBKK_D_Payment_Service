package com.service.paymentservice.service;

import io.jsonwebtoken.Claims;
import com.service.paymentservice.exception.BadRequestException;
import com.service.paymentservice.model.FoodOrder;
import com.service.paymentservice.model.Wallet;
import com.service.paymentservice.repository.FoodOrderRepository;
import com.service.paymentservice.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderServiceImpl implements FoodOrderService {

    private WalletRepository walletRepository;
    private FoodOrderRepository foodOrderRepository;
    private JwtService jwtService;

    public FoodOrderServiceImpl(WalletRepository walletRepository,
                                FoodOrderRepository foodOrderRepository,
                                JwtService jwtService) {
        this. walletRepository = walletRepository;
        this.foodOrderRepository = foodOrderRepository;
        this.jwtService = jwtService;
    }

    @Override
    public WalletRepository getWalletRepository() {
        return walletRepository;
    }

    @Override
    public FoodOrderRepository getFoodOrderRepository() {
        return foodOrderRepository;
    }

    @Override
    public Claims getClaims(String token) {
        return jwtService.getBody(token);
    }

    @Override
    public boolean canCheckOrder(FoodOrder fo, String walletNumber) {
        return walletNumber.equals(fo.getCustomerWalletNumber()) ||
                walletNumber.equals(fo.getDriverWalletNumber()) ||
                walletNumber.equals(fo.getRestaurantWalletNumber());
    }

    public int isValid(FoodOrder fo) {
        // incomplete fields
        if (fo.getFoodOrderBill() == 0 || fo.getFoodOrderWallets() == null)
            throw new BadRequestException("Some or all fields are empty");
        // invalid bill
        else if (fo.getFoodOrderBill() <= 0)
            throw new BadRequestException("Invalid bill received. Must be > 0");
        // missing wallet number or share amount
        else if(!checkWallet(fo))
            throw new BadRequestException("Some or all wallets credential are missing");
        // customer balance is insufficient
        else if(!checkCustomerBalance(fo))
            throw new BadRequestException("Customer wallet balance is insufficient to complete the order");

        return 0;
    }

    public boolean isPending(FoodOrder fo) {
        return fo.getStatus().equals("pending");
    }

    private boolean checkWallet(FoodOrder fo) {
        if(fo.getCustomerWalletNumber() == null && fo.getCustomerAmount() == 0 &&
                fo.getDriverWalletNumber() == null && fo.getDriverAmount() == 0 &&
                fo.getRestaurantWalletNumber() == null && fo.getRestaurantAmount() == 0)
            return false;

        // check wallet existence per user
        Wallet customerWallet = this.walletRepository.findByWalletNumber(fo.getCustomerWalletNumber());
        Wallet driverWallet = this.walletRepository.findByWalletNumber(fo.getDriverWalletNumber());
        Wallet restaurantWallet = this.walletRepository.findByWalletNumber(fo.getRestaurantWalletNumber());

        return customerWallet != null && driverWallet != null && restaurantWallet != null;
    }

    private boolean checkCustomerBalance(FoodOrder fo) {
        Wallet w = this.walletRepository.findByWalletNumber(fo.getCustomerWalletNumber());

        return (w.getBalance() - fo.getFoodOrderBill()) >= 0;
    }

}
