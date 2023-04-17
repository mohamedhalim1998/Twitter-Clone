package com.mohamed.halim.jwtservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper.TypePrecedence;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var converter = new Jackson2JsonMessageConverter(mapper);
        converter.setTypePrecedence(TypePrecedence.INFERRED);
        return converter;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("jwt");
    }

    @Bean
    Queue tokenGeneratQueue() {
        return new Queue("jwt.token.generate");
    }

    @Bean
    Queue tokenValidatoinQueue() {
        return new Queue("jwt.token.validation");
    }

    @Bean
    Queue extractUsername() {
        return new Queue("jwt.token.extract.username");
    }

    @Bean
    Queue verifyTokenQueue() {
        return new Queue("jwt.token.verify");
    }

    @Bean
    Binding bindTokenGeneration(TopicExchange exchange) {
        return BindingBuilder
                .bind(tokenGeneratQueue()).to(exchange).with("jwt.token.generate");
    }

    @Bean
    Binding bindTokenValidation(TopicExchange exchange) {
        return BindingBuilder
                .bind(tokenValidatoinQueue()).to(exchange).with("jwt.token.validation");
    }

    @Bean
    Binding bindExtractUsername(TopicExchange exchange) {
        return BindingBuilder
                .bind(extractUsername()).to(exchange).with("jwt.token.extract.username");
    }

    @Bean
    Binding bindVerifyTokenQueue(TopicExchange exchange) {
        return BindingBuilder
                .bind(verifyTokenQueue()).to(exchange).with("jwt.token.verify");
    }
}
