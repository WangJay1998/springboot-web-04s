package com.ktjiaoyu.demo.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AnthorInt implements HandlerInterceptor {
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        if(request.getSession().getAttribute("sysUser")==null){
////            response.setContentType("text/html;charset=utf-8");
////            PrintWriter out=response.getWriter();
////            out.print("<script type='text/javascript'>alert('请先登录');location.href='"+request.getContextPath()+"/'</script>");
////            return false;
////        }else {
////            return true;
////        }
//    }
}
