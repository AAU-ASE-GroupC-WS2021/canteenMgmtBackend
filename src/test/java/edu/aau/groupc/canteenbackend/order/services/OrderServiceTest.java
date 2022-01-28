package edu.aau.groupc.canteenbackend.order.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDishDTO;
import edu.aau.groupc.canteenbackend.orders.services.IOrderService;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("H2Database")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderServiceTest {
    final String name = "name";
    final Float price = 1f;
    final Integer count = 2;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IDishService dishService;

    @Test
    public void testFindAllByUserAsDTO_Size() {
        assertTrue(orderService.findAllByUserAsDTO(1).isEmpty());
        createOrder(1, 1);
        assertEquals(1, orderService.findAllByUserAsDTO(1).size());
        assertTrue(orderService.findAllByUserAsDTO(2).isEmpty());
    }

    @Test
    public void testFindAllByUserAsDTO_noOrdersForUser() {
        List<OrderDTO> orderDtos = orderService.findAllByUserAsDTO(1);
        assertNotNull(orderDtos);
        assertTrue(orderDtos.isEmpty());
    }

    @Test
    public void testFindAllByUserAsDTO_size() {
        createOrder(1, 1);
        createOrder(1, 1);
        createOrder(1, 1);
        List<OrderDTO> orderDtos = orderService.findAllByUserAsDTO(1);
        assertEquals(3, orderDtos.size());
    }

    @Test
    public void testFindAllByUserAsDTO_noDishes_toTestEntityToDTOConversion() {
        createOrder(1, 2);
        List<OrderDTO> orderDtos = orderService.findAllByUserAsDTO(1);
        assertEquals(1, orderDtos.size());
        OrderDTO order = orderDtos.get(0);
        assertTrue(order.getDishes().isEmpty());
        assertEquals(2, order.getCanteenId());
    }

    @Test
    public void testFindAllByUserAsDTO_WithDish_toTestEntityToDTOConversion() {
        Dish exampleDish = dishService.create(new Dish(name, price, Dish.Type.MAIN));
        CreateOrderDTO createOrderDTO = createCreateOrderDTOWithExampleDish(1, exampleDish, count);
        orderService.create(createOrderDTO);
        List<OrderDTO> orderDtos = orderService.findAllByUserAsDTO(1);
        assertEquals(1, orderDtos.size());
        OrderDTO orderDTO = orderDtos.get(0);
        assertEquals(1, orderDTO.getDishes().size());
        assertEquals(1, orderDTO.getCanteenId());
        OrderDishDTO dishDTO = orderDTO.getDishes().get(0);
        assertEquals(name, dishDTO.getName());
        assertEquals(price, dishDTO.getPrice());
        assertEquals(Dish.Type.MAIN.name(), dishDTO.getType());
        assertEquals(count, dishDTO.getCount());
    }

    @Test
    public void testCreate() {
        Dish exampleDish = dishService.create(new Dish(name, price, Dish.Type.DESSERT));
        CreateOrderDTO createOrderDTO = createCreateOrderDTOWithExampleDish(1, exampleDish, count);
        OrderDTO orderDTO = orderService.create(createOrderDTO);
        assertEquals(1, orderDTO.getDishes().size());
        assertEquals(1, orderDTO.getCanteenId());
        OrderDishDTO dishDTO = orderDTO.getDishes().get(0);
        assertEquals(name, dishDTO.getName());
        assertEquals(price, dishDTO.getPrice());
        assertEquals(Dish.Type.DESSERT.name(), dishDTO.getType());
        assertEquals(count, dishDTO.getCount());
    }

    // proper test of this function requires a findById method to be usefull
    @Test
    public void testSave_newOrder() {
        Order orderToSave = createOrder(1, 2);
        Order savedOrder = orderService.save(orderToSave);
        assertEquals(orderToSave.getCanteenId(), savedOrder.getCanteenId());
        assertEquals(orderToSave.getUserId(), savedOrder.getUserId());
    }

    private Order createOrder(Integer userId, Integer canteenId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setCanteenId(canteenId);
        return orderService.save(order);
    }

    private CreateOrderDTO createCreateOrderDTOWithExampleDish(Integer userId, Dish dish, Integer count) {
        List<DishForOrderCreationDTO> dishList = new LinkedList<>();
        DishForOrderCreationDTO dto = new DishForOrderCreationDTO();
        dto.setId(dish.getId());
        dto.setCount(count);
        dishList.add(dto);
        CreateOrderDTO orderDTO = new CreateOrderDTO();
        orderDTO.setUserId(userId);
        orderDTO.setCanteenId(1);
        orderDTO.setDishes(dishList);
        return orderDTO;
    }
}
