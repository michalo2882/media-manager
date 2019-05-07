package mediamanager.component;

import mediamanager.model.User;
import mediamanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDetailsProviderImpl implements AuthenticationDetailsProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserDetails getUserDetails() {
        Authentication authentication = getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) authentication.getPrincipal();
        }
        else {
            return null;
        }
    }

    @Override
    public User getCurrentUser() {
        UserDetails userDetails = getUserDetails();
        if (userDetails != null) {
            return userRepository.findByName(userDetails.getUsername());
        }
        else {
            return null;
        }
    }
}
