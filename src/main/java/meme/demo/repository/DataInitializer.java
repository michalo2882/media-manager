package meme.demo.repository;

import meme.demo.model.MediaFile;
import meme.demo.model.Role;
import meme.demo.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
@Profile("sandbox")
public class DataInitializer {

    static final Logger log = LogManager.getLogger(DataInitializer.class.getName());

    @Autowired
    MediaFileRepository mediaFileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void loadData() {
        log.info("Initializing repositories");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        roleRepository.saveAll(Arrays.asList(adminRole, userRole));

        User admin = new User();
        admin.setName("admin");
        admin.setPassword("$2a$10$hnv29n7cYMoQrd5RFyl9iust3Mmq6jMkyx8WPcnkR4rQp1BX338GS");
        admin.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));

        User user = new User();
        user.setName("user");
        user.setPassword("$2a$10$3IgUC//AzyMvEbe3GJ4xGuiftuY544DAGtRpF3IPs.mpFeJgjeDJC");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        userRepository.saveAll(Arrays.asList(admin, user));

        mediaFileRepository.saveAll(Arrays.asList(
                new MediaFile("fakeUuid1", "entry1", "entry1.mp4", "entry1.mp4", 101, user),
                new MediaFile("fakeUuid2", "entry2", "entry2.mp4", "entry2.mp4", 102, user),
                new MediaFile("fakeUuid3", "entry3", "entry3.mp4", "entry3.mp4", 103, user),
                new MediaFile("fakeUuid4", "entry4", "entry4.mp4", "entry4.mp4", 104, user),
                new MediaFile("fakeUuid5", "entry5", "entry5.mp4", "entry5.mp4", 105, user)
        ));
    }
}
