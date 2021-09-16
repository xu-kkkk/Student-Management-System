package com.tiankai.ssm.dao;

import com.tiankai.ssm.bean.Student;

import java.util.List;

/**
 * @author: xutiankai
 * @date: 7/30/2021 3:39 PM
 * @deprecated 整合了mybatis, 使用映射xxxMapper, 不再用xxxDAO
 */
@Deprecated
public interface StudentDAO {
    /**
     * 新增一条学生信息
     *
     * @param student
     * @return 受影响的行数
     */
    int addStudent(Student student);

    /**
     * 根据学号stuId删除学生信息
     *
     * @param stuId 学号
     * @return 受影响的行数
     */
    int deleteStudentById(int stuId);

    /**
     * 修改学生信息
     * 要修改的学生由参数中的student实例中保存的学号获得
     *
     * @param student
     * @return
     */
    int updateStudentInfoById(Student student);

    /**
     * 查询所有学生信息，已List<Student>集合返回
     *
     * @return
     */
    List<Student> showAllStudents();

    /**
     * 查询分页上显示的数据。从begin开始，共pageSize个记录
     *
     * @param begin
     * @param pageSize
     * @return
     */
    List<Student> queryPageItemsWithinRange(int begin, int pageSize);

    /**
     * 查询分页上显示的数据。从begin开始，共pageSize个记录，查询年龄在minAge（含）和maxAge（含）之间的学生信息。
     *
     * @param begin
     * @param pageSize
     * @param minAge
     * @param maxAge
     * @return
     */
    List<Student> queryPageItemsWithinRangeAndAge(int begin, int pageSize, int minAge, int maxAge);

    /**
     * 返回指定学号的学生的信息
     *
     * @return
     */
    Student showStudentById(int stuId);

    /**
     * 返回学生信息条数
     *
     * @return
     */
    int queryStudentsCount();

    /**
     * 返回指定年龄范围内的学生信息条数
     *
     * @param minAge
     * @param maxAge
     * @return
     */
    int queryStudentsCountWithinAge(int minAge, int maxAge);
}
