package com.example.project.controller.resolver;

import com.example.project.config.key.Aes;
import com.example.project.controller.annotation.Dec;
import com.example.project.controller.annotation.DecPathVariable;
import com.example.project.controller.annotation.DecRequestBody;
import com.example.project.controller.annotation.DecRequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AesArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Aes aes;

    private final List<Class> CUSTOM_ANNOTATION_LIST = Collections.unmodifiableList(List.of(DecRequestParam.class, DecPathVariable.class, DecRequestBody.class));

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

        if (hasDecRequestParamAnnotation(parameter)) {
            return getDecryptedDataOfRequestParam(parameter, webRequest);
        }

        if (hasDecPathVariableAnnotation(parameter)) {
            return getDecryptedDataOfPathVariable(parameter, webRequest);
        }

        if (hasDecRequestBodyAnnotation(parameter)) {
            return getDecryptedDataOfRequestBody(parameter, webRequest);
        }

        return null;
    }

    private boolean hasDecRequestParamAnnotation(MethodParameter parameter) {
        return null != parameter.getParameterAnnotation(DecRequestParam.class);
    }

    private boolean hasDecPathVariableAnnotation(MethodParameter parameter) {
        return null != parameter.getParameterAnnotation(DecPathVariable.class);
    }

    private boolean hasDecRequestBodyAnnotation(MethodParameter parameter) {
        return null != parameter.getParameterAnnotation(DecRequestBody.class);
    }

    private boolean hasDecAnnotation(Field field) {
        return null != field.getAnnotation(Dec.class);
    }

    private String getDecryptedDataOfRequestParam(MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
        String parameterKey = parameter.getParameterName();
        String encryptedData = webRequest.getParameter(parameterKey);
        return aes.decrypt(encryptedData);
    }

    private String getDecryptedDataOfPathVariable(MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
        String pathKey = parameter.getParameterAnnotation(DecPathVariable.class).name();
        Map<?, ?> pathVariableMap = (Map<?, ?>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        String encryptedData = pathVariableMap.get(pathKey).toString();
        return aes.decrypt(encryptedData);
    }

    private Object getDecryptedDataOfRequestBody(MethodParameter parameter, NativeWebRequest webRequest) throws Exception {

        Class type = parameter.getParameterType();

        Object object = getObjectOfBody(webRequest, type);

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);  // private로 선언되어 있더라도 접근 가능 하도록
            if (hasDecAnnotation(field)) {
                setDecryptedValue(object, field);
            }
        }
        return object;
    }

    private void setDecryptedValue(Object object, Field field) throws Exception {
        String value = (String) field.get(object);
        field.set(object, aes.decrypt(value));
    }

    private Object getObjectOfBody (NativeWebRequest webRequest, Class type) throws IOException {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        ServletInputStream inputStream = httpServletRequest.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        Object object = objectMapper.readValue(messageBody, type);
        return object;
    }


}
