package edu.aau.groupc.canteenbackend.user.services;

import edu.aau.groupc.canteenbackend.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("H2Database")
@SpringBootTest
class UserServiceTest {

    @Autowired
    IUserService userService;

    @Test
    void createUser_addOneUser_addsUserAndReturnsIt() {
        long numUsersBefore = userService.findAll().size();

        userService.create(new User("TestUser123", "0123456789ABCDEF", User.Type.USER));

        assertEquals(numUsersBefore + 1, userService.findAll().size());
    }

    @Test
    void createUser_addOneUsernameTwoTimes_addsOnlyOneUser() {
        long numUsersBefore = userService.findAll().size();

        userService.create(new User("TestUser123x", "0123456789ABCDEFa", User.Type.USER));
        userService.create(new User("TestUser123x", "0123456789ABCDEFb", User.Type.USER));

        assertEquals(numUsersBefore + 1, userService.findAll().size());
    }

}
