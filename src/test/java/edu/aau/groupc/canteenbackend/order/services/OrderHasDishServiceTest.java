package edu.aau.groupc.canteenbackend.order.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.OrderHasDish;
import edu.aau.groupc.canteenbackend.orders.services.IOrderHasDishService;
import edu.aau.groupc.canteenbackend.orders.services.IOrderService;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("H2Database")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderHasDishServiceTest {
    @Autowired
    IOrderHasDishService orderHasDishService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IDishService dishService;

    @Test
    void testSaveOrderHasDish() {
        Order order = createOrder();
        Dish dish = createDish();
        OrderHasDish orderHasDish = new OrderHasDish(order, dish, 2);
        OrderHasDish savedOrderHasDish = orderHasDishService.save(orderHasDish);
        assertEquals(orderHasDish.getDish().getId(), savedOrderHasDish.getDish().getId());
        assertEquals(orderHasDish.getOrder().getId(), savedOrderHasDish.getOrder().getId());
        assertEquals(orderHasDish.getCount(), savedOrderHasDish.getCount());
    }

    private Order createOrder() {
        Order order = new Order();
        return orderService.save(order);
    }

    private Dish createDish() {
        Dish dish = new Dish("name", 2, Dish.Type.MAIN);
        return dishService.create(dish);
    }
}
