package com.tiankai.ssm.bean;

import com.tiankai.ssm.utils.Gender;

/**
 * @author: xutiankai
 * @date: 7/30/2021 2:52 PM
 */
public class Student {
    private Integer stuId;
    private String name;
    private Gender gender;
    private Integer age;

    public Student() {

    }

    public Student(String name, Gender gender, Integer age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Student(Integer stuId, String name, Gender gender, Integer age) {
        this.stuId = stuId;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Student(Integer stuId, String name, String gender, Integer age) {
        this.stuId = stuId;
        this.name = name;
        this.age = age;

        // 注意！gender属性不直接赋值，以字符串String处理
        if ("男".equals(gender)) {
            this.gender = Gender.MALE;
        } else if ("女".equals(gender)) {
            this.gender = Gender.FEMALE;
        } else {
            throw new RuntimeException("setGender()参数有误！");
        }
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 注意！gender属性的get/set方法不使用默认的，以字符串String处理
    public String getGender() {
        return gender.getGenderType();
    }

    public void setGender(String gender) {
        // 传的是“男”/“女”，和枚举类Gender的字面量不同，不能直接中Gender.valueOf()方法将字符串转换成枚举类。
//        this.gender=Gender.valueOf(gender);
        if ("男".equals(gender)) {
            this.gender = Gender.MALE;
        } else if ("女".equals(gender)) {
            this.gender = Gender.FEMALE;
        } else {
            throw new RuntimeException("setGender()参数有误！");
        }
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId=" + stuId +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}
