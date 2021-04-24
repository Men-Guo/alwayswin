
package com.example.alwayswin.security;

import com.example.alwayswin.utils.commonAPI.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;


/**
 * @ClassName: ErrorAuthenticationEntryPoint
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-20
 */

@Component
public class ErrorAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 设置 Json 格式返回
        response.setContentType("application/json;charset=UTF-8");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        PrintWriter writer = response.getWriter();
        // 将对象输出为 JSON 格式。可以通过重写 MyResponse 的 toString() ，直接通过 myResponse.toString() 即可
        writer.write(CommonResult.unauthorized().toString());
        logger.debug(CommonResult.unauthorized().toString());
    }
}

