package me.pyradian.ojackpayment.repository;

import me.pyradian.ojackpayment.model.FoodOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends MongoRepository<FoodOrder, String> {
    FoodOrder findByTransactionId(String foodOrderId);

    @Override
    @Query(value = "{transaction_type: 'FOODORDER'}")
    List<FoodOrder> findAll();
}
