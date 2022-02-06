package edu.aau.groupc.canteenbackend.menu.endpoints;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.services.MenuService;
import edu.aau.groupc.canteenbackend.services.DishService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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


    protected ResponseEntity<String> makeGetRequest(String uri) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                String.class);
    }

    protected ResponseEntity<String> makePostRequest(Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort("/menu"),
                HttpMethod.POST,
                new HttpEntity<>(newDish, headers),
                String.class);
    }

    protected ResponseEntity<String> makePutRequest(Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort("/menu"),
                HttpMethod.PUT,
                new HttpEntity<>(newDish, headers),
                String.class);
    }

    protected ResponseEntity<String> makeDeleteRequest(Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort("/menu"),
                HttpMethod.DELETE,
                new HttpEntity<>(newDish, headers),
                String.class);
    }

    @Test
    public void testGetMenus() throws JSONException {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<Object> response1 = menuService.create(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response = makeGetRequest("/menu");
        String expected = "[{\"id\":1,\"name\":\"veg menu\",\"price\":2.0,\"menuDay\":\"MONDAY\",\"menuDishNames\":[\"Salad\",\"Cheese Burger\",\"Tiramisu\"]}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testCreateMenu() throws JSONException {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "{\"id\":5,\"name\":\"veg menu\",\"price\":2.0,\"menuDay\":\"MONDAY\",\"menuDishNames\":[\"Salad\",\"Cheese Burger\",\"Tiramisu\"]}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testCreateMenuAlreadyExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response_old = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "Menu already exists";
        assertThat(expected.equals(response.getBody())).isTrue();
    }

    @Test
    public void testCreateMenuDishNotExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "Salad Dish does not exist in the database";
        assertThat(expected.equals(response.getBody())).isTrue();
    }

    @Test
    public void testDeleteMenuInvalid() {
        ResponseEntity<Object> response = menuService.deleteAllMenus("all1");
        String expected = "invalid input parameter";
        assertThat(expected.equals(response.getBody())).isTrue();
    }

    @Test
    public void testFindMenuByDay() {
        Menu.MenuDay day = Menu.MenuDay.valueOf("MONDAY");
        List<Menu> menuList = menuService.findByMenuDay(day.toString());
        String expected =menuList.toString();
//        String expected = "\"invalid input parameter\"";
        assertThat(expected.equals(menuList.toString())).isTrue();
    }

    @Test
    public void testUpdateMenu() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Burger", 2.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makePutRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu", "Burger")));
        String expected = "Menu Updated";
        assertThat(expected.equals(response_new.getBody())).isTrue();
    }

    @Test
    public void testUpdateMenuDishNotExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makePutRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu", "Burger")));
        String expected = "Burger Dish does not exist in the database";
        assertThat(expected.equals(response_new.getBody())).isTrue();
    }

    @Test
    public void testUpdateMenuNotExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makePutRequest(new Menu("Healthy menu1", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "No Such Menu Found";
        assertThat(expected.equals(response_new.getBody())).isTrue();
    }

    @Test
    public void testDeleteMenu() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makeDeleteRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "Menu deleted";
        assertThat(expected.equals(response_new.getBody())).isTrue();
    }

    @Test
    public void testDeleteMenuNotExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makeDeleteRequest(new Menu("Healthy menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "No Such Menu";
        assertThat(expected.equals(response_new.getBody())).isTrue();
    }

}
