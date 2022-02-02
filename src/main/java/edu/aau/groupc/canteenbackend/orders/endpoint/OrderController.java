package edu.aau.groupc.canteenbackend.orders.endpoint;

import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.orders.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
public class OrderController {
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    // @Secured(User.Type.USER) TODO: ENABLE SECURITY
    @GetMapping(value = "/order")
    public List<OrderDTO> getOrders(@RequestParam @NotNull Long userId) {
        return orderService.findAllByUserAsDTO(userId);
    }

    // @Secured(User.Type.USER) TODO: ENABLE SECURITY
    @PostMapping(value = "/create-order")
    public OrderDTO createOrder(@Valid @RequestBody CreateOrderDTO newOrder) {
        return orderService.create(newOrder);
    }
}
