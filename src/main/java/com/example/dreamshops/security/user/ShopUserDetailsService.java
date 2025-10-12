package com.example.dreamshops.security.user;

import com.example.dreamshops.exceptions.UserNotFoundException;
import com.example.dreamshops.model.User;
import com.example.dreamshops.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(username))
                .orElseThrow(()-> new UserNotFoundException("User with email: " + username + " not found"));
        return ShopUserDetails.buildUserDetails(user);
    }
}
