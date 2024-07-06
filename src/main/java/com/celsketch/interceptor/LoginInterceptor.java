package com.celsketch.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final List<String> EXCLUDE_URLS = Arrays.asList(
            "/", "/index.html", "/css/", "/js/", "/user/login", "/user/join"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (EXCLUDE_URLS.stream().anyMatch(uri::startsWith)) {
            return true;
        }

        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/user/login");
            return false;
        }

        return true;
    }
}
