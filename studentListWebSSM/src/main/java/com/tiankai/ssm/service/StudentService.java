package com.tiankai.ssm.service;

import com.tiankai.ssm.bean.Page;
import com.tiankai.ssm.bean.Student;

import java.util.List;

/**
 * @author: xutiankai
 * @date: 7/30/2021 3:58 PM
 */
public interface StudentService {
    /**
     * 添加学生信息
     */
    void addStudent(Student student);

    /**
     * 根据学号删除学生信息
     *
     * @param stuId
     */
    void deleteStudentById(int stuId);

    /**
     * 根据学号stuId查询学生信息，并返回
     *
     * @param stuId 学号
     * @return Student对象
     */
    Student queryStudentById(int stuId);

    /**
     * 查询学生信息，并已List<Student>的形式返回
     *
     * @return List<Student>
     */
    List<Student> queryStudents();


    /**
     * 处理分页数据，返回Page对象，展示给用户的数据在Page中的items里
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Student> page(int pageNo, int pageSize);

    /**
     * 处理分页数据，根据用户指定的年龄范围返回Page对象，展示给用户的数据在Page中的items里
     *
     * @param pageNo
     * @param pageSize
     * @param minAge
     * @param maxAge
     * @return
     */
    Page<Student> pageWithinAge(int pageNo, int pageSize, int minAge, int maxAge);

    /**
     * 修改学生信息
     *
     * @param student
     */
    void updateStudent(Student student);

    /**
     * 批量删除选中的学生
     *
     * @param stuIds
     */
    void deleteChosenStudents(Integer[] stuIds);
}
