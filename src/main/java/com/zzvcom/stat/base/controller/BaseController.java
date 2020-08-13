package com.zzvcom.stat.base.controller;

import com.zzvcom.oauth2.util.JwtUserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author boz
 * @date 2020/5/18
 */

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 获取JwtUserInfo对象
     * @return JwtUserInfo
     */
    protected JwtUserInfo getJwtUserInfo() {
        return (JwtUserInfo)request.getAttribute("jwtUserinfo");
    }
}
