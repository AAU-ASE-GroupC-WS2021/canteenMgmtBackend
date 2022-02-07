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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("H2Database")
class MenuControllerTest extends AbstractControllerTest {
    @Autowired
    private DishService dishService;
    @Autowired
    private MenuService menuService;

    private final HttpHeaders headers = new HttpHeaders();

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
    void testGetMenus() throws JSONException {
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
    void testCreateMenu() throws JSONException {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "{\"id\":7,\"name\":\"veg menu\",\"price\":2.0,\"menuDay\":\"MONDAY\",\"menuDishNames\":[\"Salad\",\"Cheese Burger\",\"Tiramisu\"]}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void testCreateMenuAlreadyExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response_old = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "Menu already exists";
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void testCreateMenuDishNotExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "Salad Dish does not exist in the database";
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void testDeleteMenuInvalid() {
        ResponseEntity<Object> response = menuService.deleteAllMenus("all1");
        String expected = "invalid input parameter";
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void testFindMenuByDay() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        List<Menu> menuList = menuService.findByMenuDay("MONDAY");
        int numOfMenusOld=menuList.size();
        ResponseEntity<String> response_new = makePostRequest(new Menu("veg menu2", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        List<Menu> menuList2 = menuService.findByMenuDay("MONDAY");
        int numOfMenusNew=menuList2.size();
        assertThat(numOfMenusNew).isEqualTo(numOfMenusOld+1);
    }

    @Test
    void testUpdateMenu() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Burger", 2.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makePutRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu", "Burger")));
        String expected = "Menu Updated";
        assertThat(response_new.getBody()).isEqualTo(expected);
    }

    @Test
    void testUpdateMenuDishNotExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makePutRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu", "Burger")));
        String expected = "Burger Dish does not exist in the database";
        assertThat(response_new.getBody()).isEqualTo(expected);
    }

    @Test
    void testUpdateMenuNotExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makePutRequest(new Menu("Healthy menu1", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "No Such Menu Found";
        assertThat(response_new.getBody()).isEqualTo(expected);
    }

    @Test
    void testDeleteMenu() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makeDeleteRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "Menu deleted";
        assertThat(response_new.getBody()).isEqualTo(expected);
    }

    @Test
    void testDeleteMenuNotExist() {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePostRequest(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        ResponseEntity<String> response_new = makeDeleteRequest(new Menu("Healthy menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")));
        String expected = "No Such Menu";
        assertThat(response_new.getBody()).isEqualTo(expected);
    }

}
