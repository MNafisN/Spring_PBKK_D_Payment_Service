package com.service.paymentservice.repository;

import com.service.paymentservice.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String>, QuerydslPredicateExecutor<Wallet> {
    Wallet findByWalletNumber(String walletNumber);
}
