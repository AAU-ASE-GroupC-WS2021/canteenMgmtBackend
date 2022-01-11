package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.services.DishService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("H2Database")
public class DishControllerTest extends AbstractControllerTest {
    @Autowired
    private DishService dishService;

    @Test
    public void testGetDishes() throws JSONException {
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN));

        ResponseEntity<String> response = makeGetRequest("/dish");
        String expected = "[{\"name\":\"Salad\",\"price\":1.5,\"type\":\"STARTER\"}," +
                           "{\"name\":\"Cheese Burger\",\"price\":4.0,\"type\":\"MAIN\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
}
