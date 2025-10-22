package com.suyash.Base64UseCases.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws")  // WebSocket handshake
                .setAllowedOriginPatterns("*") // Allow all origins for now
                .withSockJS();  // Enable SockJs Fallback for older browsers
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); //server to client messaging
        registry.setApplicationDestinationPrefixes("/app"); //this is for Client to Server messaging
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(10*1024*1024); //10mbs
        registration.setSendBufferSizeLimit(10*1024*1024);
        registration.setSendTimeLimit(20000);
    }
}
