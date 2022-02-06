package edu.aau.groupc.canteenbackend.menu.endpoints;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.services.MenuService;
import edu.aau.groupc.canteenbackend.services.DishService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("H2Database")
public class MenuControllerTest extends AbstractControllerTest {
    @Autowired
    private DishService dishService;
    @Autowired
    private MenuService menuService;

    @Test
    public void testGetMenus() throws JSONException {
//        dishService.deleteAllDishes("all");
//        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
//        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
//        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
//        dishService.create(new Dish("Soup", 1.5f, Dish.Type.STARTER, Dish.DishDay.TUESDAY));
//        dishService.create(new Dish("Pasta", 4.0f, Dish.Type.MAIN, Dish.DishDay.TUESDAY));
//        dishService.create(new Dish("Milk Pudding", 2.0f, Dish.Type.DESSERT, Dish.DishDay.TUESDAY));
        Dish dish1 = new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY);
        Dish dish2 = new Dish("Cheese Burger", 1.5f, Dish.Type.MAIN, Dish.DishDay.MONDAY);
        Dish dish3 = new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY);
        List<String> menuDishes = Arrays.asList(dish1.getName(),dish2.getName(),dish3.getName());

        menuService.create(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, menuDishes ));

        ResponseEntity<String> response = makeGetRequest("/menu");

//        String expected = "[{\"id\":1,\"name\":\"veg menu\",\"price\":2.0,\"menuDay\":\"MONDAY\",\"menuDishes\":[]}]";
        String expected = "[{\"id\":1,\"name\":\"veg menu\",\"price\":2.0,\"menuDay\":\"MONDAY\",\"menuDishNames\":[\"Salad\",\"Cheese Burger\",\"Tiramisu\"]}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

//    @Test
//    public void testCreateDishes() throws JSONException {
//        ResponseEntity<String> response = makePostRequest("/dish", new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
//        String expected = "{\"name\":\"Salad\",\"price\":1.5,\"type\":\"STARTER\",\"dishDay\":\"MONDAY\"}";
//        JSONAssert.assertEquals(expected, response.getBody(), false);
//    }
////
////
//    @Test
//    public void testUpdateDish() throws JSONException {
//        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
//        ResponseEntity<String> response = makePutRequest("/dish", new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY));
//        String expected = "{\"name\":\"Cheese Burger\",\"price\":2.0,\"type\":\"STARTER\",\"dishDay\":\"WEDNESDAY\"}";
//
//        JSONAssert.assertEquals(expected, response.getBody(), false);
//    }

//    @Test
//    public void testDeleteDish() throws JSONException {
//
//        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
////        dishService.delete(new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY));
//
//        ResponseEntity<String> response = makeDeleteRequest("/dish", new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY) );
//        String expected = " ";
//
//        JSONAssert.assertEquals(null, response.getBody(), false);
//    }
//
//    @Test
//    public void testDeleteDishNonExisting() throws AssertionFailedError {
//        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
//        ResponseEntity<String> response = makeDeleteRequest("/dish", new Dish ("Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY) );
//        String expected = "No Such Dish";
//        assertThat(expected.equals(response.getBody())).isTrue();
//    }


}
