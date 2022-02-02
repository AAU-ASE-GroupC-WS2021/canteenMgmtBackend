package edu.aau.groupc.canteenbackend.orders.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.OrderHasDish;
import edu.aau.groupc.canteenbackend.orders.dao.OrderRepository;
import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDishDTO;
import edu.aau.groupc.canteenbackend.services.IDishService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepo;

    // TODO: setup all required services, e.g.: User, Canteen and Menu
    IDishService dishService;
    IOrderHasDishService orderHasDishService;
    IUserService userService;
    ICanteenService canteenService;

    @Autowired
    public OrderService(OrderRepository orderRepo, IDishService dishService, IOrderHasDishService orderHasDishService, IUserService userService, ICanteenService canteenService) {
        this.orderRepo = orderRepo;
        this.dishService = dishService;
        this.orderHasDishService = orderHasDishService;
        this.userService = userService;
        this.canteenService = canteenService;
    }

    /*
     * returns a list of the orderDtos
     */
    @Override
    public List<OrderDTO> findAllByUserAsDTO(Long userId) {
        User user = userService.findById(userId);
        List<Order> orderEntityList = orderRepo.findAllByUserOrderByPickUpDate(user);
        List<OrderDTO> dtoList = new LinkedList<>();
        for (Order order : orderEntityList) {
            dtoList.add(convertOrderEntityToDto(order));
        }
        return dtoList;
    }

    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public OrderDTO create(CreateOrderDTO orderDto) {
        Order order = new Order();
        order.setCanteen(canteenService.findEntityById(orderDto.getCanteenId()));
        order.setUser(userService.findById(orderDto.getUserId()));
        order.setPickUpDate(orderDto.getPickupDate());
        order.setReserveTable(orderDto.isReserveTable());
        // order needs to be pushed to DB to create the assoziations
        order = save(order);
        // add all the required assoziations from the order to the dishes
        for (DishForOrderCreationDTO dishDto : orderDto.getDishes()) {
            Dish d = dishService.findById(dishDto.getId());
            OrderHasDish orderHasDish = new OrderHasDish(order, d, dishDto.getCount());
            order.addOrderHasDish(orderHasDishService.save(orderHasDish));
        }
        return convertOrderEntityToDto(save(order));
    }

    /*
     * helper method to convert the entity into the dto
     */
    private OrderDTO convertOrderEntityToDto(Order order) {
        OrderDTO orderDto = new OrderDTO();
        orderDto.setId(order.getId());
        orderDto.setCanteenId(order.getCanteen().getId());
        orderDto.setReserveTable(order.isReserveTable());
        orderDto.setPickupDate(order.getPickUpDate());
        for (OrderHasDish orderHasDish : order.getOrderHasDishes()) {
            Dish dish = orderHasDish.getDish();
            OrderDishDTO dishDto = new OrderDishDTO(dish.getName(), dish.getPrice(), dish.getType().toString(), dish.getId(), orderHasDish.getCount());
            orderDto.addDish(dishDto);
        }
        return orderDto;
    }
}
