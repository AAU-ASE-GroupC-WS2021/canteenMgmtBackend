package edu.aau.groupc.canteenbackend.user.services;

import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.exceptions.CanteenNotFoundException;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserDto;
import edu.aau.groupc.canteenbackend.user.dto.UserUpdateDTO;
import edu.aau.groupc.canteenbackend.user.exceptions.UserNotFoundException;
import edu.aau.groupc.canteenbackend.user.exceptions.UsernameConflictException;
import edu.aau.groupc.canteenbackend.user.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ICanteenService canteenService;

    @Autowired
    UserService(IUserRepository userRepository, ICanteenService canteenService) {
        this.userRepository = userRepository;
        this.canteenService = canteenService;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findEntityById(long id) throws ResponseStatusException {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(long id, UserUpdateDTO updateInfo) throws UsernameConflictException, UserNotFoundException, CanteenNotFoundException {
        Optional<User> foundUser = findById(id);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        User existingUser = foundUser.get();
        if (updateInfo.getUsername() != null) {
            User userWithUsername = userRepository.getUserByUsername(updateInfo.getUsername());
            if (userWithUsername != null && userWithUsername.getId() != existingUser.getId()) {
                throw new UsernameConflictException();
            }
            existingUser.setUsername(updateInfo.getUsername());
        }
        if (updateInfo.getPassword() != null) existingUser.setPassword(updateInfo.getPassword());
        if (updateInfo.getType() != null) existingUser.setType(updateInfo.getType());
        if (updateInfo.getCanteenID() != null) {
            Optional<Canteen> canteen = canteenService.findById(updateInfo.getCanteenID());
            if (canteen.isEmpty()) {
                throw new CanteenNotFoundException();
            }
            existingUser.setHomeCanteen(canteen.get());
        } else {
            existingUser.setHomeCanteen(null);
        }

        existingUser = userRepository.save(existingUser);
        return existingUser;
    }

    @Override
    public boolean ownerExists() {
        return !userRepository.findUsersByTypeOrderById(User.Type.OWNER).isEmpty();
    }

    @Override
    public User create(User user) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername((user.getUsername())))) {
            return null;
        }

        return userRepository.save(user);
    }

    @Override
    public User create(UserDto user, User.Type type) throws UsernameConflictException, CanteenNotFoundException {
        if (userRepository.existsByUsername((user.getUsername()))) {
            throw new UsernameConflictException();
        }
        User newUser = user.toEntity();
        newUser.setType(type);
        // home canteen is optional
        if (user.getCanteenID() != null) {
            Optional<Canteen> homeCanteen = canteenService.findById(user.getCanteenID());
            if (homeCanteen.isEmpty()) {
                throw new CanteenNotFoundException();
            }
            newUser.setHomeCanteen(homeCanteen.get());
        }
        return userRepository.save(newUser);
    }

    @Override
    public List<User> findByCanteenID(Integer canteenID) {
        return userRepository.findUsersByHomeCanteen_IdOrderById(canteenID);
    }

    @Override
    public List<User> findByType(User.Type type) {
        return userRepository.findUsersByTypeOrderById(type);
    }

    @Override
    public List<User> findByCanteenIDAndType(Integer canteenID, User.Type type) {
        return userRepository.findUsersByHomeCanteen_IdAndTypeOrderById(canteenID, type);
    }

    @Override
    public boolean updatePassword(String username, String newPassword) {
        User user = userRepository.getUserByUsername(username);

        if (user == null || newPassword == null) {
            return false;
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        return user.getPassword().equals(newPassword);
    }
}
