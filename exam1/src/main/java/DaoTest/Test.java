package DaoTest;

import dao.examdao;
import dao.impl.ExamDAOImpl;

import java.util.Map;

public class Test {
    public static void main(String[] args) {
        examdao examDAO = new ExamDAOImpl();

        // 获取所有学生信息并输出
        System.out.println("学生账号密码信息：");
        Map<String, Integer> students = examDAO.getAllstudent();
        for (Map.Entry<String, Integer> entry : students.entrySet()) {
            System.out.println("姓名：" + entry.getKey() + ", 密码：" + entry.getValue());
        }

        // 获取所有教师信息并输出
        System.out.println("\n教师账号密码信息：");
        Map<String, Integer> teachers = examDAO.getAllTeachers();
        for (Map.Entry<String, Integer> entry : teachers.entrySet()) {
            System.out.println("姓名：" + entry.getKey() + ", 密码：" + entry.getValue());
        }

    }
}
