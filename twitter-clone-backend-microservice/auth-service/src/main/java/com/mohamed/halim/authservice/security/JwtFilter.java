package com.mohamed.halim.authservice.security;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.google.common.net.HttpHeaders;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Configuration
public class JwtFilter implements WebFilter {
    private final ReactiveUserDetailsService userDetailsService;
    private final RabbitTemplate rabbit;
    public static final String HEADER_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange);
        if (token == null)
            return chain.filter(exchange);
        Boolean isTokenValid = rabbit.convertSendAndReceiveAsType(
                "jwt", "jwt.token.validation", token, new ParameterizedTypeReference<Boolean>() {
                });
        if (StringUtils.hasText(token) && isTokenValid) {
            String username = rabbit.convertSendAndReceiveAsType(
                    "jwt", "jwt.token.extract.username", token, new ParameterizedTypeReference<String>() {
                    });
            Mono<Authentication> authentication = userDetailsService.findByUsername(username)
                    .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                            userDetails.getPassword(), List.of()));
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder
                            .withSecurityContext(authentication.map(SecurityContextImpl::new)))
                    .switchIfEmpty(chain.filter(exchange));
        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token != null ? token.substring(HEADER_PREFIX.length()) : null;
    }
}
