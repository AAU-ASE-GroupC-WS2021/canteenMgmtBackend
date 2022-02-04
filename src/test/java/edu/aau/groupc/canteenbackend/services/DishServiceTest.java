package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("H2Database")
@SpringBootTest
public class DishServiceTest {

    @Autowired
    private IDishService dishService;

    @Test
    public void testAddDish() {
        int numDishesBefore = dishService.findAll().size();
        dishService.create(new Dish());
        int numDishesAfter = dishService.findAll().size();
        assertEquals(numDishesBefore+1, numDishesAfter);
    }

    @Test
    public void testDeleteAllDish() {
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.deleteAllDishes("all");
        int numDishesAtual = dishService.findAll().size();
        assertEquals(0, numDishesAtual);
    }

    @Test
    public void testDeleteAllDishFalse(){
        int numDishesBefore = dishService.findAll().size();
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity response = dishService.deleteAllDishes("all1");
        String expected = "invalid input parameter";
        assertEquals(expected, response.getBody());
    }

}
