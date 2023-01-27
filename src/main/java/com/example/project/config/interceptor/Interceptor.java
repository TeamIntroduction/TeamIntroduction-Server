package com.example.project.config.interceptor;

import com.example.project.utils.key.AES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isPreflightRequest(request)) return true;

//        if (request.getRequestURL().toString().equals("http://localhost:8080/api/members")) {
//            String encryptedTeamId = request.getParameter("teamId");
//
//            HttpSession session = request.getSession();
//            String symmetricKey = (String)session.getAttribute("SYMMETRIC_KEY");
//
//            log.info("==============encryptedTeamId============"+encryptedTeamId);
//            log.info("==============TeamId============"+ AES.decrypt(symmetricKey, encryptedTeamId));
//
//            Long teamId = Long.parseLong(AES.decrypt(symmetricKey, encryptedTeamId));
//            log.info("===== teamId ====="+teamId);
//        }
        return true;
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return request.getMethod().equals("OPTIONS");
    }
}
