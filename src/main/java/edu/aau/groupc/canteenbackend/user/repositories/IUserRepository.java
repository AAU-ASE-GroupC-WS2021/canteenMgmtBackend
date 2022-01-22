package edu.aau.groupc.canteenbackend.user.repositories;

import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByUsernameAndPassword(String username, String password);

    User getUserByUsername(String username);

}
