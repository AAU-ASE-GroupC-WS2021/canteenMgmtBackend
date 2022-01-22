package edu.aau.groupc.canteenbackend.auth.repositories;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByUsername(String username);

    Optional<Auth> findByUsernameAndToken(String username, String token);

    Boolean existsByUsername(String username);

    Boolean deleteByUsername(String username);

    Long deleteByUsernameAndToken(String username, String token);

}
