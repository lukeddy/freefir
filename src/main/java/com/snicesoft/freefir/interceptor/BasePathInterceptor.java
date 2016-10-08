package com.snicesoft.freefir.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhe on 2016/10/6.
 */

public class BasePathInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String basePath = scheme + "://" + serverName;
        if (port != 80)
            basePath += ":" + port + "/";
        else
            basePath += "/";
        request.setAttribute("basePath", basePath);
        request.setAttribute("requestURL", request.getRequestURL().toString());
        return true;
    }
}
