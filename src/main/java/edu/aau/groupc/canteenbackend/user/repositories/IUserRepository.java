package edu.aau.groupc.canteenbackend.user.repositories;

import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    User getUserByUsername(String username);

    List<User> findUsersByTypeOrderById(User.Type type);

    List<User> findUsersByHomeCanteen_IdOrderById(Integer canteenID);

    List<User> findUsersByHomeCanteen_IdAndTypeOrderById(Integer canteenID, User.Type type);

}
