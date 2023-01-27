package com.example.project.config.resolver;

import com.example.project.config.annotation.Dec;
import com.example.project.config.annotation.DecRequestParam;
import com.example.project.utils.key.AES;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.project.config.filter.ExcludeAuthenticationUrl.*;
import static com.example.project.config.filter.ExcludeAuthenticationUrl.H2;

@Slf4j
@RequiredArgsConstructor
@Component
public class AesArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    private final List<Class> CUSTOM_ANNOTATION_LIST = Collections.unmodifiableList(List.of(DecRequestParam.class));
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        for (Class customAnnotation : CUSTOM_ANNOTATION_LIST) {
              return parameter.hasParameterAnnotation(customAnnotation);
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("====================resolver==================");
        String symmetricKey = (String)httpSession.getAttribute("SYMMETRIC_KEY");

        if (null != parameter.getParameterAnnotation(DecRequestParam.class)) {
            String encryptedTeamId = webRequest.getParameter("teamId");
            Long teamId = Long.parseLong(AES.decrypt(symmetricKey, encryptedTeamId));
            log.info("=====teamId : " + teamId);
            return teamId;
        }


        //log.info(String.valueOf(parameter));
        return null;
    }
}
