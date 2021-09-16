package com.tiankai.ssm.filter;

import com.tiankai.ssm.bean.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对ManagerStudentServlet中的操作进行过滤
 *
 * @author: xutiankai
 * @date: 8/1/2021 8:25 PM
 * @deprecated ManagerStudentServlet已不再使用
 */
@Deprecated
public class ManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User user = (User) req.getSession().getAttribute("user");
        // 如果用户没有登录，则拦截这个请求，让用户跳转至登录界面
        if (user == null) {
            String preUrl = req.getParameter("pageUrl");
            if (preUrl == null || "".equals(preUrl)) {
                // 如果上一个请求中没有"pageUrl"参数，那么就直接获取上一个请求的url作为preUrl
                preUrl = req.getHeader("Referer");
            }
            // 将登录后跳转的页面的url放在Session域中
            req.getSession().setAttribute("preUrl", preUrl);
//            req.getRequestDispatcher("/pages/user/login.html").forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/pages/user/login.html");

            return;
        } else {
            // 用户已登录，让程序继续往下访问用户的目标资源
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
