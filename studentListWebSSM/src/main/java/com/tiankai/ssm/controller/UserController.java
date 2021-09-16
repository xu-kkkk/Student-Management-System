package com.tiankai.ssm.controller;

import com.tiankai.ssm.service.StudentService;
import com.tiankai.ssm.service.UserService;
import com.tiankai.ssm.service.impl.UserServiceImpl;
import com.tiankai.ssm.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author: xutiankai
 * @date: 8/23/2021 4:06 PM
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 跳转至登录页面user/login.html
     * 仅用于跳转，不做其他功能
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView toLoginPage() {
        return new ModelAndView("user/login");
    }

    @PostMapping(value = "/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

//        System.out.println("username = " + username);
//        System.out.println("password = " + password);


        User user = userService.queryUserByUsername(username);

        // &&的左边不成立的话就不会继续判断右边了，所以不会报空指针异常
        if (user != null && password.equals(user.getPassword())) {
            // 密码正确，登录成功，将user对象放入session域中
            session.setAttribute("user", user);

            // 获取原来想要请求的地址（可能为null)
            String servletPath = (String) session.getAttribute("servletPath");
            if (servletPath == null) {
                // 若Session中没有servletPath, 说明之前的不是GET请求,
                // 用重定向到之前用户所在的页面, 由用户重新发起请求

                String formerRequestRESTful = (String) session.getAttribute("formerRequestRESTful");

                if (formerRequestRESTful == null) {
                    // 跳转至首页
                    modelAndView.setViewName("redirect:/");
                } else {
                    session.removeAttribute("formerRequestRESTful");
                    modelAndView.setViewName("redirect:" + formerRequestRESTful);
                }
            } else {
                // 若Session中有servletPath, 直接重定向到这里就行了，继续完成用户本
                // 打算完成的GET请求

                // servletPath只需用一次，可以从Session域中移除了
                session.removeAttribute("servletPath");
                // 重定向至原来要请求的页面
                modelAndView.setViewName("redirect:" + servletPath);
            }

        } else {
            // 用户不存在或密码错误，登录失败，返回上一页

            // Request域中放一个错误提示，用于在前端显示。
            // 但我不做前端，不用jsp的话暂时不知道如何将该错误信息显示出来, 所以现在看不到效果。
            modelAndView.addObject("errMessage", "用户名或密码错误");
            // 回到登录页面
            modelAndView.setViewName("user/login");
        }


        return modelAndView;
    }

    /**
     * 用于页面跳转，跳转至user/register页面，让用户输入登录信息
     * get方法请求/register的话使用该方法
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView toRegisterPage() {
        return new ModelAndView("user/register");
    }

    /**
     * 用户注册用户，将新注册的用户信息写入数据库，完成后回到主页。
     * 使用POST方法请求/register时使用该方法
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email) {

        ModelAndView modelAndView = new ModelAndView();

        if (userService.existUsername(username)) {
            // 用户名已存在，不能再插入了

            // 回传错误信息，用于在alert中显示
            modelAndView.addObject("errMessage", "用户名已存在！");

            // 回传除用户名之外的输入的数据，避免用户重复输入
            modelAndView.addObject("password", password);
            modelAndView.addObject("email", email);

            // 回到注册页面
            modelAndView.setViewName("user/register");
        } else {
            // 用户名不存在，可以插入
            userService.addUser(new User(null, username, password, email));

            // 结束后重定向至登录页面
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }

    /**
     * 登出用户账户，即把User对象的实例从Session中取出，并重定向至首页
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        // 将session中的user改成null
//        session.setAttribute("user", null);

        // 将user实例从Session中移除
        session.removeAttribute("user");

        // 登出完成，重定向回首页
        return new ModelAndView("redirect:/");
    }
}
