package com.mohamed.halim.authservice.security;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mohamed.halim.authservice.model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private RabbitTemplate rabbit;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = rabbit.convertSendAndReceiveAsType(
                "profile", "profile.load.profile", username, new ParameterizedTypeReference<User>(){});
        return user;
    }

}
