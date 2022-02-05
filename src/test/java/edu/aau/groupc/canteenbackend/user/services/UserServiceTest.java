package edu.aau.groupc.canteenbackend.user.services;

import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.exceptions.CanteenNotFoundException;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.UserTestUtils;
import edu.aau.groupc.canteenbackend.user.dto.UserDto;
import edu.aau.groupc.canteenbackend.user.dto.UserUpdateDTO;
import edu.aau.groupc.canteenbackend.user.exceptions.UserNotFoundException;
import edu.aau.groupc.canteenbackend.user.exceptions.UsernameConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("H2Database")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceTest {

    @Autowired
    IUserService userService;

    @Autowired
    ICanteenService canteenService;

    private final int invalidCanteenID = 9999;
    private final int invalidUserID = 9999;

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

    @Test
    void createUserFromDTO_validDataNoCanteen_thenOk() throws CanteenNotFoundException, UsernameConflictException {
        UserDto userData = new UserDto("someName", "somePass");
        User u = userService.create(userData, User.Type.ADMIN);
        assertEquals(userData.getUsername(), u.getUsername());
        assertEquals(userData.getPassword(), u.getPassword());
        assertEquals(User.Type.ADMIN, u.getType());
    }

    @Test
    void createUserFromDTO_validDataWithCanteen_thenOk() throws CanteenNotFoundException, UsernameConflictException {
        Canteen canteen = createCanteen();

        UserDto userData = new UserDto("someName", "somePass", canteen.getId());
        User u = userService.create(userData, User.Type.ADMIN);
        assertEquals(userData.getUsername(), u.getUsername());
        assertEquals(userData.getPassword(), u.getPassword());
        assertEquals(User.Type.ADMIN, u.getType());
        assertEquals(canteen.getId(), u.getHomeCanteen().getId());
    }

    @Test
    void createUserFromDTO_invalidCanteenID_throwsException() {
        UserDto userData = new UserDto("someName", "somePass", invalidCanteenID);
        assertThrows(CanteenNotFoundException.class, () -> userService.create(userData, User.Type.ADMIN));
    }

    @Test
    void createUserFromDTO_duplicateUsername_throwsException() throws CanteenNotFoundException, UsernameConflictException {
        String username = "someName";
        UserDto userData1 = new UserDto(username, "somePass");
        UserDto userData2 = new UserDto(username, "somePass");
        userService.create(userData1, User.Type.ADMIN);
        assertThrows(UsernameConflictException.class, () -> userService.create(userData2, User.Type.ADMIN));
    }

    @Test
    // @Transactional is required here for the lazy loading of home canteen to work
    @Transactional
    void updateUser_validData_updatedUserReturned() throws UserNotFoundException, CanteenNotFoundException, UsernameConflictException {
        Canteen canteen = createCanteen();
        User existingUser = userService.create(new User("username", "password", User.Type.ADMIN, canteen));

        UserUpdateDTO updateInfo = UserUpdateDTO.create("newUsername", "newPassword", User.Type.USER, canteen.getId());
        User updatedUser = userService.updateUser(existingUser.getId(), updateInfo);

        assertEquals(updateInfo.getUsername(), updatedUser.getUsername());
        assertEquals(updateInfo.getPassword(), updatedUser.getPassword());
        assertEquals(updateInfo.getType(), updatedUser.getType());
        assertEquals(canteen, updatedUser.getHomeCanteen());
    }

    @Test
    // @Transactional is required here for the lazy loading of home canteen to work
    @Transactional
    void updateUser_validDataNullCanteenID_updatedUserReturned() throws UserNotFoundException, CanteenNotFoundException, UsernameConflictException {
        Canteen canteen = createCanteen();
        User existingUser = userService.create(new User("username", "password", User.Type.ADMIN, canteen));

        UserUpdateDTO updateInfo = UserUpdateDTO.create("newUsername", "newPassword", User.Type.USER, null);
        User updatedUser = userService.updateUser(existingUser.getId(), updateInfo);

        assertEquals(updateInfo.getUsername(), updatedUser.getUsername());
        assertEquals(updateInfo.getPassword(), updatedUser.getPassword());
        assertEquals(updateInfo.getType(), updatedUser.getType());
        assertNull(updatedUser.getHomeCanteen());
    }

    @Test
    void updateUser_invalidCanteen_throwsException() {
        User existingUser = userService.create(new User("username", "password", User.Type.ADMIN));
        UserUpdateDTO updateInfo = UserUpdateDTO.create("newUsername", "newPassword", User.Type.USER, invalidCanteenID);
        assertThrows(CanteenNotFoundException.class, () -> userService.updateUser(existingUser.getId(), updateInfo));
    }

    @Test
    void updateUser_duplicateUsername_throwsException() {
        userService.create(new User("username1", "password", User.Type.ADMIN));
        User existingUser2 = userService.create(new User("username2", "password", User.Type.ADMIN));
        UserUpdateDTO updateInfo = UserUpdateDTO.create("username1", "newPassword", User.Type.USER, null);
        assertThrows(UsernameConflictException.class, () -> userService.updateUser(existingUser2.getId(), updateInfo));
    }

    @Test
    void updateUser_invalidUserId_throwsException() {
        UserUpdateDTO updateInfo = UserUpdateDTO.create("username1", "newPassword", User.Type.USER, null);
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(invalidUserID, updateInfo));
    }

    @Test
    void findUserByType_returnsOnlyDesiredUsers() {
        createUser(User.Type.ADMIN);
        createUser(User.Type.ADMIN);
        createUser(User.Type.USER);
        createUser(User.Type.OWNER);

        List<User> results = userService.findByType(User.Type.ADMIN);
        assertEquals(2, results.size());
        for (User u : results) {
            assertEquals(User.Type.ADMIN, u.getType());
        }
    }

    @Test
    void findUserByType_null_returnsNothing() {
        createUser(User.Type.ADMIN);
        createUser(User.Type.USER);
        createUser(User.Type.OWNER);

        List<User> results = userService.findByType(null);
        assertEquals(0, results.size());
    }

    @Test
    @Transactional
    void findUserByCanteenID_returnsOnlyDesiredUsers() {
        Canteen c1 = createCanteen();
        Canteen c2 = createCanteen();

        createUser(c1.getId());
        createUser(c1.getId());
        createUser(c2.getId());

        List<User> results = userService.findByCanteenID(c1.getId());
        assertEquals(2, results.size());
        for (User u : results) {
            assertEquals(c1.getId(), u.getHomeCanteen().getId());
        }
    }

    @Test
    @Transactional
    void findUserByCanteenID_null_returnsOnlyDesiredUsers() {
        Canteen c1 = createCanteen();

        createUser(c1.getId());
        createUser(User.Type.USER);
        createUser(User.Type.USER);

        List<User> results = userService.findByCanteenID(null);
        // keep standard OWNER in mind
        assertEquals(2+1, results.size());
        for (User u : results) {
            assertNull(u.getHomeCanteen());
        }
    }

    @Test
    @Transactional
    void findUserByCanteenIDAndType_returnsOnlyDesiredUsers() {
        Canteen c1 = createCanteen();
        Canteen c2 = createCanteen();

        createUser(User.Type.ADMIN, c1.getId());
        createUser(User.Type.USER, c1.getId());
        createUser(User.Type.ADMIN, c2.getId());

        List<User> results = userService.findByCanteenIDAndType(c1.getId(), User.Type.ADMIN);
        assertEquals(1, results.size());
        for (User u : results) {
            assertEquals(c1.getId(), u.getHomeCanteen().getId());
            assertEquals(User.Type.ADMIN, u.getType());
        }
    }

    @Test
    void testFindById_NoUser() {
        Optional<User> userOptional = userService.findById(-1);
        assertTrue(userOptional.isEmpty());
    }

    @Test
    void testFindById() {
        User user = createUser(User.Type.USER, null);
        Optional<User> userOptional = userService.findById(user.getId());
        assertFalse(userOptional.isEmpty());
        assertEquals(user.getId(), userOptional.get().getId());
    }

    @Test
    void testFindEntityById_throwException() {
        Exception e = assertThrows(ResponseStatusException.class, () -> userService.findEntityById(-1));
        assertEquals("user not found", e.getMessage());
    }

    @Test
    void testFindEntityById() {
        User user = createUser(User.Type.USER, null);
        User returnedUser = userService.findEntityById(user.getId());
        assertEquals(user.getId(), returnedUser.getId());
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
