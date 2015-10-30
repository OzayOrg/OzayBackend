package com.ozay.backend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);
    @Inject
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor");
        return true;
    }


}
