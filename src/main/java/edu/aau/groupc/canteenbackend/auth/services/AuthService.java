package edu.aau.groupc.canteenbackend.auth.services;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.repositories.IAuthRepository;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class AuthService implements IAuthService {

    private IAuthRepository authRepository;
    private IUserRepository userRepository;

    @Autowired
    AuthService(IAuthRepository authRepository, IUserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Auth login(String username, String password) {
        if (!this.userRepository.existsByUsernameAndPassword(username, password)) {
            return null;
        }

        return this.authRepository.save(new Auth(username));
    }

    @Override
    public Boolean logout(String username, String token) {
        return this.authRepository.deleteByUsernameAndToken(username, token) > 0;
    }

    @Override
    public List<Auth> findAll() {
        return this.authRepository.findAll();
    }

    @Override
    public Boolean isValidLogin(String username, String token) {
        return this.authRepository.getAuthByUsernameAndToken(username, token) != null;
    }

    @Override
    public User getUserByUsernameAndToken(String username, String token) {
        if (!isValidLogin(username, token)) {
            return null;
        }

        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public boolean isValidLogin(String token) {
        if (token == null) {
            return false;
        }
        Optional<Auth> auth = authRepository.getAuthByToken(token);
        if (auth.isEmpty()) {
            return false;
        }
        return auth.get().isNotExpired();
    }

    @Override
    public User getUserByToken(String token) {
        if (token == null) {
            return null;
        }
        Optional<Auth> auth = authRepository.getAuthByToken(token);
        if (auth.isEmpty()) {
            return null;
        }
        return userRepository.getUserByUsername(auth.get().getUsername());
    }
}
