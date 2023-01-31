package com.example.project.controller.resolver;

import com.example.project.config.key.Aes;
import com.example.project.controller.annotation.DecPathVariable;
import com.example.project.controller.annotation.DecRequestParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AesArgumentResolver implements HandlerMethodArgumentResolver {

    private final Aes aes;
    private final List<Class> CUSTOM_ANNOTATION_LIST = Collections.unmodifiableList(List.of(DecRequestParam.class, DecPathVariable.class));
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Boolean hasParameterAnnotation = false;
        for (Class customAnnotation : CUSTOM_ANNOTATION_LIST) {
            if (parameter.hasParameterAnnotation(customAnnotation)) {
                hasParameterAnnotation = true;
            }
        }
        return hasParameterAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("====================resolver==================");

        if (isDecRequestParamAnnotation(parameter)) {
            return getDecryptedDataOfRequestParam(parameter, webRequest);
        }

        if (isDecPathVariableAnnotation(parameter)) {
            return getDecryptedDataOfPathVariable(parameter, webRequest);
        }

        return null;
    }

    private boolean isDecRequestParamAnnotation(MethodParameter parameter) {
        return null != parameter.getParameterAnnotation(DecRequestParam.class);
    }

    private boolean isDecPathVariableAnnotation(MethodParameter parameter) {
        return null != parameter.getParameterAnnotation(DecPathVariable.class);
    }

    private String getDecryptedDataOfRequestParam(MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
        String parameterKey = parameter.getParameterName();
        String encryptedData = webRequest.getParameter(parameterKey);

        log.info("=====encryptedData======== : " + encryptedData);
        return aes.decrypt(encryptedData);
    }

    private String getDecryptedDataOfPathVariable(MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
        String parameterKey = parameter.getParameterAnnotation(DecPathVariable.class).name();
        Map<?, ?> pathVariableMap = (Map<?, ?>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        String encryptedData = pathVariableMap.get(parameterKey).toString();
        return aes.decrypt(encryptedData);
    }


}
