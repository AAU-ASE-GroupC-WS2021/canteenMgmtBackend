package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserDto;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import edu.aau.groupc.canteenbackend.util.JsonTest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("H2Database")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerOwnerTest extends AbstractControllerTest implements JsonTest {

    private MockMvc mvc;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICanteenService canteenService;

    private User existingUser;
    private Canteen existingCanteen;
    private final int invalidCanteenID = 9999;
    private final int invalidUserID = 9999;

    @BeforeEach
    void setupUsers() {
        mvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();

        existingCanteen = createCanteen();
        existingUser = createUser("user1", "a".repeat(64), User.Type.ADMIN);
    }

    @Test
    void createAdmin_validData_ThenOk() throws Exception {
        UserDto userData = new UserDto("username", "a".repeat(64), existingCanteen.getId());
        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/owner/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userData)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject responseJson = convertToJsonObject(res.getResponse().getContentAsString());
        assertEquals(userData.getUsername(), responseJson.get("username"));
        assertEquals(User.Type.ADMIN.toString(), responseJson.get("type"));
        assertEquals(userData.getCanteenID(), responseJson.get("canteenID"));
    }

    @Test
    void createAdmin_duplicateUsername_ThenConflict() throws Exception {
        UserDto userData = new UserDto(existingUser.getUsername(), "a".repeat(64));
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/owner/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userData)))
                .andExpect(status().isConflict());
    }

    @Test
    void createAdmin_invalidCanteen_ThenNotFound() throws Exception {
        UserDto userData = new UserDto("username", "a".repeat(64), invalidCanteenID);
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/owner/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userData)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_validData_ThenOk() throws Exception {
        UserDto userData = new UserDto("username", "a".repeat(64), existingCanteen.getId());
        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .put("/api/owner/user/" + existingUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userData)))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject responseJson = convertToJsonObject(res.getResponse().getContentAsString());
        assertEquals(userData.getUsername(), responseJson.get("username"));
        assertEquals(User.Type.ADMIN.toString(), responseJson.get("type"));
        assertEquals(userData.getCanteenID(), responseJson.get("canteenID"));
    }

    @Test
    void updateUser_keepUsername_ThenOk() throws Exception {
        UserDto userData = new UserDto(existingUser.getUsername(), "a".repeat(64));
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/owner/user/" + existingUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userData)))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser_duplicateUsername_ThenConflict() throws Exception {
        User otherUser = createUser("user2", "", User.Type.USER);
        UserDto userData = new UserDto(existingUser.getUsername(), "a".repeat(64));
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/owner/user/" + otherUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userData)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateUser_invalidUserID_ThenNotFound() throws Exception {
        UserDto userData = new UserDto("username", "a".repeat(64));
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/owner/user/" + invalidUserID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userData)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_invalidCanteenID_ThenNotFound() throws Exception {
        UserDto userData = new UserDto("username", "a".repeat(64), invalidCanteenID);
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/owner/user/" + existingUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userData)))
                .andExpect(status().isNotFound());
    }

    private Canteen createCanteen() {
        return canteenService.create(new Canteen("name", "address", 55));
    }

    private User createUser(String name, String password, User.Type type) {
        return userService.create(new User(name, password, type));
    }
}
