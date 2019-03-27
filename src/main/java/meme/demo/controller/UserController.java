package meme.demo.controller;

import meme.demo.model.User;
import meme.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/id")
    @Secured("ROLE_USER")
    public User getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByName(authentication.getName());
    }
}
