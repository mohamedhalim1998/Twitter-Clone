package com.mohamed.halim.tweetservice.config;

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
        return new TopicExchange("tweet");
    }

    @Bean
    Queue countUserTweetsQueue() {
        return new Queue("tweet.user.tweets.count");
    }

    @Bean
    Binding bindRegisterQueue(TopicExchange exchange) {
        return BindingBuilder.bind(countUserTweetsQueue()).to(exchange).with("tweet.user.tweets.count");
    }

    
}
