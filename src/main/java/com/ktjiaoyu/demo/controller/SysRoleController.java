package com.ktjiaoyu.demo.controller;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ktjiaoyu.demo.mapper.OperLog;
import com.ktjiaoyu.demo.pojo.SysRight;
import com.ktjiaoyu.demo.pojo.SysRole;
import com.ktjiaoyu.demo.pojo.SysRoleRight;
import com.ktjiaoyu.demo.service.SysRightService;
import com.ktjiaoyu.demo.service.SysRoleRightService;
import com.ktjiaoyu.demo.service.SysRoleService;
import com.ktjiaoyu.demo.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 王伟杰
 * @since 2021-09-23
 */
@Controller
@SuppressWarnings("all")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRightService sysRightService;

    @Resource
    private SysRoleRightService sysRoleRightService;

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "111";
    }

    @OperLog(operDescs = "描述",operType = "角色列表",operModel = "角色管理-列表")
    @RequestMapping("/role/list")
    public String roleList(String roleName, HttpSession session,@RequestParam(required = false, defaultValue = "1", value = "pageIndex")Integer pageIndex,Model model, HttpServletRequest request){
        System.out.println("进来了~~~~~~~");
        List<SysRight> list = sysRightService.list();
        session.setAttribute("rightSet",list);
        System.out.println("所有权限11111111111111111111"+list);
        QueryWrapper<SysRole> wrapper=new QueryWrapper<SysRole>();
        if(!StringUtils.isEmpty(roleName)) {
            wrapper.like("role_name",roleName);
        }
        wrapper.orderByDesc("role_id");
        Page<SysRole> page=new Page<SysRole>(pageIndex,5);
        IPage<SysRole> roleList = sysRoleService.findList(page, wrapper);
        request.setAttribute("jumpUrl", "/role/list?roleName="+"&pageIndex=");  //构造访问路径
        model.addAttribute("roles",roleList);
        return "role/list";
    }

    //跳转到新增页面
    @RequestMapping("/role/add")
    public String toAdd(HttpSession session){
        System.out.println("进来了新增页面。。。。。");
        return "role/add";
    }

    //新增
    @RequestMapping("/role/save")
    @OperLog(operDescs = "描述",operType = "角色添加",operModel = "角色管理-添加")
    public String add(String roleName, String roleDesc,
                      SysRoleRight sysRoleRight,
                      @RequestParam(defaultValue = "1") Integer roleFlag,
                      @RequestParam(value = "rightCodes",required = false)List<String> rightCodes, Model model){
        System.out.println("进来了~~~~~~~~");
        SysRole sysRole=new SysRole();
        sysRole.setRoleName(roleName);
        sysRole.setRoleDesc(roleDesc);
        sysRole.setRoleFlag(roleFlag);
        int i = sysRoleService.addInfo(sysRole);
        for(int k=0;k<rightCodes.size();k++){
            sysRoleRight.setRfRightCode(rightCodes.get(k));
            sysRoleRight.setRfRoleId(sysRole.getRoleId());
            i = sysRoleRightService.addRoleRight(sysRoleRight);
            System.out.println("添加了"+i);
        }
        System.out.println("code"+rightCodes);
        if(i>0){
            System.out.println("添加成功");
            return "redirect:/role/list";
        }else{
            System.out.println("添加失败");
            return "role/add";
        }
    }

    //跳转到编辑页面
    @RequestMapping("/role/edit")
    public String toUp(Integer roleId,Model model){
        SysRole role = sysRoleService.findRoleById(roleId);
        HashSet<String> set=new HashSet<>();

        List<Integer> rfIdSet=new ArrayList<>();

        List<SysRoleRight> sysRoleRights = sysRoleRightService.selectRoleRightsByRoleId(roleId);

        List<String> collect = sysRoleRights.stream().map(SysRoleRight::getRfRightCode).collect(Collectors.toList());

        for (int k = 0; k < collect.size(); k++) {
               set.add(collect.get(k));
        }
//        for (SysRoleRight sysRoleRight : sysRoleRights) {
//            set.add(sysRoleRight.getRfRightCode());
////            rfIdSet.add(sysRoleRight.getRfId());      //rfId
//        }
        model.addAttribute("code",set);
//      关键  sysRoleRights
//        model.addAttribute("rfIdSet",rfIdSet);    //rfId
        model.addAttribute("role",role);
        model.addAttribute("roleRights",sysRoleRights);
        return "role/edit";
    }


    //修改
    @RequestMapping("/role/up")
    @OperLog(operDescs = "描述",operType = "角色修改",operModel = "角色管理-修改")
    public String up(String roleName,String roleDesc,Integer roleFlag,SysRoleRight sysRoleRight,Integer roleId,@RequestParam(value = "rfId",required = false) List<Integer> rfId,@RequestParam(value = "rightCodes",required = false)List<String> rightCodes,Model model){
        System.out.println("进来了~~~~~~~~");
        SysRole sysRole=new SysRole();
        sysRole.setRoleName(roleName);
        sysRole.setRoleDesc(roleDesc);
        sysRole.setRoleFlag(roleFlag);
        sysRole.setRoleId(roleId);
//        int i = sysRoleService.updateInfo(sysRole);
            int i=sysRoleRightService.deleCodeAndRoId(roleId);
        for(int k=0;k<rightCodes.size();k++){
            i= sysRoleRightService.addListByCodeAndRoId(rightCodes.get(k),roleId);
        }
        if(i>0){
            System.out.println("修改成功");
            return "redirect:/role/list";
        }else{
            System.out.println("修改失败");
            return "role/edit";
        }
    }

    //删除信息
    @RequestMapping("/role/del")
    @ResponseBody
    @OperLog(operDescs = "描述",operType = "角色删除",operModel = "角色管理-删除")
    public String dele(Integer roleId){
        int i = sysUserService.deleRoleId(roleId);
        int i3=sysRoleRightService.deleCodeAndRoId(roleId);
        int i2 = sysRoleService.deleInfo(roleId);
        if(i2>0){
            return "true";
        }else{
            return "false";
        }
    }
}

