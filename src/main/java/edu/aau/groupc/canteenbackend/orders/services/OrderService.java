package edu.aau.groupc.canteenbackend.orders.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.services.IMenuService;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.OrderHasDish;
import edu.aau.groupc.canteenbackend.orders.OrderHasMenu;
import edu.aau.groupc.canteenbackend.orders.dao.OrderRepository;
import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.orders.dto.MenuForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.services.IDishService;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepo;

    IDishService dishService;
    IOrderHasDishService orderHasDishService;
    IOrderHasMenuService orderHasMenuService;
    ICanteenService canteenService;
    IMenuService menuService;

    @Autowired
    public OrderService(OrderRepository orderRepo, IDishService dishService, IOrderHasDishService orderHasDishService, IOrderHasMenuService orderHasMenuService, ICanteenService canteenService, IMenuService menuService) {
        this.orderRepo = orderRepo;
        this.dishService = dishService;
        this.orderHasDishService = orderHasDishService;
        this.orderHasMenuService = orderHasMenuService;
        this.canteenService = canteenService;
        this.menuService = menuService;
    }

    /*
     * returns a list of the orderDtos
     */
    @Override
    public List<OrderDTO> findAllByUserAsDTO(User user) {
        List<Order> orderEntityList = orderRepo.findAllByUserOrderByPickUpDate(user);
        List<OrderDTO> dtoList = new LinkedList<>();
        for (Order order : orderEntityList) {
            dtoList.add(OrderDTO.from(order));
        }
        return dtoList;
    }

    @Override
    public OrderDTO findById(int orderId) throws ResponseStatusException {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        return OrderDTO.from(orderOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found")));
    }

    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public OrderDTO create(CreateOrderDTO orderDto, User user) {
        Order order = new Order();
        order.setCanteen(canteenService.findEntityById(orderDto.getCanteenId()));
        order.setUser(user);
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

        // add all the required assoziations from the order to the dishes
        for (MenuForOrderCreationDTO menuDto : orderDto.getMenus()) {
            Menu menu = menuService.findById(menuDto.getId());
            OrderHasMenu orderHasMenu = new OrderHasMenu(order, menu, menuDto.getCount());
            order.addOrderHasMenu(orderHasMenuService.save(orderHasMenu));
        }
        return OrderDTO.from(save(order));
    }

}
