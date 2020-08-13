package com.zzvcom.stat.base.filter;

import com.zzvcom.oauth2.util.JwtUserInfo;
import com.zzvcom.oauth2.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @公司 vcom
 * @功能描述 解析Jwt过滤器
 * @作者 lixinyun
 * @时间 2019/6/12 15:11
 */
@Component
@Slf4j
@Order(1)
public class ParseJwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String requestURI = req.getRequestURI();
        if (log.isDebugEnabled()) {
            log.debug("解析Jwt过滤器：请求地址" + requestURI);
        }
        JwtUserInfo jwtUserinfo = JwtUtil.getJwtUserInfo(req);
        req.setAttribute("jwtUserinfo", jwtUserinfo);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}