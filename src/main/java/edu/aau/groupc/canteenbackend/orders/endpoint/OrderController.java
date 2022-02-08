package edu.aau.groupc.canteenbackend.orders.endpoint;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.orders.services.IOrderService;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api")
public class OrderController {
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @Secured(User.Type.USER)
    @GetMapping(value = "/order")
    public List<OrderDTO> getOrders(@NotNull HttpServletRequest request) {
        return orderService.findAllByUserAsDTO((User) request.getAttribute("user"));
    }

    @Secured(User.Type.USER)
    @GetMapping(value = "/order-by-id")
    public OrderDTO getOrderById(@RequestParam @NotNull Integer orderId) {
        return orderService.findById(orderId);
    }

    @Secured(User.Type.USER)
    @PostMapping(value = "/create-order")
    public OrderDTO createOrder(@NotNull HttpServletRequest request, @Valid @RequestBody CreateOrderDTO newOrder) {
        return orderService.create(newOrder, (User) request.getAttribute("user"));
    }
}
