package edu.aau.groupc.canteenbackend.orders.services;

import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
    // TODO: UPDATE TO TAKE USER-ENTITY
    List<OrderDTO> findAllByUserAsDTO(Long userId);

    Order save(Order order);

    OrderDTO create(CreateOrderDTO orderDto);
}
