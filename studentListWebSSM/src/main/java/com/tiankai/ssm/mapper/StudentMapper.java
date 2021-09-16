package com.tiankai.ssm.mapper;

import com.tiankai.ssm.bean.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: xutiankai
 * @date: 9/2/2021 7:51 PM
 */
@Repository
public interface StudentMapper {
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
    int deleteStudentById(@Param("stuId") int stuId);

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
     * @deprecated 计划改成用pageHelper实现
     */
    @Deprecated
    List<Student> queryPageItemsWithinRange(@Param("begin") int begin, @Param("pageSize") int pageSize);
    // TODO: 9/2/2021 改成用pageHelper实现

    /**
     * 查询分页上显示的数据。从begin开始，共pageSize个记录，查询年龄在minAge（含）和maxAge（含）之间的学生信息。
     *
     * @param begin
     * @param pageSize
     * @param minAge
     * @param maxAge
     * @return
     */
    @Deprecated
    List<Student> queryPageItemsWithinRangeAndAge(@Param("begin") int begin,
                                                  @Param("pageSize") int pageSize,
                                                  @Param("minAge") int minAge,
                                                  @Param("maxAge") int maxAge);
    // TODO: 9/2/2021 改成用pageHelper实现

    /**
     * 返回指定学号的学生的信息
     *
     * @return
     */
    Student showStudentById(@Param("stuId") int stuId);

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
    int queryStudentsCountWithinAge(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

    /**
     * 批量删除选中的学生
     *
     * @param stuIds
     */
    void deleteChosenStudents(@Param("stuIds") Integer[] stuIds);
}
