package me.pyradian.ojackpayment.repository;

import me.pyradian.ojackpayment.model.Withdrawal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends MongoRepository<Withdrawal, String> {
    Withdrawal findByTransactionId(String withdrawalId);

    @Override
    @Query(value = "{transaction_type: 'WITHDRAWAL'}")
    List<Withdrawal> findAll();
}
