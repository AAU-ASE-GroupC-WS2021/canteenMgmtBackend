package edu.aau.groupc.canteenbackend.orders.dao;

import edu.aau.groupc.canteenbackend.orders.OrderHasMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHasMenuRepository extends JpaRepository<OrderHasMenu, Integer> {
}
