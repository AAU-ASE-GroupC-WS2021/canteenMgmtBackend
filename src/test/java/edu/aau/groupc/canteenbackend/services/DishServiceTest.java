package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.dto.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DishServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    public void testAddDish() {
        int numDishesBefore = dishService.findAll().size();
        dishService.create(new Dish());
        int numDishesAfter = dishService.findAll().size();
        assertEquals(numDishesBefore+1, numDishesAfter);
    }

}
