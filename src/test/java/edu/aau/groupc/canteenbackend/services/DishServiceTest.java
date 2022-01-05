package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.AppConfig;
import edu.aau.groupc.canteenbackend.dto.Dish;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
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
