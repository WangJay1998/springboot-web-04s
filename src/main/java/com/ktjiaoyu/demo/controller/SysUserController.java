package com.ktjiaoyu.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ktjiaoyu.demo.mapper.OperLog;
import com.ktjiaoyu.demo.pojo.SysRight;
import com.ktjiaoyu.demo.pojo.SysRole;
import com.ktjiaoyu.demo.pojo.SysRoleRight;
import com.ktjiaoyu.demo.pojo.SysUser;
import com.ktjiaoyu.demo.service.SysRightService;
import com.ktjiaoyu.demo.service.SysRoleRightService;
import com.ktjiaoyu.demo.service.SysRoleService;
import com.ktjiaoyu.demo.service.SysUserService;
import com.ktjiaoyu.demo.utils.Page;
import com.ktjiaoyu.demo.utils.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Value;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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
@Api(description = "user类")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleRightService sysRoleRightService;

    @Resource
    private SysRightService sysRightService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //redis中数据的key的前缀
    private String SHIRO_LOGIN_COUNT="shiro_login_count_";  //登录计数


//    @OperLog(operDesc = "描述",operType = "用户登录",operModel = "用户管理-登录")
    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public String login(String usrName, String usrPassword,HttpSession session, Model model){
        System.out.println("name"+usrName+"passwrd"+usrPassword);
//        SysUser sysUser = sysUserService.seleRoleName(usrName, usrPassword);
//        if(sysUser!=null){
//            session.setAttribute("sysUser",sysUser);
//            System.out.println(sysUser);
//            return "redirect:/main";
//        }else{
//            model.addAttribute("msg","您输入的账号或者密码错误");
//            return "login";
//        }
        try {
            //比较验证码
//            String codes = (String) session.getAttribute("code");
//            usrPassword=sysUserService.encryptPassword(usrPassword);
//            if (codes.equalsIgnoreCase(code)) {
                UsernamePasswordToken token = new UsernamePasswordToken(usrName, usrPassword);
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);  //认证登录
                //登录成功.清空登陆数
                stringRedisTemplate.delete(SHIRO_LOGIN_COUNT + usrName);

                SysUser sysUser = (SysUser) subject.getPrincipal();
                //left动态显示
                List<SysRight> sysRights = new ArrayList<>();
//            HashSet<SysRight> sysRights=new HashSet<>();
                List<SysRoleRight> sysRoleRights = sysRoleRightService.selectRoleRightsByRoleId(sysUser.getUsrRoleId());
                for (SysRoleRight sysRoleRight : sysRoleRights) {
                    System.out.println("编码" + sysRoleRight.getRfRightCode());
                    SysRight right = sysRightService.selectRightsByRightId(sysRoleRight.getRfRightCode());
                    sysRights.add(right);
                    session.setAttribute("right", sysRights);
                }

                session.setAttribute("sysUser", sysUser);
                return "redirect:/main";

        }catch (UnknownAccountException| IncorrectCredentialsException e){
            e.printStackTrace();
            model.addAttribute("msg","用户或密码被错误,登录失败");
            return "login";
        }catch (LockedAccountException e){
            model.addAttribute("msg","用户禁用,登录失败");
            return "login";
        }catch (DisabledAccountException e){
            model.addAttribute("msg","用户锁定，等tm一个小时");
            return "login";
        } catch (AuthenticationException e){
            model.addAttribute("msg","认证异常,登录失败");
            return "login";
        }
    }

    @RequestMapping("/main")
    public String toMain(){
       return "main";
    }

    //退出
//    @RequestMapping("/logout")
//    public String logout(HttpSession session){
////        return "login";
//        session.removeAttribute("sysUser");
//        SecurityUtils.getSubject().logout();
//        return "redirect:main";
//    }

    //跳转到显示并且分页页面
    @OperLog(operDescs = "描述",operType = "用户列表",operModel = "用户列表")
    @GetMapping("/user/list")
    public String userList(Model model,@RequestParam(required = false, defaultValue = "1", value = "pageIndex") Integer pageIndex,
                            String usrName,@RequestParam(required = false,value = "roleId")Integer roleId
                           ){
        System.out.println("进来了~~~~~~~~~"+roleId);
//        QueryWrapper<SysUser> wrapper=new QueryWrapper<>();
        int i = sysUserService.count(usrName, roleId);

        Page page = new Page();
        page.setPageDq(pageIndex);
        page.setPageSum(i);
        int totalPageCount = page.getPageCount();
        SysUser sysUser = new SysUser();
        sysUser.setUsrRoleId(roleId);
        sysUser.setUsrName(usrName);
        sysUser.setPageIndex((page.getPageDq() - 1) * page.getPageSize());
        sysUser.setPageSize(page.getPageSize());
        List<SysUser> list = sysUserService.list(sysUser);
        model.addAttribute("users", list);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("currentPageNo", pageIndex);
        List<SysRole> sysRoles = sysRoleService.findroleName();
        model.addAttribute("roles",sysRoles);
        sysRoles.forEach(System.out::println);
        return "user/list";
    }

    @OperLog(operDescs = "描述",operType = "用户列表",operModel = "用户列表-条件查询")
    @PostMapping("/user/list")
    public String userList2(Model model,@RequestParam(required = false, defaultValue = "1", value = "pageIndex") Integer pageIndex,
                           String usrName,@RequestParam(required = false,value = "roleId")Integer roleId,HttpSession session
    ){
        System.out.println("进来了~~~~~~~~~"+roleId);
        int i = sysUserService.count(usrName, roleId);
        Page page = new Page();
        page.setPageDq(pageIndex);
        page.setPageSum(i);
        int totalPageCount = page.getPageCount();
        SysUser sysUser = new SysUser();
        sysUser.setUsrRoleId(roleId);
        sysUser.setUsrName(usrName);
        sysUser.setPageIndex((page.getPageDq() - 1) * page.getPageSize());
        sysUser.setPageSize(page.getPageSize());
        List<SysUser> list = sysUserService.list(sysUser);
        model.addAttribute("users", list);
        session.setAttribute("roleId", roleId);
        session.setAttribute("usrName", usrName);
        model.addAttribute("roleId", roleId);
        model.addAttribute("usrName", usrName);
        model.addAttribute("totalPageCount", totalPageCount);
//        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", pageIndex);
        List<SysRole> sysRoles = sysRoleService.findroleName();
        System.out.println(roleId);
        model.addAttribute("roles",sysRoles);
//        model.addAttribute("users",page);
        sysRoles.forEach(System.out::println);
        return "user/list";
    }

    //跳转到修改页面
    @RequestMapping(value = "/user/edit",method = RequestMethod.PUT)
    public String edit(Model model,Integer usrId){
        System.out.println("进来了~~~~~"+usrId);
        SysUser user = sysUserService.findById(usrId);
        model.addAttribute("user",user);
        List<SysRole> roles = sysRoleService.findroleName();
        model.addAttribute("roles",roles);
        return "user/edit";
    }

    //修改数据
    //跳转到修改页面
    @OperLog(operDescs = "描述",operType = "用户修改",operModel = "用户管理-修改")
    @RequestMapping("/user/save")
    public String updateInfo(Model model,Integer usrId,String usrName,String usrPassword,@RequestParam(required = false,value = "roleId") Integer roleId){
        System.out.println("进来了~~~~~"+roleId+"\n"+usrId+"\n"+usrName+"usrId"+usrId);
        SysUser sysUser=new SysUser();
        sysUser.setUsrName(usrName);
        usrPassword=sysUserService.encryptPassword(usrPassword);
        sysUser.setUsrPassword(usrPassword);
        sysUser.setUsrRoleId(roleId);
        sysUser.setUsrId(usrId);
        int i = sysUserService.updateInfo(sysUser);
        if(i>0){
            System.out.println("修改成功！");
            return "redirect:/user/list";
        }else{
            System.out.println("修改失败！");
            return "user/edit";
        }
    }

    //跳转到新增页面

    @RequestMapping(value = "/user/add")
    public String toAdd(Model model){
        List<SysRole> roles = sysRoleService.findroleName();
        model.addAttribute("roles",roles);
        return "user/add";
    }

    //新增数据
    @OperLog(operDescs = "描述",operType = "用户添加",operModel = "用户管理-添加")
    @RequestMapping(value = "/user/inse",method = RequestMethod.POST)
    public String add(String usrName,String usrPassword,@RequestParam(defaultValue = "1") int usrFlag,@RequestParam(required = false,value = "roleId")Integer roleId){
        System.out.println("进来了~~~~~~");
        SysUser sysUser=new SysUser();
        sysUser.setUsrName(usrName);
        String password = sysUserService.encryptPassword(usrPassword);
        System.out.println(password);
        sysUser.setUsrPassword(password);
        sysUser.setUsrFlag(usrFlag);
        sysUser.setUsrRoleId(roleId);
        int i = sysUserService.insertInfo(sysUser);
        if(i>0){
            System.out.println("添加成功！");
            return "redirect:/user/list";
        }else{
            System.out.println("添加失败！");
            return "user/add";
        }
    }

    //删除数据

    @RequestMapping(value = "/userdel",method = RequestMethod.DELETE)
    @OperLog(operDescs = "描述",operType = "用户删除",operModel = "用户管理-删除")
    @ResponseBody
    public String del(@RequestParam(required = false,value = "usrId")Integer usrId){
        System.out.println("进来删除了~~~~~~~~"+usrId);
        int i = sysUserService.delInfo(usrId);
        if(i==1){
            return "1";
        }else{
            return "0";
        }
    }

    @RequestMapping("/403")
    public String unauth(){
        return "403";
    }

//    @RequestMapping("/getImage")
//    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
//        //生成验证码
//        String code = VerifyCodeUtils.generateVerifyCode(4);
//        //验证码放入session
//        session.setAttribute("code",code);
//        //验证码存入图片
//        ServletOutputStream os = response.getOutputStream();
//        response.setContentType("image/png");
//        VerifyCodeUtils.outputImage(220,60,os,code);
//    }

}

