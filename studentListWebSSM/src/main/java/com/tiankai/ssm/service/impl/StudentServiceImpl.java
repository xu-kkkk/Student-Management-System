package com.tiankai.ssm.service.impl;

import com.tiankai.ssm.bean.Page;
import com.tiankai.ssm.bean.Student;
import com.tiankai.ssm.mapper.StudentMapper;
import com.tiankai.ssm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现了StudentService中定义的功能
 *
 * @author: xutiankai
 * @date: 7/30/2021 4:31 PM
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    //    private final StudentDAO studentDAO = new StudentDAOImpl();
//    @Autowired
//    private StudentDAO studentDAO;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void addStudent(Student student) {
        studentMapper.addStudent(student);
    }

    @Override
    public void deleteStudentById(int stuId) {
        studentMapper.deleteStudentById(stuId);
    }

    @Override
    public Student queryStudentById(int stuId) {
        return studentMapper.showStudentById(stuId);
    }

    @Override
    public List<Student> queryStudents() {
        return studentMapper.showAllStudents();
    }

    @Override
    public Page<Student> page(int pageNo, int pageSize) {
        // 总的记录数
        int totalCount = studentMapper.queryStudentsCount();


        int pageTotal = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        // 调用pageNo前必须做边界检查！
        pageNo = pageNo > pageTotal ? pageTotal : pageNo;
        pageNo = pageNo < 1 ? 1 : pageNo;

        List<Student> items = studentMapper.queryPageItemsWithinRange((pageNo - 1) * pageSize, pageSize);

        Page<Student> page = new Page<>();
        page.setPageSize(pageSize);
        page.setPageTotal(pageTotal);


        page.setPageNo(pageNo);

        page.setItems(items);
        // setUrl在Servlet中执行
        page.setUrl(null);
        page.setPageItemTotalCount(totalCount);

        return page;
    }

    @Override
    public Page<Student> pageWithinAge(int pageNo, int pageSize, int minAge, int maxAge) {
        // 总的记录数
        int totalCount = studentMapper.queryStudentsCountWithinAge(minAge, maxAge);

        int pageTotal = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        // 调用pageNo前必须做边界检查！
        pageNo = pageNo > pageTotal ? pageTotal : pageNo;
        pageNo = pageNo < 1 ? 1 : pageNo;

        List<Student> items = studentMapper.queryPageItemsWithinRangeAndAge(
                (pageNo - 1) * pageSize, pageSize, minAge, maxAge);

        Page<Student> page = new Page<>();
        page.setPageSize(pageSize);
        page.setPageTotal(pageTotal);


        page.setPageNo(pageNo);

        page.setItems(items);
        // setUrl在Servlet中执行
        page.setUrl(null);
        page.setPageItemTotalCount(totalCount);

        return page;
    }

    @Override
    public void updateStudent(Student student) {
        studentMapper.updateStudentInfoById(student);
    }

    @Override
    public void deleteChosenStudents(Integer[] stuIds) {
        studentMapper.deleteChosenStudents(stuIds);
    }
}
