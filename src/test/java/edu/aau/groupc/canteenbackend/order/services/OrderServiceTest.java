package edu.aau.groupc.canteenbackend.order.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.DishForOrderCreationDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDTO;
import edu.aau.groupc.canteenbackend.orders.dto.OrderDishDTO;
import edu.aau.groupc.canteenbackend.orders.services.IOrderService;
import edu.aau.groupc.canteenbackend.services.IDishService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("H2Database")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderServiceTest {
    final String name = "name";
    final Float price = 1f;
    final Integer count = 2;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IDishService dishService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICanteenService canteenService;

    @Test
    void testFindAllByUserAsDTO_Size() {
        User user = createExampleUser();
        User emptyUser = userService.create(new User("test", "a", User.Type.GUEST));
        System.out.println(user);
        System.out.println(emptyUser);
        assertTrue(orderService.findAllByUserAsDTO(user).isEmpty());
        createOrder(user, createCanteen());
        assertEquals(1, orderService.findAllByUserAsDTO(user).size());
        assertTrue(orderService.findAllByUserAsDTO(emptyUser).isEmpty());
    }

    @Test
    void testFindAllByUserAsDTO_noOrdersForUser() {
        List<OrderDTO> orderDtos = orderService.findAllByUserAsDTO(createExampleUser());
        assertNotNull(orderDtos);
        assertTrue(orderDtos.isEmpty());
    }

    @Test
    void testFindAllByUserAsDTO_multipleOrders() {
        User user = createExampleUser();
        createOrder(user, createCanteen());
        createOrder(user, createCanteen());
        createOrder(user, createCanteen());
        List<OrderDTO> orderDtos = orderService.findAllByUserAsDTO(user);
        assertEquals(3, orderDtos.size());
    }

    @Test
    void testFindAllByUserAsDTO_noDishes_toTestEntityToDTOConversion() {
        User user = createExampleUser();
        Canteen canteen = createCanteen();
        createOrder(user, canteen);
        List<OrderDTO> orderDtos = orderService.findAllByUserAsDTO(user);
        assertEquals(1, orderDtos.size());
        OrderDTO order = orderDtos.get(0);
        assertTrue(order.getDishes().isEmpty());
        assertEquals(canteen.getId(), order.getCanteenId());
    }

    @Test
    void testFindAllByUserAsDTO_WithDish_toTestEntityToDTOConversion() {
        User user = createExampleUser();
        Canteen canteen = createCanteen();
        Dish exampleDish = dishService.create(new Dish(name, price, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        CreateOrderDTO createOrderDTO = createCreateOrderDTOWithExampleDish(exampleDish, count, new Date(), canteen.getId());
        orderService.create(createOrderDTO, user);
        List<OrderDTO> orderDtos = orderService.findAllByUserAsDTO(user);
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
    void testCreate() {
        User user = createExampleUser();
        Canteen canteen = createCanteen();
        Dish exampleDish = dishService.create(new Dish(name, price, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        CreateOrderDTO createOrderDTO = createCreateOrderDTOWithExampleDish(exampleDish, count, new Date(), canteen.getId());
        OrderDTO orderDTO = orderService.create(createOrderDTO, user);
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
    void testSave_newOrder() {
        Order orderToSave = createOrder(createExampleUser(), createCanteen());
        Order savedOrder = orderService.save(orderToSave);
        assertEquals(orderToSave.getCanteen().getId(), savedOrder.getCanteen().getId());
        assertEquals(orderToSave.getUser().getId(), savedOrder.getUser().getId());
    }

    private Order createOrder(User user, Canteen canteen) {
        Order order = new Order();
        order.setUser(user);
        order.setCanteen(canteen);
        return orderService.save(order);
    }

    private Canteen createCanteen() {
        return canteenService.create(new Canteen("test", "address", 1));
    }

    private User createExampleUser() {
        return userService.create(new User("testname", "pw", User.Type.USER));
    }

    private CreateOrderDTO createCreateOrderDTOWithExampleDish(Dish dish, Integer count, Date date, Integer canteenId) {
        List<DishForOrderCreationDTO> dishList = new LinkedList<>();
        DishForOrderCreationDTO dto = new DishForOrderCreationDTO();
        dto.setId(dish.getId());
        dto.setCount(count);
        dishList.add(dto);
        CreateOrderDTO orderDTO = new CreateOrderDTO();
        orderDTO.setCanteenId(canteenId);
        orderDTO.setPickupDate(date);
        orderDTO.setDishes(dishList);
        return orderDTO;
    }
}
