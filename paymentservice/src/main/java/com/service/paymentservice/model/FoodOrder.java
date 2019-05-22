package com.service.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

public class FoodOrder extends Transaction {
    @Field("food_order_bill")
    private int foodOrderBill;
    @Field("wallets")
    private Map<String, WalletShare> foodOrderWallets;

    public FoodOrder(int foodOrderBill, Map<String, WalletShare> foodOrderWallets) {
        // generate food order transaction id
        String hash = DigestUtils.sha1Hex(new Date() + "secretkey");

        this.transactionId = "OJAP-FOD-" + hash;
        this.transactionType = "FOODORDER";
        this.foodOrderBill = foodOrderBill;
        this.foodOrderWallets = foodOrderWallets;
        this.status = "pending";
        this.cashflow = "debit";
    }

    @JsonProperty("food_order_bill")
    public int getFoodOrderBill() {
        return foodOrderBill;
    }

    @JsonProperty("food_order_wallets")
    public Map<String, WalletShare> getFoodOrderWallets() {
        return foodOrderWallets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public String getCustomerWalletNumber() {
        return foodOrderWallets.get("customer").getWalletNumber();
    }
    @JsonIgnore
    public int getCustomerAmount() {
        return foodOrderWallets.get("customer").getAmount();
    }

    @JsonIgnore
    public String getDriverWalletNumber() {
        return foodOrderWallets.get("driver").getWalletNumber();
    }
    @JsonIgnore
    public int getDriverAmount() {
        return foodOrderWallets.get("driver").getAmount();
    }

    @JsonIgnore
    public String getRestaurantWalletNumber() {
        return foodOrderWallets.get("restaurant").getWalletNumber();
    }
    @JsonIgnore
    public int getRestaurantAmount() {
        return foodOrderWallets.get("restaurant").getAmount();
    }
}

class WalletShare {
    private String walletNumber;
    private int amount;

    public WalletShare(String walletNumber, int amount) {
        this.walletNumber = walletNumber;
        this.amount = amount;
    }

    @JsonProperty("wallet_number")
    public String getWalletNumber() {
        return walletNumber;
    }

    public int getAmount() {
        return amount;
    }
}