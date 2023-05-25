package com.example.demo.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class MyHandshakeInterceptor implements HandshakeInterceptor { // 3 handshake를 interceptor하여 http session 획득

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		
		HttpSessionHandshakeInterceptor httpSessionInterceptor = new HttpSessionHandshakeInterceptor();
		
		if (httpSessionInterceptor.beforeHandshake(request, response, wsHandler, attributes)) {
			HttpSession httpSession = (HttpSession) attributes.get("javax.servlet.http.HttpSession");
			attributes.put("HTTP_SESSION", httpSession);
			return true;
		}
		
		else {
			return false;
		}

		
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		HttpSessionHandshakeInterceptor httpSessionInterceptor = new HttpSessionHandshakeInterceptor();
		httpSessionInterceptor.afterHandshake(request, response, wsHandler, exception);
	}

}
