package com.mohamed.halim.authservice.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper.TypePrecedence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setTypePrecedence(TypePrecedence.INFERRED);
        return converter;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("auth");
    }

    @Bean
    Queue passwordValidationQueue() {
        return new Queue("auth.password.validation");
    }

    @Bean
    Binding bindLoadProfileQueue(TopicExchange exchange) {
        return BindingBuilder.bind(passwordValidationQueue()).to(exchange).with("auth.password.validation");
    }
}
