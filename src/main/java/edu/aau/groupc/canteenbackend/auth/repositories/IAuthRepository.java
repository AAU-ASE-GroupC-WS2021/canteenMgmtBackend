package edu.aau.groupc.canteenbackend.auth.repositories;

import edu.aau.groupc.canteenbackend.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthRepository extends JpaRepository<Auth, Long> {

    Long deleteByToken(String token);

    Optional<Auth> getAuthByToken(String token);

}
