package com.service.paymentservice.repository;

import com.service.payment.model.FoodOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends MongoRepository<FoodOrder, String> {
    @Query(value = "{'wallets.customer.walletNumber': ?0}")
    List<FoodOrder> findByCustomerWalletNumber(String walletNumber);
    FoodOrder findByTransactionId(String foodOrderId);

    @Override
    @Query(value = "{transaction_type: 'FOODORDER'}")
    List<FoodOrder> findAll();
}
