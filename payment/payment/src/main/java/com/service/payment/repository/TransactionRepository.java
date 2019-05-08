package me.pyradian.ojackpayment.repository;

import me.pyradian.ojackpayment.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    @Query(value = "{status: ?0}")
    List<Transaction> findAll(String status);
}
