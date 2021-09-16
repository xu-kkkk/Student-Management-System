package com.tiankai.ssm.controller;

import com.tiankai.ssm.service.StudentService;
import com.tiankai.ssm.service.impl.StudentServiceImpl;
import com.tiankai.ssm.bean.Page;
import com.tiankai.ssm.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author: xutiankai
 * @date: 8/22/2021 1:19 PM
 */
@Controller
public class ManageStudentController {
    @Autowired
    private StudentService studentService;

    /**
     * a标签"添加学生“的功能, 跳转到add-student.html页面
     *
     * @return
     * @deprecated 使用Modal模态页，不再需要跳转到独立的页面了
     */
    @Deprecated
    @RequestMapping("/toAddStudentPage")
    public ModelAndView toAddStudentPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("add-student");

        return modelAndView;
    }

    /**
     * 添加学生信息
     *
     * @param name
     * @param gender
     * @param age
     * @return
     */
    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public ModelAndView addStudent(
            @RequestParam String name,
            @RequestParam String gender,
            @RequestParam Integer age) {
        ModelAndView modelAndView = new ModelAndView();

        Student student = new Student(name, null, age);
        student.setGender(gender);

        studentService.addStudent(student);
        // 注意！这里是重定向到一个斜杠!
        // 添加信息后重定向到列表的最后一个页面
        modelAndView.setViewName("redirect:/" + Integer.MAX_VALUE);

        return modelAndView;
    }

    /**
     * 删除学生信息
     *
     * @param id
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/student/{id}/{pageNo}", method = RequestMethod.DELETE)
    public ModelAndView deleteStudent(@PathVariable("id") Integer id, @PathVariable("pageNo") Integer pageNo) {
        ModelAndView modelAndView = new ModelAndView();

        studentService.deleteStudentById(id);

        pageNo = pageNo == null ? Page.INIT_PAGE_SIZE : pageNo;

//        modelAndView.addObject("pageNo", pageNo);
        modelAndView.setViewName("redirect:/" + pageNo);

        return modelAndView;
    }

    /**
     * 跳转到edit-student.html
     * 获取pageNo, 并将其请求转发到新的页面，使得更新完信息后能够回到原来的页面
     * 根据stuId实例化Student对象，并将其放到Request域中，用于表单数据的回显
     *
     * @param pageNo 当前页面
     * @return
     */
    @RequestMapping(value = "/toEditStudentPage/{stuId}/{pageNo}", method = RequestMethod.GET)
    public ModelAndView toEditStudentPage(@PathVariable Integer stuId, @PathVariable Integer pageNo) {
        ModelAndView modelAndView = new ModelAndView("edit-student");


        Student student = studentService.queryStudentById(stuId);
        modelAndView.addObject("student", student);
        modelAndView.addObject("pageNo", pageNo);

        return modelAndView;
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public ModelAndView editStudent(
            @RequestParam Integer stuId,
            @RequestParam String name,
            @RequestParam String gender,
            @RequestParam Integer age,
            @RequestParam Integer pageNo) {

        ModelAndView modelAndView = new ModelAndView();

        studentService.updateStudent(new Student(stuId, name, gender, age));

        modelAndView.setViewName("redirect:/" + pageNo);
        return modelAndView;
    }


    /**
     * 批量删除
     * 完成后不由后端服务器跳转，而是返回一个ResponseBody, 由前端的js刷新页面，
     * 这样比后端跳转要方便很多，不再需要通过传参告诉后端服务器跳转到哪个页面去，可以
     * 由前端的js直接刷新。
     * <p>
     * 注意: RequestParam中为拼接的请求参数，不是js中封装多个请求参数的那个数组,
     * 一定要搞清楚RequestParam到底是哪个！根据请求地址中的参数来！
     * <p>
     * 目前只会用GET请求做，但根据SpringMVC的惯例，此处应该使用DELETE请求
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteChosenStudents", method = RequestMethod.GET)
    @ResponseBody
    public String deleteChosenStudents(@RequestParam("stuId") Integer[] ids) {

        studentService.deleteChosenStudents(ids);

        return "ok";
    }
}
