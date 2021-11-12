package com.ktjiaoyu.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SuppressWarnings("all")
public class SysRightController {

    @Resource
    private SysRightService sysRightService;

    @Resource
    private SysRoleRightService sysRoleRightService;

    @OperLog(operDescs = "描述",operType = "资源列表",operModel = "用户管理-资源列表")
    @RequestMapping("/right/list")
    public String rightList(String rightText, String rightType, HttpSession session, @RequestParam(required = false, defaultValue = "1", value = "pageIndex") Integer pageIndex, Model model, HttpServletRequest request) {
        System.out.println("进来了~~~~~~~");
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(rightText)) {
            queryWrapper.eq("right_text", rightText);
        }
        if (!StringUtils.isEmpty(rightType)) {
            queryWrapper.eq("right_type", rightType);
        }
        queryWrapper.orderByDesc("right_code");
        Page<SysRight> page = new Page<>(pageIndex, 5);
        IPage<SysRight> page1 = sysRightService.findPage(page, queryWrapper);
        request.setAttribute("rightText", rightText);
        request.setAttribute("rightType", rightType);
        request.setAttribute("jumpUrl", "/right/list?rightText=" + rightText + "&rightType=" + rightType + "&pageIndex=");  //构造访问路径
//        model.addAttribute("roles",roleList);
        model.addAttribute("page1", page1);
        return "right/list";
    }

    //跳到资源添加页面
    @RequestMapping("/right/add")
    public String add() {
        System.out.println("进来了！！");
        return "right/add";
    }

    //添加一条数据
    @OperLog(operDescs = "描述",operType = "资源添加",operModel = "用户管理-添加")
    @RequestMapping("/right/save")
    public String save(SysRight sysRight) {
        System.out.println("进来了！！");
        if(sysRight.getRightParentCode()==null||sysRight.getRightParentCode()==""){
            sysRight.setRightParentCode("ROOT_MENU");
        }
        if(sysRight.getRightType().equals("Folder")||sysRight.getRightType().equals("Button")){
            sysRight.setRightUrl(null);
        }
        int i = sysRightService.addInfo(sysRight);
        if (i > 0) {
            System.out.println("添加成功");
            return "redirect:/right/list";
        }
        return "right/add";
    }

    @ResponseBody
    @RequestMapping(value = "/findrightCode")
    public String rightCode(HttpServletRequest request){
        System.out.println("异步验证code");
        String rightCode = request.getParameter("rightCode");
        if(sysRightService.yzsj(rightCode)>0){
            return "1";
        }
        return "0";
    }

    //异步删除
    @ResponseBody
    @OperLog(operDescs = "描述",operType = "资源删除",operModel = "资源管理-删除")
    @RequestMapping(value = "/rightdel")
    public String rightdel(String rightCode,String rightType,Integer usrRoleId){
        System.out.println("异步验证删除");
        if(rightType.equals("Folder")) {         //假设你删除的是父类菜单
            System.out.println("22222"+rightCode);
            List<SysRight> sysRights = sysRightService.selectByParentCode(rightCode);    //那么你就要先根据父类菜单的编号righecode去查询子类菜单
            if(sysRights.size()!=0) {         //如果他有子类
                System.out.println("11111111111111111");
                List<String> rightCodeList = sysRights.stream().map(SysRight::getRightCode).collect(Collectors.toList());    //获得这一堆code的集合
                for (int k = 0; k < rightCodeList.size(); k++) {
                    sysRoleRightService.delteCode(rightCodeList.get(k));      //之后循环先删掉权限资源表子类的
                    sysRoleRightService.delteCode(rightCode);                 //在删掉权限资源父类的
                }
                sysRightService.deleRightByrightParentCode(rightCode);        //然后删掉资源表子类
                int i = sysRightService.deleRight(rightCode);                 //最后删掉资源表父类
                if (i > 0) {
                    return "1";
                }
            }else{
                System.out.println("哈哈哈");
                sysRoleRightService.delteCode(rightCode);                      //如果他没有子类，先删资源权限表
                int i = sysRightService.deleRight(rightCode);                  //再删资源表
                if (i > 0) {
                    return "1";
                }
            }
            }
        else{
            sysRoleRightService.delteCode(rightCode);                          //如果他不是父类菜单。删权限资源
            int i=sysRightService.deleRight(rightCode);                        //再资源表
            if(i>0){
                return "1";
            }
        }
        return "0";
    }


    //进入修改页面
    @RequestMapping("/right/edit")
    public String rightedit(String rightCode,Model model){
        System.out.println("进来了~~~~");
        SysRight sysRight = sysRightService.selectByRightCode(rightCode);
        model.addAttribute("right",sysRight);
        return "right/edit";
    }

    //修改成功
    @OperLog(operDescs = "描述",operType = "资源修改",operModel = "资源管理-修改")
    @RequestMapping("/right/up")
    public String edit(SysRight sysRight){
        System.out.println("进来了~~~~");
        if(sysRight.getRightParentCode()==null||sysRight.getRightParentCode()==""){
            sysRight.setRightParentCode("ROOT_MENU");
        }
        if(sysRight.getRightType().equals("Folder")||sysRight.getRightType().equals("Button")){
            sysRight.setRightUrl(null);
        }
        int i = sysRightService.updateInfo(sysRight);
        if(i>0){
            System.out.println("修改成功!");
            return "redirect:/right/list";
        }
        return "right/eidt";
    }
}
