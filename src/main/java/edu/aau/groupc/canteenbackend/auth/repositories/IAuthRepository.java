package edu.aau.groupc.canteenbackend.auth.repositories;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthRepository extends JpaRepository<Auth, Long> {

    Long deleteByUsernameAndToken(String username, String token);

    Optional<Auth> getAuthByUsernameAndToken(String username, String token);

}
