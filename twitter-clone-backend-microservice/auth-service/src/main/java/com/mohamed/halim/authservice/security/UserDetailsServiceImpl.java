package com.mohamed.halim.authservice.security;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mohamed.halim.authservice.model.User;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    private RabbitTemplate rabbit;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        User user = rabbit.convertSendAndReceiveAsType(
                "profile", "profile.load.profile", username, new ParameterizedTypeReference<User>(){});
        return Mono.just(user).cast(UserDetails.class);
    }


}
