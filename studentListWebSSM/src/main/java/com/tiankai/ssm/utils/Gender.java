package com.tiankai.ssm.utils;

/**
 * 性别的枚举类
 *
 * @author: xutiankai
 * @date: 7/30/2021 2:53 PM
 */
public enum Gender {
    MALE("男"),
    FEMALE("女");

    private final String genderType;

    private Gender(String genderType) {
        this.genderType = genderType;
    }

    public String getGenderType() {
        return genderType;
    }

    @Override
    public String toString() {
        return "Gender{" +
                "genderType='" + genderType + '\'' +
                '}';
    }
}
