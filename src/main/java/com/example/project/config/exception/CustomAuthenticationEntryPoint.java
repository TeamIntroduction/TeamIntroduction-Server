package com.example.project.config.exception;

import com.example.project.constant.ErrorResponse;
import com.example.project.dto.ResponseDto;
import com.example.project.exception.err40x.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.project.constant.ErrorResponse.EXPIRED_ACCESS_TOKEN;
import static com.example.project.constant.ErrorResponse.TOKEN_ERROR;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String EXCEPTION = "exception";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String TARGET_URL = "http://localhost:3000";

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        ErrorResponse exception = (ErrorResponse) request.getAttribute(EXCEPTION);
        if (null != exception) {
            setResponse(response, exception);
            return;
        }
        setResponse(response, TOKEN_ERROR);
    }

    private void setResponse(HttpServletResponse response, ErrorResponse error) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("access-control-allow-origin", TARGET_URL);

        String body = objectMapper.writeValueAsString(ResponseDto.error(error.getMessage(), error.getCode()));
        response.getWriter().write(body);
        response.getWriter().flush();
    }
}
