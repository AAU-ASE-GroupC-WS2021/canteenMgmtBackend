package edu.aau.groupc.canteenbackend.menu.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.services.IMenuService;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("H2Database")
@SpringBootTest
public class MenuServiceTest {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IDishService dishService;
    @Test
    public void testFindAllMenus() {
        int numDishesBefore = menuService.findAllMenus().size();
        dishService.create(new Dish());
        int numDishesAfter = menuService.findAllMenus().size();
        assertEquals(numDishesBefore+1, numDishesAfter);
    }

}
