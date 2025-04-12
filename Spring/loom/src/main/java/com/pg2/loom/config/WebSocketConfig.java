package com.pg2.loom.config;

import com.pg2.loom.websockets.WebSocketThreadHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketThreadHandler webSocketThreadHandler;

    public WebSocketConfig(WebSocketThreadHandler webSocketThreadHandler) {
        this.webSocketThreadHandler = webSocketThreadHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketThreadHandler, "/thread/{threadId}")
                .setAllowedOrigins("*"); // Allow cross-origin requests (CORS)
    }
}
