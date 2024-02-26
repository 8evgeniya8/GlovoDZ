package com.example.glovodz.repository;

import com.example.glovodz.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order,Integer> {
}
