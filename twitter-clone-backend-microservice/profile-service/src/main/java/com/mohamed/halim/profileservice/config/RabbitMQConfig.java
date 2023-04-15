package com.mohamed.halim.profileservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("profile");
    }

    @Bean
    Queue registerQueue() {
        return new Queue("profile.user.register");
    }

    @Bean
    Binding bindRegisterQueue(TopicExchange exchange) {
        return BindingBuilder.bind(registerQueue()).to(exchange).with("profile.user.register");
    }

    @Bean
    Queue loadProfileQueue() {
        return new Queue("profile.load.profile");
    }

    @Bean
    Binding bindLoadProfileQueue(TopicExchange exchange) {
        return BindingBuilder.bind(loadProfileQueue()).to(exchange).with("profile.load.profile");
    }

    @Bean
    Queue loginQueue() {
        return new Queue("profile.user.login");
    }

    @Bean
    Binding bindLoginQueue(TopicExchange exchange) {
        return BindingBuilder.bind(loginQueue()).to(exchange).with("profile.user.login");
    }
}
