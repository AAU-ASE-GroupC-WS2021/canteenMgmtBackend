package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.controllers.CanteenController;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import edu.aau.groupc.canteenbackend.util.JsonTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("H2Database")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerOwnerTest extends AbstractControllerTest implements JsonTest {

    private MockMvc mvc;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICanteenService canteenService;

    private User existingUser;
    private Canteen existingCanteen;

    @BeforeAll
    void setupUsers() {
        mvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();

        existingCanteen = createCanteen();
        existingUser = createUser("user1", "a".repeat(64), User.Type.ADMIN);
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

    private Canteen createCanteen() {
        return canteenService.create(new Canteen("name", "address", 55));
    }

    private User createUser(String name, String password, User.Type type) {
        return userService.create(new User(name, password, type));
    }
}
