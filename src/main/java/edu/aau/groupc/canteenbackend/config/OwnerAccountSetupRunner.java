package edu.aau.groupc.canteenbackend.config;

import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OwnerAccountSetupRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(OwnerAccountSetupRunner.class);
    private IUserService userService;

    @Value("${app.mgmt.default-owner-username}")
    protected String ownerUsername;

    @Value("${app.mgmt.default-owner-password}")
    protected String ownerPassword;

    @Autowired
    public OwnerAccountSetupRunner(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (!userService.ownerExists()) {
            userService.create(new User(ownerUsername, ownerPassword, User.Type.OWNER));
            logger.debug("No Owner account found. Creating default.");
        }
    }
}
