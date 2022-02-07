package edu.aau.groupc.canteenbackend.avatar.repositories;

import edu.aau.groupc.canteenbackend.avatar.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAvatarRepository extends JpaRepository<Avatar, Long> {

    Long deleteAvatarByUsername(String username);

    Optional<Avatar> getAvatarByUsername(String username);

}
