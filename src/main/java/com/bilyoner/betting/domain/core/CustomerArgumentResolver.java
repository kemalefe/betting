package com.bilyoner.betting.domain.core;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Customer.class) && CustomerDto.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public CustomerDto resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception {

        String customerId = webRequest.getHeader("x-customer-id");
        if (customerId == null || customerId.isEmpty()) {
            throw new MissingRequestHeaderException("x-customer-id", parameter);
        }

        // mock - bearer authentication  could be implemented
        return new CustomerDto(Long.parseLong(customerId), "Kemal", "Efe");
    }

}