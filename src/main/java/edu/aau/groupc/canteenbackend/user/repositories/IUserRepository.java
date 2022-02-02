package edu.aau.groupc.canteenbackend.user.repositories;

import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByUsernameAndPassword(String username, String password);

    User getUserByUsername(String username);

    List<User> findUsersByType(User.Type type);

    List<User> findUsersByHomeCanteen_Id(Integer canteenID);

    List<User> findUsersByHomeCanteen_IdAndType(Integer canteenID, User.Type type);

}
