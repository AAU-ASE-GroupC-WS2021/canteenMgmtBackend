package edu.aau.groupc.canteenbackend.menu.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.endpoints.DishController;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.controller.MenuController;
import edu.aau.groupc.canteenbackend.menu.services.MenuService;
import edu.aau.groupc.canteenbackend.services.DishService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import edu.aau.groupc.canteenbackend.util.JsonTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("H2Database")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MenuControllerTest extends AbstractControllerTest implements JsonTest {


    private MockMvc mvc;

    @Autowired
    private DishService dishService;
    @Autowired
    private MenuService menuService;

    @BeforeAll
    void setupMVC() {
        mvc = MockMvcBuilders.standaloneSetup(new MenuController(menuService)).build();
    }


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
    void testCreateMenu1() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"id\":2,\"name\":\"veg menu\",\"price\":2.0,\"menuDay\":\"MONDAY\",\"menuDishNames\":[\"Salad\",\"Cheese Burger\",\"Tiramisu\"]}";
        JSONAssert.assertEquals(expected, res.getResponse().getContentAsString(), false);
    }


    @Test
    void testCreateMenuAlreadyExist() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "Menu already exists";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void testCreateMenuDishNotExist() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "Salad Dish does not exist in the database";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void testDeleteMenuInvalid() {
        ResponseEntity<Object> response = menuService.deleteAllMenus("all1");
        String expected = "invalid input parameter";
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void testFindMenuByDay() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        List<Menu> menuList = menuService.findByMenuDay("MONDAY");
        int numOfMenusOld = menuList.size();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu1", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        List<Menu> menuList2 = menuService.findByMenuDay("MONDAY");
        int numOfMenusNew = menuList2.size();
        assertThat(numOfMenusNew).isEqualTo(numOfMenusOld + 1);
    }

    @Test
    void testUpdateMenu() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Burger", 2.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu1", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu1", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "Menu Updated";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void testUpdateMenuDishNotExist() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu1", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu1", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu", "Burger")))))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "Burger Dish does not exist in the database";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void testUpdateMenuNotExist() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu1", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "No Such Menu Found";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void testDeleteMenu() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .delete("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("veg menu"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "Menu deleted";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void testDeleteMenuNotExist() throws Exception {
        dishService.deleteAllDishes("all");
        menuService.deleteAllMenus("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Tiramisu", 2.0f, Dish.Type.DESSERT, Dish.DishDay.MONDAY));
        mvc.perform(MockMvcRequestBuilders
                        .post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Menu("veg menu", 2.0f, Menu.MenuDay.MONDAY, Arrays.asList("Salad", "Cheese Burger", "Tiramisu")))))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .delete("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("veg menu1"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "No Such Menu";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

}
