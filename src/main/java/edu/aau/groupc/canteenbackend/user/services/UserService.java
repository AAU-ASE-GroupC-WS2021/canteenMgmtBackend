package edu.aau.groupc.canteenbackend.user.services;

import edu.aau.groupc.canteenbackend.user.User;
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

    @Autowired
    UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

    @Override
    public boolean ownerExists() {
        return !userRepository.findUsersByType(User.Type.OWNER).isEmpty();
    }

    @Override
    public User create(User user) {
        if (userRepository.existsByUsername((user.getUsername()))) {
            return null;
        }

        return userRepository.save(user);
    }

}
