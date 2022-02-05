package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("H2Database")
@SpringBootTest
public class DishServiceTest {

    @Autowired
    private IDishService dishService;

    final String noDishFoundExceptionMessage = "no dish found";

    @Test
    public void testAddDish() {
        int numDishesBefore = dishService.findAll().size();
        dishService.create(new Dish());
        int numDishesAfter = dishService.findAll().size();
        assertEquals(numDishesBefore + 1, numDishesAfter);
    }

    @Test
    public void testFindById() {
        Exception ex = assertThrows(ResponseStatusException.class, () -> dishService.findById(-1));
        assertEquals(noDishFoundExceptionMessage, ex.getMessage());
        Dish dish = createDish();
        Dish foundDish = dishService.findById(dish.getId());
        assertEquals(dish.getName(), foundDish.getName());
        assertEquals(dish.getId(), foundDish.getId());
        assertEquals(dish.getPrice(), foundDish.getPrice());
        assertEquals(dish.getType().name(), foundDish.getType().name());
    }

    private Dish createDish() {
        return dishService.create(new Dish("test", 5, Dish.Type.MAIN));
    }

}
