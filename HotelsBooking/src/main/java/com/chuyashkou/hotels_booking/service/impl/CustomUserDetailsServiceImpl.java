package com.chuyashkou.hotels_booking.service.impl;

import com.chuyashkou.hotels_booking.model.CustomUserDetails;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<GrantedAuthority> grantedAuthoritySet = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(Objects.toString(role.ordinal())))
                    .collect(Collectors.toSet());
            return new CustomUserDetails(user.getEmail(), user.getPassword(),
                    user.isActive(), true, true, true,
                    grantedAuthoritySet, user.getFirstName(), user.getLastName(), user.getGender(), user.getId());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
