package edu.aau.groupc.canteenbackend.orders.dao;

import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserOrderByPickUpDate(User user);
}
