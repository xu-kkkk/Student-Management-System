package com.tiankai.ssm.web;

import com.google.gson.Gson;
import com.tiankai.ssm.service.StudentService;
import com.tiankai.ssm.utils.DefaultValue;
import com.tiankai.ssm.utils.WebUtils;
import com.tiankai.ssm.bean.Page;
import com.tiankai.ssm.bean.Student;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用于Student数据的展示
 *
 * @author: xutiankai
 * @date: 7/30/2021 6:10 PM
 */
@Deprecated
public class ClientStudentServlet extends BaseServlet {
    //    private final StudentService studentService = new StudentServiceImpl();
    // 使用Spring的方式
    private final ApplicationContext context = null;
    // 要用service的实现类，而不是接口
    private final StudentService studentService = null;

    /**
     * 用于数据展示，所有数据展示在同一页面中
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     * @deprecated 写了分页的逻辑后就不再需要将所有数据展示在同一个页面中了，应尽量避免再使用该类。
     */
    @Deprecated
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = studentService.queryStudents();
        // 将查询到的students对象组成的集合放到Request域中
        req.setAttribute("students", students);


        // 使用json，传到前端后由vue处理
        Gson gson = new Gson();
        String json = gson.toJson(students);
        resp.getWriter().write(json);

        // 请求转发到要去的页面
        req.getRequestDispatcher("/pages/index.jsp").forward(req, resp);
    }

    /**
     * 用于分页数据展示
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), DefaultValue.DEFAULT_PAGE_NO);
        int pageSize = Page.INIT_PAGE_SIZE;

        Page<Student> page = studentService.page(pageNo, pageSize);
        page.setUrl("client/studentServlet?action=page&pageNo=");

        // 将page保存到Request域中
        req.setAttribute("page", page);
        req.getRequestDispatcher("/pages/index.jsp").forward(req, resp);
    }

    /**
     * 用于分页数据展示，展示用户指定的年龄范围内的用户
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pageWithinAge(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), DefaultValue.DEFAULT_PAGE_NO);
        int pageSize = Page.INIT_PAGE_SIZE;

        int minAge = WebUtils.parseInt(req.getParameter("min_age"), DefaultValue.DEFAULT_AGE);
        int maxAge = WebUtils.parseInt(req.getParameter("max_age"), DefaultValue.MAX_AGE);

        Page<Student> page = studentService.pageWithinAge(pageNo, pageSize, minAge, maxAge);
        page.setUrl("client/studentServlet?action=pageWithinAge&min_age=" + minAge + "&max_age=" + maxAge + "&pageNo=");

        // 将page保存到Request域中
        req.setAttribute("page", page);

        // maxAge不是默认值时将两个参数保存到request域中，用于input标签回显
        if (maxAge != DefaultValue.MAX_AGE) {
            req.setAttribute("min_age", minAge);
            req.setAttribute("max_age", maxAge);
        }

        req.getRequestDispatcher("/pages/index.jsp").forward(req, resp);
    }
}
