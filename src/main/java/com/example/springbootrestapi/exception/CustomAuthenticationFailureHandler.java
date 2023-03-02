package com.example.springbootrestapi.exception;

import com.example.springbootrestapi.payload.ErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        response.setStatus(httpStatus.value());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getOutputStream().println(mapper.writeValueAsString(new ErrorDetails(new Date(),exception.getMessage(), request.getRequestURL().toString())));
    }
}
