package edu.aau.groupc.canteenbackend.orders.dao;

import edu.aau.groupc.canteenbackend.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // TODO: UPDATE TO USE THE USER ENTITY
    List<Order> findAllByUserId(Integer userId);
}
