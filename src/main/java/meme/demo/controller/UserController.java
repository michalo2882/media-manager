package meme.demo.controller;

import meme.demo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/id")
    public User getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        user.setName(authentication.getName());
        return user;
    }
}
