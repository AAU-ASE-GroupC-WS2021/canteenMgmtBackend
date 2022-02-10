package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("H2Database")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class DishServiceTest {

    @Autowired
    private IDishService dishService;

    @Test
    void testAddDish() {
        int numDishesBefore = dishService.findAll().size();
        dishService.create(new Dish());
        int numDishesAfter = dishService.findAll().size();
        assertEquals(numDishesBefore + 1, numDishesAfter);
    }

    @Test
    void testFindById() {
        assertThrows(ResponseStatusException.class, () -> dishService.findById(-1));
        Dish dish = createDish();
        Dish foundDish = dishService.findById(dish.getId());
        assertEquals(dish.getName(), foundDish.getName());
        assertEquals(dish.getId(), foundDish.getId());
        assertEquals(dish.getPrice(), foundDish.getPrice());
        assertEquals(dish.getType().name(), foundDish.getType().name());
    }

    private Dish createDish() {
        return dishService.create(new Dish("test", 5, Dish.Type.MAIN, Dish.DishDay.MONDAY));
    }

    @Test
    void testDeleteAllDish() {
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.deleteAllDishes("all");
        int numDishesActual = dishService.findAll().size();
        assertEquals(0, numDishesActual);
    }

    @Test
    void testDeleteAllDishFalse(){
        int numDishesBefore = dishService.findAll().size();
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<Object> response = dishService.deleteAllDishes("all1");
        String expected = "invalid input parameter";
        assertEquals(expected, response.getBody());
    }

}
