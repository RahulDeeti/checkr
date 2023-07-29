package com.org.checkr.config;

import com.org.checkr.entity.User;
import com.org.checkr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component

public class UserAuthDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user =  userRepository.findByUserName(userName);

        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found "+userName));
    }
}
