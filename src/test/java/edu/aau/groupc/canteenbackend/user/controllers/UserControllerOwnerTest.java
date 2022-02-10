package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.UserTestUtils;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    void getUsers_noParam_thenAll() throws Exception {
        createUser(User.Type.USER);
        createUser(User.Type.ADMIN);
        createUser(User.Type.ADMIN);

        mvc.perform( MockMvcRequestBuilders
                        .get("/api/owner/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // keep standard OWNER and existingUser in mind
                .andExpect(jsonPath("$", hasSize(3+2)));
    }

    @Test
    void getUsers_type_thenOnlyDesired() throws Exception {
        createUser(User.Type.USER);
        createUser(User.Type.ADMIN);
        createUser(User.Type.ADMIN);

        mvc.perform( MockMvcRequestBuilders
                        .get("/api/owner/user")
                        .param("type", User.Type.ADMIN.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // keep existingUser in mind
                .andExpect(jsonPath("$", hasSize(2+1)));
    }

    @Test
    void getUsers_canteenID_thenOnlyDesired() throws Exception {
        Canteen c1 = createCanteen();
        Canteen c2 = createCanteen();
        createUser(c1.getId());
        createUser(c1.getId());
        createUser(c2.getId());

        mvc.perform( MockMvcRequestBuilders
                        .get("/api/owner/user")
                        .param("canteenID", c2.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // keep standard OWNER in mind
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getUsers_canteenIDAndType_thenOnlyDesired() throws Exception {
        Canteen c1 = createCanteen();
        Canteen c2 = createCanteen();
        createUser(User.Type.ADMIN, c1.getId());
        createUser(User.Type.USER, c1.getId());
        createUser(User.Type.ADMIN, c2.getId());

        mvc.perform( MockMvcRequestBuilders
                        .get("/api/owner/user")
                        .param("canteenID", c1.getId().toString())
                        .param("type", User.Type.ADMIN.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // keep standard OWNER in mind
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private User createUser(String name, String password, User.Type type) {
        return userService.create(new User(name, password, type));
    }

    private User createUser(User.Type type, Integer canteenID) {
        Canteen canteen = canteenID != null ? canteenService.findById(canteenID).get() : null;
        return userService.create(new User(UserTestUtils.getRandomUsername(), "somePassword",
                type, canteen));
    }

    private User createUser(Integer canteenID) {
        return createUser(User.Type.USER, canteenID);
    }

    private User createUser(User.Type type) {
        return createUser(type, null);
    }

    private Canteen createCanteen() {
        return canteenService.create(new Canteen("myCanteen", "myAddress", 69));
    }
}
