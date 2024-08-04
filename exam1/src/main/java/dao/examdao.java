package dao;


import java.util.Map;
import java.util.Set;
import dao.impl.Question;

public interface examdao {
    //查询考试列表
    Set<Question> questionRepository();
    //查询教师信息
     Map<String, Integer> getAllTeachers();
    //查询学生信息
    Map<String, Integer> getAllstudent();
    //填加题目
    boolean addquestion(String title,String[] option,String answer);
    //添加学生信息
    boolean addstudent(String name,Integer password);
    //添加教师信息
    boolean addteacher(String name,Integer password);



}
