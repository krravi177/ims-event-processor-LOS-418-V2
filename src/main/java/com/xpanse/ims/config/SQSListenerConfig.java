package com.xpanse.ims.config;

//import com.amazonaws.services.sqs.AmazonSQSAsync;
//import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
//import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
//import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
//import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
//import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
//import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@EnableSqs
public class SQSListenerConfig {

//
//
//    @Bean
//    public AmazonSQSAsync amazonSQSAsync() {
//        return AmazonSQSAsyncClientBuilder.defaultClient();
//    }



}


