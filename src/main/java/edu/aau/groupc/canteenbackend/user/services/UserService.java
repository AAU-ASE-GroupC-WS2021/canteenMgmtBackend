package edu.aau.groupc.canteenbackend.user.services;

import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserService implements IUserService {

    private IUserRepository userRepository;

    @Autowired
    UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

}
