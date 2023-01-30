package com.example.project.controller.resolver;

import com.example.project.config.key.Aes;
import com.example.project.controller.annotation.DecRequestParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AesArgumentResolver implements HandlerMethodArgumentResolver {

    private final Aes aes;
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
//        String symmetricKey = (String)httpSession.getAttribute("SYMMETRIC_KEY");

        if (isDecRequestParamAnnotation(parameter)) {
            return getDecryptedData(parameter, webRequest);
        }


        //log.info(String.valueOf(parameter));
        return null;
    }

    private boolean isDecRequestParamAnnotation(MethodParameter parameter) {
        return null != parameter.getParameterAnnotation(DecRequestParam.class);
    }

    private String getDecryptedData(MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
        String parameterKey = parameter.getParameterName();
        String encryptedData = webRequest.getParameter(parameterKey);

        log.info("=====encryptedData======== : " + encryptedData);
        return aes.decrypt(encryptedData);
    }
}
