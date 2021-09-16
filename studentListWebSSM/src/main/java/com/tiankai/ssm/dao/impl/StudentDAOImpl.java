package com.tiankai.ssm.dao.impl;

import com.tiankai.ssm.bean.Student;
import com.tiankai.ssm.dao.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实现了StudentDAO中定义的方法
 * Spring自动注入JdbcTemplate，移除对BaseDao的依赖，不再需要继承BaseDao
 *
 * @author: xutiankai
 * @date: 7/30/2021 3:45 PM
 */
@Deprecated
@Repository
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addStudent(Student student) {
        String sql = "INSERT INTO student_list(`stu_id`, `name`, `gender`, `age`) VALUES(?,?,?,?);";
//        return update(sql, student.getStuId(), student.getName(), student.getGender(), student.getAge());
        return jdbcTemplate.update(sql, student.getStuId(), student.getName(), student.getGender(), student.getAge());
    }

    @Override
    public int deleteStudentById(int stuId) {
        String sql = "DELETE FROM student_list WHERE `stu_id`= ?; ";
//        return update(sql, stuId);
        return jdbcTemplate.update(sql, stuId);
    }

    @Override
    public int updateStudentInfoById(Student student) {
        String sql = "UPDATE student_list SET `name`=?, `gender`=?, `age`=? WHERE `stu_id`=?;";
        return jdbcTemplate.update(sql, student.getName(), student.getGender(), student.getAge(), student.getStuId());
    }

    @Override
    public List<Student> showAllStudents() {
        String sql = "SELECT stu_id stuId, `name` name,gender gender, age age FROM student_list";
//        return queryForList(sql, Student.class);
        return jdbcTemplate.queryForList(sql, Student.class);
    }

    @Override
    public List<Student> queryPageItemsWithinRange(int begin, int pageSize) {
        String sql = "SELECT stu_id stuId, `name` name,gender gender, age age FROM student_list LIMIT ?, ?; ";
//        return queryForList(sql, Student.class, begin, pageSize);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class), begin, pageSize);
    }

    @Override
    public List<Student> queryPageItemsWithinRangeAndAge(int begin, int pageSize, int minAge, int maxAge) {
        // sql语句换行的时候注意要有空格
        String sql = "SELECT stu_id stuId, `name` name,gender gender, age age FROM student_list " +
                "WHERE `age` >= ? AND `age` <= ? LIMIT ?, ?; ";
//        return queryForList(sql, Student.class, minAge, maxAge, begin, pageSize);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class), minAge, maxAge, begin, pageSize);
    }

    @Override
    public Student showStudentById(int stuId) {
        String sql = "SELECT stu_id stuId, `name` name,gender gender, age age FROM student_list WHERE `stu_id`=?; ";
//        return queryForOne(sql, Student.class, stuId);
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class), stuId);
    }

    @Override
    public int queryStudentsCount() {
        String sql = "SELECT COUNT(*) FROM student_list;";

//        Long count = (Long) queryForSingleValue(sql);
//        Long count = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Long.class));
//        assert count != null;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int queryStudentsCountWithinAge(int minAge, int maxAge) {
        String sql = "SELECT COUNT(*) FROM student_list WHERE `age`>= ? AND `age` <= ? ; ";
//        Long count = (Long) queryForSingleValue(sql, minAge, maxAge);

//        return count.intValue();

//        Long count = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Long.class), minAge, maxAge);
//        assert count != null;
//        return count.intValue();
        return jdbcTemplate.queryForObject(sql, Integer.class, minAge, maxAge);
    }


}
