package edu.aau.groupc.canteenbackend.avatar.services;

import edu.aau.groupc.canteenbackend.avatar.Avatar;
import edu.aau.groupc.canteenbackend.avatar.repositories.IAvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class AvatarService implements IAvatarService {

    private IAvatarRepository avatarRepository;

    @Autowired
    AvatarService(IAvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    @Override
    public List<Avatar> findAll() {
        return this.avatarRepository.findAll();
    }

    @Override
    public void updateOrAddAvatar(String username, String newAvatar) {
        Optional<Avatar> avatar = this.avatarRepository.getAvatarByUsername(username);

        if (avatar.isPresent()) {
            avatar.get().setAvatar(Base64.getDecoder().decode(newAvatar));
            avatarRepository.save(avatar.get());
        }
        else {
            Avatar newlyCreatedAvatar = new Avatar(username, newAvatar);
            avatarRepository.save(newlyCreatedAvatar);
        }
    }

    @Override
    public Boolean deleteAvatar(String username) {
        return avatarRepository.deleteAvatarByUsername(username) > 0;
    }

    @Override
    public Avatar getAvatar(String username) {
        Optional<Avatar> avatar = avatarRepository.getAvatarByUsername(username);

        if (avatar.isPresent()) {
            return avatar.get();
        } else {
            return null;
        }
    }
}
