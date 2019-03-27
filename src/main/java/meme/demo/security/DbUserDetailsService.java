package meme.demo.security;

import meme.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        meme.demo.model.User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (meme.demo.model.Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(user.getName(), user.getPassword(),
                true, true, true, true, authorities);
    }
}
