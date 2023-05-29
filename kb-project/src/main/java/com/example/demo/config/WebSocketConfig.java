package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.example.demo.handler.MyWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	

	private final MyWebSocketHandler myWebSocketHandler;
	private final IPHandshakeInterceptor ipHandshakeInterceptor;

	public WebSocketConfig(MyWebSocketHandler myWebSocketHandler, IPHandshakeInterceptor ipHandshakeInterceptor) {
		this.myWebSocketHandler = myWebSocketHandler;
		this.ipHandshakeInterceptor = ipHandshakeInterceptor;
	}

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myWebSocketHandler, "/socket")
		.addInterceptors(ipHandshakeInterceptor)
		.addInterceptors(new HttpSessionHandshakeInterceptor())
		.setHandshakeHandler(new DefaultHandshakeHandler());
		
	}

	
	
}
