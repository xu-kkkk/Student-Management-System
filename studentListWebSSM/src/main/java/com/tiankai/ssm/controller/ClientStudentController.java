package com.tiankai.ssm.controller;

import com.tiankai.ssm.service.StudentService;
import com.tiankai.ssm.service.impl.StudentServiceImpl;
import com.tiankai.ssm.utils.DefaultValue;
import com.tiankai.ssm.bean.Page;
import com.tiankai.ssm.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 用于Student数据的展示
 *
 * @author: xutiankai
 * @date: 8/22/2021 12:53 PM
 */
@Controller
public class ClientStudentController {

    // 经测试发现这里不需要再加载配置类了，可以继续自动装入。
    // 也许是在WebInit类中已经加载了SpringConfiguration配置类?
    // 不能用接口的实现类StudentServiceImpl, 必须要用公共的接口StudentService!
    @Autowired
    private StudentService studentService;

    /**
     * 用于分页展示首页的学生信息
     *
     * @param pageNo
     * @return
     */
    @RequestMapping(value = {"/", "/{pageNo}"})
    public ModelAndView page(@PathVariable(required = false) Integer pageNo) {
        ModelAndView modelAndView = new ModelAndView();

        pageNo = pageNo == null ? DefaultValue.DEFAULT_PAGE_NO : pageNo;

        // Thymeleaf有分页模板，但是我没学过，所以这里就做简陋一点了
        int pageSize = Page.INIT_PAGE_SIZE;

        Page<Student> page = studentService.page(pageNo, pageSize);
        page.setUrl("/");

        modelAndView.addObject("page", page);
        modelAndView.setViewName("index");

        return modelAndView;
    }

    /**
     * 根据年龄查询用户信息, 封装成Page对象后放入Request域中
     * 要将min_age和max_age也放入Request域中，用户回显数据
     *
     * @param pageNo
     * @param minAge
     * @param maxAge
     * @return
     */
    @RequestMapping(value = "/pageWithinAge", method = RequestMethod.GET)
    public ModelAndView pageWithinAge(
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(name = "min_age", required = false) Integer minAge,
            @RequestParam(name = "max_age", required = false) Integer maxAge) {

        ModelAndView modelAndView = new ModelAndView();

        // 此处使用卫语句
        // 若minAge和maxAge均为null, 则不应该按照年龄查询，故重定向到/方法，不再执行下面的步骤了
        if (minAge == null && maxAge == null) {
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }

        // 初始化可能为null的参数
        pageNo = pageNo == null ? DefaultValue.DEFAULT_PAGE_NO : pageNo;
        minAge = minAge == null ? DefaultValue.DEFAULT_AGE : minAge;
        maxAge = maxAge == null ? DefaultValue.MAX_AGE : maxAge;


        Page<Student> studentPage = studentService.pageWithinAge(pageNo, Page.INIT_PAGE_SIZE, minAge, maxAge);

        modelAndView.addObject("page", studentPage);

        modelAndView.addObject("min_age", minAge);
        modelAndView.addObject("max_age", maxAge);

        // 请求转发至首页
        modelAndView.setViewName("index");

        return modelAndView;
    }
}
