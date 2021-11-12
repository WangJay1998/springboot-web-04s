package com.ktjiaoyu.demo.utils;

import com.alibaba.fastjson.JSON;
import com.ktjiaoyu.demo.mapper.MyLogMapper;
import com.ktjiaoyu.demo.mapper.OperLog;
import com.ktjiaoyu.demo.pojo.SysLog;
import com.ktjiaoyu.demo.pojo.SysUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;


@Aspect
@Component
public class OperLogAspect {

    private static final Logger log=LogManager.getLogger(OperLogAspect.class);
     @Autowired
     private MyLogMapper myLogMapper;

     //定义切点
    @Pointcut("@annotation(com.ktjiaoyu.demo.mapper.OperLog)")
    public  void logPoinCut(){

    }

    //切面配置通知
    @Before("logPoinCut()")
    public void saveOperation(JoinPoint joinPoint) throws UnknownHostException {
        log.info("---------接口日志记录------");
        //用于保存日志
        SysLog sysLog=new SysLog();
        MethodSignature signature= (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method=signature.getMethod();

        OperLog operLog = method.getAnnotation(OperLog.class);
        if(operLog!=null){
            //保存操作事件
            String model = operLog.operModel();
            sysLog.setModel(model);
            String operDesc = operLog.operDescs();
            sysLog.setDescs(operDesc);
            String type = operLog.operType();
            sysLog.setType(type);
        }
        RequestAttributes requestAttributes= RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String ip = InetAddress.getLocalHost().getHostAddress();   //获取ip地址
        sysLog.setIp(ip);
        //操作人账号
        SysUser sysUser = (SysUser) request.getSession().getAttribute("sysUser");
        if(sysUser!=null){
            String usrName = sysUser.getUsrName();
            sysLog.setUserCode(usrName);
            sysLog.setCurTime(new Date());
        }
        myLogMapper.insert(sysLog);
    }


}
