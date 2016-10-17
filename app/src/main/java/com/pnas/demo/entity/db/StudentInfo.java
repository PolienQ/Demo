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
    public int score;
    public String birthday;

    public StudentInfo() {
    }

    public StudentInfo(String name, String sex, int age, int score, String birthday) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.score = score;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "姓名:" + name + "\t性别:" + sex + "\t 年龄:" + age + "\t 分数:" + score;
    }
}
