package me.pyradian.ojackpayment.repository;

import me.pyradian.ojackpayment.model.Topup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopupRepository extends MongoRepository<Topup, String> {
    Topup findByTransactionId(String topupId);

    @Override
    @Query(value = "{transaction_type: 'TOPUP'}")
    List<Topup> findAll();

    List<Topup> findByWalletNumber(String walletNumber);
    List<Topup> findByStatus(String status);
}
