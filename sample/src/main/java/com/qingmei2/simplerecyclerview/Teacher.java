package com.qingmei2.simplerecyclerview;

public class Teacher {

    public String name;
    public String age;

    public Teacher(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
