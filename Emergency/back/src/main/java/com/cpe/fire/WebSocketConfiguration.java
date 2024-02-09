package com.cpe.fire;

import com.cpe.fire.domain.webSocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    @Value("${ws.endpoints}")
    private String simulationFireEndpoint;

    @Value("ws://127.0.0.1:${server.port}")
    private String serverAddress;

    public WebSocketConfiguration(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("WS is running at " + serverAddress + simulationFireEndpoint);
        registry.addHandler(webSocketHandler, simulationFireEndpoint).setAllowedOrigins("*");
    }
}