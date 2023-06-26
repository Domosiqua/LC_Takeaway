package com.filter;

import com.alibaba.fastjson.JSON;
import com.common.BaseContext;
import com.common.Result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CWB
 * @version 1.0
 * 检查用户是否已经完成登陆
 */
@SuppressWarnings({"all"})
@Slf4j
@Component
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public  static  final AntPathMatcher PATH_MATCHER= new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        //设置放行请求路径
        String []urls=new String[]{
          "/employee/login",
          "/employee/logout",
          "/backend/**",
          "/front/**",
          "/user/login",
          "/user/sendMsg",
          "/doc.html",
          "/webjars/**",
          "/swagger-resources",
          "/v2/spi-docs"
        };
        log.info("拦截到请求:{}",request.getRequestURI());

        boolean check = check(urls, requestURI);
        if (check){
            filterChain.doFilter(request,response);
            log.info("放行");

            return;
        }
//        session中有employee 说明已经登陆 放行
        if (request.getSession().getAttribute("employee")!=null) {
            long id=(long)request.getSession().getAttribute("employee");
            log.info("用户已登陆 ：{}",id);
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request,response);
            return;
        }
        //同理 判断用户是否登陆
        if (request.getSession().getAttribute("user")!=null) {
            Long id=(long)request.getSession().getAttribute("user");
            log.info("用户已登陆 ：{}",id);
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登陆");
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
            return;
    }

    /**
     * 路径匹配 本次请求是否放行
     * @param requestURI
     * @return
     */
    public boolean check(String []urls,String requestURI){
        for (String url: urls){
           if (PATH_MATCHER.match(url,requestURI))
               return true;
        }
        return false;

    }
}
