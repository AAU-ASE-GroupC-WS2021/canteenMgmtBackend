package edu.aau.groupc.canteenbackend.orders.dao;

import edu.aau.groupc.canteenbackend.orders.OrderHasDish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHasDishRepository extends JpaRepository<OrderHasDish, Integer> {
}
