package com.ktjiaoyu.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ktjiaoyu.demo.pojo.SysLog;
import com.ktjiaoyu.demo.service.SysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SysLogController {

    @Resource
    private SysLogService sysLogService;

    @RequestMapping("/log/list")
    public String list(Model model, HttpServletRequest request, String userCode, @RequestParam(required = false, defaultValue = "1", value = "pageIndex")Integer pageIndex){
        System.out.println("进来了~~~~~~~");
        QueryWrapper<SysLog> wrapper=new QueryWrapper<SysLog>();
        if(!StringUtils.isEmpty(userCode)) {
            wrapper.eq("user_code",userCode);
        }
        wrapper.orderByDesc("id");
        Page<SysLog> page=new Page<SysLog>(pageIndex,5);
        IPage<SysLog> sysLogList = sysLogService.list(page,wrapper);
        request.setAttribute("jumpUrl", "/log/list?userCode="+userCode+"&pageIndex=");  //构造访问路径
        model.addAttribute("logs",sysLogList);
        model.addAttribute("userCode",userCode);
        return "log/list";
    }
}
