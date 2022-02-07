package edu.aau.groupc.canteenbackend.avatar.services;

import edu.aau.groupc.canteenbackend.avatar.Avatar;

import java.util.List;

public interface IAvatarService {

    List<Avatar> findAll();

    void updateOrAddAvatar(String username, String avatar);

    Boolean deleteAvatar(String username);

    Avatar getAvatar(String username);

}
