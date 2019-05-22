package com.service.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class Topup extends Transaction {
    @Field("topup_wallet_number")
    private String walletNumber;
    @Field("topup_balance")
    private int topupBalance;

    public Topup(String walletNumber, int topupBalance) {
        // generate topup transaction id
        String hash = DigestUtils.sha1Hex(new Date() + walletNumber + topupBalance);

        this.transactionId = "OJAP-TOP-" + hash;
        this.transactionType = "TOPUP";
        this.cashflow = "credit";
        this.walletNumber = walletNumber;
        this.topupBalance = topupBalance;
        this.status = "pending";
    }

    @JsonProperty("wallet_number")
    public String getWalletNumber() {
        return walletNumber;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    @JsonProperty("topup_balance")
    public int getTopupBalance() {
        return topupBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
