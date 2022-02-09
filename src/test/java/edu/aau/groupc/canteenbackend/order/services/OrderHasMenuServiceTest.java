package edu.aau.groupc.canteenbackend.order.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.repositories.MenuRepository;
import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.OrderHasMenu;
import edu.aau.groupc.canteenbackend.orders.services.IOrderHasMenuService;
import edu.aau.groupc.canteenbackend.orders.services.IOrderService;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("H2Database")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderHasMenuServiceTest {
    @Autowired
    IOrderHasMenuService orderHasMenuService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IDishService dishService;
    @Autowired
    MenuRepository menuRepo;

    @Test
    void testSaveOrderHasDish() {
        Order order = createOrder();
        Dish dish = createDish();
        Menu menu = menuRepo.save(new Menu("test", 2, Menu.MenuDay.MONDAY, Collections.singletonList(dish.getName())));
        OrderHasMenu orderHasMenu = new OrderHasMenu(order, menu, 2);
        OrderHasMenu savedOrderHasMenu = orderHasMenuService.save(orderHasMenu);
        assertEquals(orderHasMenu.getMenu().getId(), savedOrderHasMenu.getMenu().getId());
        assertEquals(orderHasMenu.getOrder().getId(), savedOrderHasMenu.getOrder().getId());
        assertEquals(orderHasMenu.getCount(), savedOrderHasMenu.getCount());
    }

    @Test
    void testSaveOrderHasDish_UtilizingSetters() {
        Order order = createOrder();
        Dish dish = createDish();
        Menu menu = menuRepo.save(new Menu("test", 2, Menu.MenuDay.MONDAY, Collections.singletonList(dish.getName())));
        OrderHasMenu orderHasMenu = new OrderHasMenu();
        orderHasMenu.setOrder(order);
        orderHasMenu.setMenu(menu);
        orderHasMenu.setCount(2);
        OrderHasMenu savedOrderHasMenu = orderHasMenuService.save(orderHasMenu);
        assertEquals(orderHasMenu.getMenu().getId(), savedOrderHasMenu.getMenu().getId());
        assertEquals(orderHasMenu.getOrder().getId(), savedOrderHasMenu.getOrder().getId());
        assertEquals(orderHasMenu.getCount(), savedOrderHasMenu.getCount());
    }

    private Order createOrder() {
        Order order = new Order();
        return orderService.save(order);
    }

    private Dish createDish() {
        Dish dish = new Dish("name", 2, Dish.Type.MAIN, Dish.DishDay.MONDAY);
        return dishService.create(dish);
    }
}
