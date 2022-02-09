package edu.aau.groupc.canteenbackend.orders.services;

import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface IOrderService {
    List<OrderDTO> findAllByUserAsDTO(User user);

    OrderDTO findById(int oderId) throws ResponseStatusException;

    Order save(Order order);

    OrderDTO create(CreateOrderDTO orderDto, User user);
}
