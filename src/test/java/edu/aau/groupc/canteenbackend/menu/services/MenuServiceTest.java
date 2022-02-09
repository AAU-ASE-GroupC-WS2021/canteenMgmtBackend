package edu.aau.groupc.canteenbackend.menu.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.repositories.MenuRepository;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("H2Database")
@SpringBootTest
class MenuServiceTest {

    @Autowired
    private IDishService dishService;

    @Autowired
    private MenuRepository menuRepos;
    @Autowired
    private IMenuService menuService;

    @Test
    void testAddMenu() {
        int numDishesBefore = menuService.findAll().size();
        menuService.create(new Menu());
        int numDishesAfter = menuService.findAll().size();
        assertEquals(numDishesBefore, numDishesAfter);
    }

    @Test
    void testFindById() {
        assertThrows(ResponseStatusException.class, () -> menuService.findById(-1));
        Dish exampleDish = dishService.create(new Dish("name", 1, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        Menu menu = menuRepos.save(new Menu("test", 2, Menu.MenuDay.MONDAY, Collections.singletonList(exampleDish.getName())));
        Menu foundMenu = menuService.findById(menu.getId());
        assertEquals(menu.getName(), foundMenu.getName());
        assertEquals(menu.getId(), foundMenu.getId());
        assertEquals(menu.getPrice(), foundMenu.getPrice());
        assertEquals(menu.getMenuDay().name(), foundMenu.getMenuDay().name());
    }


}
