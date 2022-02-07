package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.services.DishService;
import edu.aau.groupc.canteenbackend.util.JsonTest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("H2Database")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DishControllerTest extends AbstractControllerTest implements JsonTest {

    private MockMvc mvc;
    @Autowired
    private DishService dishService;
    private final HttpHeaders headers = new HttpHeaders();

    @BeforeAll
    void setupMVC() {
        mvc = MockMvcBuilders.standaloneSetup(new DishController(dishService)).build();
    }


    protected ResponseEntity<String> makeGetRequestDish(String uri) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                String.class);
    }

    protected ResponseEntity<String> makePostRequestDish(String uri, Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.POST,
                new HttpEntity<>(newDish, headers),
                String.class);
    }
    protected ResponseEntity<String> makePutRequestDish(String uri, Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.PUT,
                new HttpEntity<>(newDish, headers),
                String.class);
    }

    protected ResponseEntity<String> makeDeleteRequest(String uri, Menu newDish) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.DELETE,
                new HttpEntity<>(newDish, headers),
                String.class);
    }

    @Test
    void testGetDishes() throws JSONException {
        dishService.deleteAllDishes("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));

        ResponseEntity<String> response = makeGetRequestDish("/dish");
        String expected = "[{\"name\":\"Salad\",\"price\":1.5,\"type\":\"STARTER\",\"dishDay\":\"MONDAY\"}," +
                           "{\"name\":\"Cheese Burger\",\"price\":4.0,\"type\":\"MAIN\",\"dishDay\":\"MONDAY\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void testCreateDishes() throws Exception {
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                .post("/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new Dish("Salad1", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY))))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "{\"name\":\"Salad1\",\"price\":1.5,\"type\":\"STARTER\",\"dishDay\":\"MONDAY\"}";
        JSONAssert.assertEquals(expected, res.getResponse().getContentAsString(), false);
    }

    @Test
    void testUpdateDish()  {
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePutRequest("/dish", new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY));
        String expected = "dish updated";
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void testUpdateDishNON()  {
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makePutRequest("/dish", new Dish ("Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY));
        String expected = "No Such Dish found";
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void testDeleteDish() {

        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
//        dishService.delete(new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY));

        ResponseEntity<String> response = makeDeleteRequest("/dish", new Dish ("Cheese Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY) );
        String expected = "dish deleted";

        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void testDeleteDishNonExisting() {
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));
        ResponseEntity<String> response = makeDeleteRequest("/dish", new Dish ("Burger", 2.0f, Dish.Type.STARTER, Dish.DishDay.WEDNESDAY) );
        String expected = "No Such Dish";
        assertThat(response.getBody()).isEqualTo(expected);
    }



}
