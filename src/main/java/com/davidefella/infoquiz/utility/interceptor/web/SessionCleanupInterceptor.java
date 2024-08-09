package com.davidefella.infoquiz.utility.interceptor.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionCleanupInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();

        if ("/thank-you".equals(requestURI)) {
            // Invalida la sessione
            request.getSession().invalidate();

            // Rimuove il cookie di sessione
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
