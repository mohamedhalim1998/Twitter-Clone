package com.mohamed.halim.pollservice.config;

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
        return new TopicExchange("poll");
    }

    @Bean
    Queue addPollQueue() {
        return new Queue("poll.add");
    }

    @Bean
    Binding bindAddPollQueue(TopicExchange exchange) {
        return BindingBuilder.bind(addPollQueue()).to(exchange).with("poll.add");
    }

    @Bean
    Queue getPollQueue() {
        return new Queue("poll.get");
    }

    @Bean
    Binding bindGetPollQueue(TopicExchange exchange) {
        return BindingBuilder.bind(getPollQueue()).to(exchange).with("poll.get");
    }

}
