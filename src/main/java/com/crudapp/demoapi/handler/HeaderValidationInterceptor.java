package com.crudapp.demoapi.handler;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class HeaderValidationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object handler) throws IOException {
        String customHeader=httpServletRequest.getHeader("X-CMC_PRO_API_KEY");
        if(StringUtils.isEmpty(customHeader)){
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Required header not found");
            return false;
        }
        return true;
    }
}
