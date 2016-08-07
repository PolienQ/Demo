package com.pnas.demo.entity.db;

/***********
 * @author pans
 * @date 2016/8/5
 * @describ
 */
public class StudentInfo {

    public String name;
    public int age;
    public String sex;
    public int height;
    public String birthday;

    public StudentInfo() {
    }

    public StudentInfo(String name, String sex, int age, int height, String birthday) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.birthday = birthday;
    }
}
