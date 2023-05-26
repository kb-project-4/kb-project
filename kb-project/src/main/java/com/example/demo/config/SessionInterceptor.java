package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false); // 세션을 가져옴 (세션이 없으면 null 반환)

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/users/index"); // 세션이 없으면 인덱스 페이지로 리다이렉트
            return false; // 요청 처리 중지
        }

        return true; // 요청 처리 계속 진행
    }
    
}