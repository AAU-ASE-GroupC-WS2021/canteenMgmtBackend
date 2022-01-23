package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("H2Database")
class UserControllerTest extends AbstractControllerTest {

    @Autowired
    IUserService userService;

    @Test
    void userGET_createNewUserManuallyThenGET_returnsCorrect() throws JSONException {
        userService.create(new User("TestUser123", "0123456789ABCDEF", User.Type.USER));

        ResponseEntity<String> response = makeGetRequest("/user");

        JSONArray expected = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("username", "TestUser123");
        object.put("password", "0123456789ABCDEF");
        object.put("type", "USER");
        expected.put(object);

        JSONAssert.assertEquals(expected.toString(), response.getBody(), false);
    }

}
