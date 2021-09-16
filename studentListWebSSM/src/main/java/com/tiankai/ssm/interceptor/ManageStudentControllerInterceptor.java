package com.tiankai.ssm.interceptor;

import com.tiankai.ssm.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对控制器ManageStudentController进行拦截，只有用户登录后（即Session中有user对象且不为null）,
 * 才能执行ManageStudentController中的功能
 *
 * @author: xutiankai
 * @date: 9/5/2021 4:39 PM
 */
public class ManageStudentControllerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        User user = (User) request.getSession().getAttribute("user");

        /*
            request.getRequestURL() 返回全路径
            request.getRequestURI() 返回除去host（域名或者ip）部分的路径
            request.getContextPath() 返回工程名部分，如果工程映射为/，此处返回则为空
            request.getServletPath() 返回除去host和工程名部分的路径
         */
        String servletPath = request.getServletPath();

        // 配置需要拦截的url
        // 此处写的都是ManageStudentController中的方法，ManageStudentController中写了新方法后，
        // 要及时补充到这里来
        String[] interceptedUrl = new String[]{
                "/toAddStudentPage",
                "/student",
                "/toEditStudentPage",
                "/updateStudent",
        };

        // 是否需要拦截
        boolean needIntercept = false;
        for (String s : interceptedUrl) {
            if (servletPath.startsWith(s)) {
                needIntercept = true;
                break;
            }
        }

        if (!needIntercept) {
            // 无需拦截，直接放行
            // 卫语句
            return true;
        }

        if (user == null) {
            // 用户未登录
            // 不调用控制器方法, 并重定向至登录页面

            // 获取工程名
            String contextPath = request.getContextPath();

            // 获取请求方法
            String method = request.getMethod();

            if ("GET".equalsIgnoreCase(method)) {
                // 若为GET方法，将原来想要请求的地址放入Session中，登录后继续该请求
                request.getSession().setAttribute("servletPath", servletPath);
            } else {
                // 登录后的GET方法才能继续请求，其余方法的话跳转至登陆前的页面
                String referer = request.getHeader("Referer");

                // 请求的全域名
                String requestURL = new String(request.getRequestURL());


                // 域名+工程名，如http://localhost:8080/studentListWebSSM/
                String host = requestURL.replace(servletPath, "");
                // 上一次的请求地址(全域名)去掉域名+工程名后的结果
                // 也就是工程名后的请求地址
                String formerRequestRESTful = referer.replace(host, "");
                request.getSession().setAttribute("formerRequestRESTful", formerRequestRESTful);

            }

            // 重定向到登录页面
            response.sendRedirect(contextPath + "/login");

            return false;
        } else {
            // 用户已登录
            // 拦截器放行
            return true;
        }
    }
}
