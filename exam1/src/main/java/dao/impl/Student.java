package dao.impl;

import dao.examdao;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private static final java.util.UUID UUID = java.util.UUID.randomUUID();;
    private String name;
    private Integer password;
    private String examNum;//考号，没有登录系统则为null
    private static Map<String,Integer> userBox = new HashMap<>();//用户信息



    public Student(String name, Integer password) {
        this.name = name;
        this.password = password;
        loginUser();
    }

    void loginUser() {
        examdao em =new ExamDAOImpl();
        userBox =em.getAllstudent();
    }

    public String getName()
    {
        return name;
    }

    public boolean loginStudentCheck(String name, Integer password)
    {
        boolean checkResult = this.userBox.get(name)!=null && this.userBox.get(name).equals(password);
        if (checkResult){
            System.out.println("登录成功！\n请考生"+this.getName()+"开始作答.\n不要作弊，一旦发现严肃处理！！！");
            return true;
        }else {
            System.out.println("学生用户名或密码错误");
            return false;
        }
    }
    //get以及set方法····

}
