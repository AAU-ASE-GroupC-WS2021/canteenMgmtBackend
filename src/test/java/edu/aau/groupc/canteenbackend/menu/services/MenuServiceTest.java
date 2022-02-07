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
class MenuServiceTest {

    @Autowired
    private IMenuService menuService;

    @Test
    void testAddMenu() {
        int numDishesBefore = menuService.findAll().size();
        menuService.create(new Menu());
        int numDishesAfter = menuService.findAll().size();
        assertEquals(numDishesBefore, numDishesAfter);
    }





}
