//package edu.aau.groupc.canteenbackend.menu.endpoints;
//
//import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
//import edu.aau.groupc.canteenbackend.entities.Dish;
//import edu.aau.groupc.canteenbackend.services.DishService;
//import org.json.JSONException;
//import org.junit.jupiter.api.Test;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@ActiveProfiles("H2Database")
//public class MenuControllerTest extends AbstractControllerTest {
//    @Autowired
//    private DishService dishService;
//
//    @Test
//    public void testGetDishes() throws JSONException {
//        dishService.deleteAllDishes("all");
//        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
//        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
//
//        ResponseEntity<String> response = makeGetRequest("/dish");
//        String expected = "[{\"name\":\"Salad\",\"price\":1.5,\"type\":\"STARTER\",\"dishDay\":\"MONDAY\"}," +
//                           "{\"name\":\"Cheese Burger\",\"price\":4.0,\"type\":\"MAIN\",\"dishDay\":\"MONDAY\"}]";
//
//        JSONAssert.assertEquals(expected, response.getBody(), false);
//    }
//
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
//
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
//    public void testDeleteDishNonExisting() throws JSONException {
//        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
//        ResponseEntity<String> response = makeDeleteRequest("/dish", new Dish ("Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY) );
//        String expected = "No Such Dish";
//        assertThat(expected.equals(response.getBody())).isTrue();
//    }
//
//
//}
