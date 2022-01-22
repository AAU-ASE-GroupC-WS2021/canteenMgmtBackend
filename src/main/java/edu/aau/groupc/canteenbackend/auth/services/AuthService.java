package edu.aau.groupc.canteenbackend.auth.services;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.repositories.IAuthRepository;
import edu.aau.groupc.canteenbackend.user.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
