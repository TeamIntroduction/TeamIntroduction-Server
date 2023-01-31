package com.example.project.controller.advicer;

import com.example.project.config.key.Aes;
import com.example.project.controller.annotation.Enc;
import com.example.project.controller.annotation.EncResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@RestControllerAdvice
public class AesResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final Aes aes;

    private final List<Class> CUSTOM_ANNOTATION_LIST = Collections.unmodifiableList(List.of(EncResponseBody.class));

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        Boolean hasMethodAnnotation = false;
        for (Class customAnnotation : CUSTOM_ANNOTATION_LIST) {
            if (returnType.hasMethodAnnotation(customAnnotation)) {
                hasMethodAnnotation = true;
            }
        }
        return hasMethodAnnotation;
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        Field body = object.getClass().getDeclaredFields()[3];
        body.setAccessible(true);

        Object bodyDto;
        Boolean isArrayList = false;
        try {
            bodyDto = body.get(object);
            if (bodyDto.getClass().getName().equals("java.util.ArrayList")) {
                isArrayList = true;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (isArrayList) {
            for (Object dto : (ArrayList<Object>) bodyDto) {
                encryptBody(dto);
            }
            return object;
        }
        encryptBody(body);
        return object;
    }

    private void encryptBody(Object dto) {
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);  // private로 선언되어 있더라도 접근 가능 하도록
            if (hasEncAnnotation(field)) {
                try {
                    setEncryptedValue(dto, field);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean hasEncAnnotation(Field field) {
        return null != field.getAnnotation(Enc.class);
    }

    private void setEncryptedValue(Object object, Field field) throws Exception {
        String value = (String) field.get(object);
        field.set(object, aes.encrypt(value));
    }
}