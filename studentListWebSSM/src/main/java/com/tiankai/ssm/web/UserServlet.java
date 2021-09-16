package com.tiankai.ssm.web;

import com.google.gson.Gson;
import com.tiankai.ssm.service.UserService;
import com.tiankai.ssm.bean.User;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: xutiankai
 * @date: 8/1/2021 9:44 AM
 */
@Deprecated
public class UserServlet extends BaseServlet {
//    private final UserService userService = new UserServiceImpl();

    private final ApplicationContext context = null;
    private final UserService userService = null;

    /**
     * 添加用户账号
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User(username, password, email);
        userService.addUser(user);

        // 注册后就自动登录了，将User对象放到Session中
        req.getSession().setAttribute("user", user);

        resp.sendRedirect("index.jsp");
    }

    /**
     * 登录用户账户
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.queryUserByUsername(username);

        if (user != null && password.equals(user.getPassword())) {
            // 成功获取到用户账号信息，且密码正确

            // 将user对象保存到session中
            req.getSession().setAttribute("user", user);

            String preUrl = (String) req.getSession().getAttribute("preUrl");
            // 取出来之后就将其清空
            req.getSession().setAttribute("preUrl", null);
            if (preUrl == null || "".equals(preUrl)) {
                // 没有上一次请求的地址
                // 重定向到首页
                resp.sendRedirect("index.jsp");
            } else {
                resp.sendRedirect(preUrl);
            }


        } else {
            // 未成功获取到用户账号信息，或密码错误

            // 应该将用户输入的数据回传到输入页面，
            // 但由于登录页面没有用jsp写，vue绑定数据我也不会，所以这一步就暂时省了
            req.getRequestDispatcher("/pages/user/login.html").forward(req, resp);
        }
    }

    /**
     * 登出账户
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();

        resp.sendRedirect("index.jsp");
    }

    /**
     * 根据Request域中的用户名username判断用户是否存在
     * 在register.js中请求这个方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxExistUsername(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        boolean existUsername = userService.existUsername(username);

        // 将返回的结果封装成Map对象
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("existUsername", existUsername);

        Gson gson = new Gson();
        // 将Map转换成json字符串
        String json = gson.toJson(resultMap);

        resp.getWriter().write(json);
    }
}
