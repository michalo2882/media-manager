package meme.demo.component;

import meme.demo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationDetailsProvider {
    Authentication getAuthentication();
    UserDetails getUserDetails();
    User getCurrentUser();
}
