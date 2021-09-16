package com.tiankai.ssm.web;

import com.tiankai.ssm.service.StudentService;
import com.tiankai.ssm.utils.WebUtils;
import com.tiankai.ssm.bean.Student;
import com.tiankai.ssm.utils.DefaultValue;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于Student数据的管理（增删改）。
 * 该类下的所有操作均需要用户登录后才能进行，由Filter过滤器进行保证，无需在该类中判断user是否为null。
 *
 * @author: xutiankai
 * @date: 7/31/2021 12:44 PM
 */
@Deprecated
public class ManagerStudentServlet extends BaseServlet {
//    private final StudentService studentService = new StudentServiceImpl();

    private final ApplicationContext context = null;
    // new AnnotationConfigApplicationContext()构造器的参数不写的话会报错说context没有refresh，
    // 很难让人想明白是因为构造器缺少参数的原因。百度之后才发现是这个地方错了。
    private final StudentService studentService = null;

    /**
     * 向数据库中添加学生信息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void addStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String age = req.getParameter("age");

        Student student = new Student();
        student.setName(name);
        student.setGender(gender);
        student.setAge(Integer.parseInt(age));

        studentService.addStudent(student);

        // 添加完成后重定向回首页
        resp.sendRedirect(req.getContextPath() + "/client/studentServlet?action=page&pageNo=" + Integer.MAX_VALUE);
    }

    /**
     * 删除一条学生信息
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 登录后才能删除信息，否则跳转到登录界面
//        User user = (User) req.getSession().getAttribute("user");
        // 使用了Filter过滤器，所以这边不再需要判断用户是否已经登录
//        if (user == null) {
//            // 没有从Session域中获取到User对象，说明没有登录
//            // 跳转到登录页面
//            // 获取当前请求的上一个url
//            String preUrl = req.getHeader("Referer");
//            req.getSession().setAttribute("preUrl", preUrl);
////            req.getRequestDispatcher("/pages/user/login.html").forward(req, resp);
//            resp.sendRedirect(req.getContextPath() + "/pages/user/login.html");
//
//            return;
//        }

        // 模拟一个异常，测试过滤器是否正常运行
        // 测试后一定要注释掉！
//        int i = 5 / 0;

        // 若转换时有异常，则stuId为0的，因为没有学生的编号为0
        int stuId = WebUtils.parseInt(req.getParameter("stuId"), DefaultValue.DEFAULT_STUDENT_ID);
        studentService.deleteStudentById(stuId);

        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), DefaultValue.DEFAULT_PAGE_NO);
        resp.sendRedirect(req.getContextPath() + "/client/studentServlet?action=page&pageNo=" + pageNo);
    }

    /**
     * 将用户修改后的学生信息写进数据库。操作执行完成后，重定向回用户执行该操作前所在的页面。
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void editStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 登录后才能编辑
        // 由Filter过滤器判断是否已经登录，这里不用再写这个逻辑了
//        User user = (User) req.getSession().getAttribute("user");

//        if (user == null) {
//            // 没有从Session域中获取到User对象，说明没有登录
//            // 跳转到登录页面
//            // 获取当前请求的上一个url
//            String preUrl = req.getParameter("pageUrl");
//            // 将登录后跳转的页面的url放在Session域中
//            req.getSession().setAttribute("preUrl", preUrl);
////            req.getRequestDispatcher("/pages/user/login.html").forward(req, resp);
//            resp.sendRedirect(req.getContextPath() + "/pages/user/login.html");
//
//            return;
//        }

        int stuId = WebUtils.parseInt(req.getParameter("stuId"), DefaultValue.DEFAULT_STUDENT_ID);
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        int age = WebUtils.parseInt(req.getParameter("age"), DefaultValue.DEFAULT_AGE);

        Student student = new Student(stuId, name, gender, age);
        studentService.updateStudent(student);

        // 获取上一次展示信息的页面的url，并重定向过去
        String pageUrl = req.getParameter("pageUrl");
        resp.sendRedirect(pageUrl);
    }

    /**
     * 将用户选定要编辑的那个学生的信息保存到Request域中，再请求转发到edit-student.jsp页面，用于edit-student.jsp上学生信息的显示
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void editStudentPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int stuId = WebUtils.parseInt(req.getParameter("stuId"), DefaultValue.DEFAULT_STUDENT_ID);
        Student student = studentService.queryStudentById(stuId);
        req.setAttribute("student", student);

        // 获取上一次请求的地址，用于完成student-edit.jsp页面的功能后的跳转
        String referer = req.getHeader("Referer");
        req.setAttribute("pageUrl", referer);

        // 这里要传数据，得用请求转发，不能是重定向
        req.getRequestDispatcher("/pages/edit-student.html").forward(req, resp);
    }
}
