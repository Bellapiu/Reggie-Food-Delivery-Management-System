package com.example.lys01.filter;

import com.example.lys01.Result.R;
import com.example.lys01.common.BaseContext;
import com.example.lys01.entry.Employee;
import com.example.lys01.entry.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;



@WebFilter(filterName = "登录过滤器", urlPatterns = "/*")//拦截urlPatterns哪些路径  所有请求/*
//过滤类
public class EmployeeLoginCheck implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    //那些路径放行  那些路径不放行
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            //有些路径放行  有些路径拦截
        //获取请求对象和响应对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请求路径
        String uri = request.getRequestURI();
        System.out.println(uri);
        //1、放行路径放行
        String urls[] = {"/employee/login","/employee/logout","/backend/**","/front/**","/user/sendMsg","/user/login","/user/loginout"};
        boolean check = check(urls,uri);
        if(check){
            filterChain.doFilter(request,response);//放行
            return;
        }
        //2、员工用户登录了  也要放行    判断sessions 中保存了员工信息
        Object employee=request.getSession().getAttribute("employee");
        if(employee!=null){
            Long userId = ((Employee)request.getSession().getAttribute("employee")).getId();
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);//用户已经登录放行
            return;
        }

        //2、手机用户登录了  也要放行    判断sessions 中保存了员工信息
        Object user=request.getSession().getAttribute("user");
        if(user!=null){
            Long userId = ((User)request.getSession().getAttribute("user")).getId();
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);//用户已经登录放行
            return;
        }

        //3 .用户没有登录 拦截 跳转到登录页面
        //System.out.println(R.error("NOTLOGIN").toString());
      //  response.getWriter().write(R.error("NOTLOGIN").toString());
        //System.out.println(new ObjectMapper().writeValueAsString(R.error("NOTLOGIN")));
        response.getWriter().write(new ObjectMapper().writeValueAsString(R.error("NOTLOGIN")));


        System.out.println("过滤器开始工作,拦截！！！");
    }
    //匹配路径方法
    public boolean check(String[] urls,String uri){
        for (String url : urls) {
            if(PATH_MATCHER.match(url,uri)){
                return true;
            }
        }
        return false;
    }
}
