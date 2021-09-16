package com.tiankai.ssm.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 根据get/post时的参数调用不同的功能，使用反射来实现。
 * 该类为抽象类，必须被继承。
 *
 * @author: xutiankai
 * @date: 7/30/2021 4:14 PM
 */
@Deprecated
public abstract class BaseServlet extends HttpServlet {

//    protected final ApplicationContext context = new AnnotationConfigApplicationContext();
//    protected final StudentService studentService = context.getBean("studentServiceImpl", StudentServiceImpl.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理乱码问题
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");

        String action = req.getParameter("action");

        // 第一个参数为要调用的方法的名字，后面的可变形参为方法的参数
        try {
            Method declaredMethod =
                    this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            declaredMethod.setAccessible(true);
            // 执行上面的方法
            declaredMethod.invoke(this, req, resp);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 和post执行同样的功能，所以直接调用，不用写两份了
        doPost(req, resp);
    }
}
