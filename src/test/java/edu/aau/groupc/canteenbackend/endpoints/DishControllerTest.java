package edu.aau.groupc.canteenbackend.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.io.UnsupportedEncodingException;

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

    @Test
    void testGetDishes() throws JSONException {
        dishService.deleteAllDishes("all");
        dishService.create(new Dish("Salad", 1.5f, Dish.Type.STARTER, Dish.DishDay.MONDAY));
        dishService.create(new Dish("Cheese Burger", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));

        ResponseEntity<String> response = makeGetRequest("/dish");
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
    void testUpdateDishNoSuchDish() throws Exception {
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put("/dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Dish("Salad1", 2f, Dish.Type.STARTER, Dish.DishDay.MONDAY))))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "No Such Dish found";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }


    @Test
    void testUpdateDishes() throws Exception {
        MvcResult res_old = mvc.perform(MockMvcRequestBuilders
                        .post("/dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Dish("Salad1", 2f, Dish.Type.STARTER, Dish.DishDay.MONDAY))))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put("/dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new Dish("Salad1", 2f, Dish.Type.STARTER, Dish.DishDay.MONDAY))))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "dish updated";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

    // old tests

    @Test
    void testDeleteDish() throws Exception {

        dishService.create(new Dish("Salad1", 4.0f, Dish.Type.MAIN, Dish.DishDay.MONDAY));

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .delete("/dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Salad1"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "dish deleted";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void testDeleteNoDish() throws Exception {
        dishService.deleteAllDishes("all");
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .delete("/dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Salad1"))
                .andExpect(status().isOk())
                .andReturn();
        String expected = "No Such Dish";
        assertThat(res.getResponse().getContentAsString()).isEqualTo(expected);
    }


}
