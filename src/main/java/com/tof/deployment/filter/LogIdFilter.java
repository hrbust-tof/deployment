package com.tof.deployment.filter;



import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @author leiwenxue
 * @version 1.0
 * @ClassName LogIdFilter
 * @desc LogIdFilter
 * @date 2019/8/6 下午4:50
 */
@Slf4j
public class LogIdFilter implements Filter {
    private static final String HEADER_LOGGER_ID_PARAM_NAME = "Logger-Id";
    private static final String BODY_LOGGER_ID_PARAM_NAME = "logger_id";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("LogIdFilter is init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //请求头中获取logId
        String loggerId = ((HttpServletRequest)request).getHeader(HEADER_LOGGER_ID_PARAM_NAME);
        //请求头中无logId，在请求参数中获取
        if(StringUtils.isBlank(loggerId)){
            loggerId = request.getParameter(BODY_LOGGER_ID_PARAM_NAME);
        }
        //获取不到logId,自己生成
        if(StringUtils.isBlank(loggerId)){
            loggerId = UUID.randomUUID().toString().replace("-","").toLowerCase();
        }
        MDC.put(HEADER_LOGGER_ID_PARAM_NAME,"【"+loggerId+"】");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        MDC.remove(HEADER_LOGGER_ID_PARAM_NAME);
    }
}
