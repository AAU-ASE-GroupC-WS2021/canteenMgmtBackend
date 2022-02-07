package edu.aau.groupc.canteenbackend.mgmt.controllers;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.util.JsonTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("H2Database")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CanteenControllerTest extends AbstractControllerTest implements JsonTest {

    private MockMvc mvc;
    private final int invalidID = 9999;
    private final String malformedID = "xyz543";

    @Autowired
    private ICanteenService canteenService;

    @BeforeAll
    void setupCanteens() {
        mvc = MockMvcBuilders.standaloneSetup(new CanteenController(canteenService)).build();

        createCanteen("Canteen #1", "Address #1", 11);
        createCanteen("Canteen #2", "Address #2", 22);
        createCanteen("Canteen #3", "Address #3", 33);
    }

    @Test
    void testFindAll_ThenReturnsAll() throws Exception {
        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .get("/api/canteen")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertJsonArrayEquals(canteenService.findAll(), res.getResponse().getContentAsString());
    }

    @Test
    void testFindById_ValidId_ThenReturnsCanteen() throws Exception {
        Canteen c = createCanteen("MyCanteen", "MyAddress", 42);

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .get("/api/canteen/"+c.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertJsonObjectEquals(c, res.getResponse().getContentAsString());
    }

    @Test
    void testFindById_InvalidId_ThenNotFound() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/api/canteen/"+invalidID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindById_MalformedId_ThenUnprocessableEntity() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/api/canteen/"+malformedID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testCreate_ValidData_ThenOKAndResponse() throws Exception {
        Canteen newCanteen = new Canteen("ValidName", "ValidAddress", 44);
        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/canteen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newCanteen)))
                .andExpect(status().isOk())
                .andReturn();

        assertJsonObjectEquals(newCanteen, res.getResponse().getContentAsString());
    }

    @Test
    void testUpdate_ValidId_ThenOKAndResponse() throws Exception {
        Canteen c = createCanteen("ValidName", "ValidAddress", 44);
        c.setName("UpdatedName");
        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .put("/api/canteen/"+c.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(c)))
                .andExpect(status().isOk())
                .andReturn();

        assertJsonObjectEquals(c, res.getResponse().getContentAsString());
    }

    @Test
    void testUpdate_InvalidId_ThenNotFound() throws Exception {
        Canteen newCanteen = new Canteen("ValidName", "ValidAddress", 44);
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/canteen/"+invalidID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newCanteen)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testUpdate_MalformedId_ThenUnprocessableEntity() throws Exception {
        Canteen newCanteen = new Canteen("ValidName", "ValidAddress", 44);
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/canteen/"+malformedID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newCanteen)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();
    }

    private Canteen createCanteen(String name, String address, Integer numSeats) {
        return canteenService.create(new Canteen(name, address, numSeats));
    }
}
