package com.mohamed.halim.mediaservice.config;

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
        return new TopicExchange("media");
    }

    @Bean
    Queue loadMediaQueue() {
        return new Queue("media.get");
    }

    @Bean
    Binding bindRegisterQueue(TopicExchange exchange) {
        return BindingBuilder.bind(loadMediaQueue()).to(exchange).with("media.get");
    }

    
}
