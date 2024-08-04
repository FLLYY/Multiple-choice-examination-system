package dao.impl;

import dao.examdao;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExamDAOImpl implements examdao {
    Connection conn;
    PreparedStatement psmt;
    ResultSet rs;
    final String DRIVER ="oracle.jdbc.driver.OracleDriver"; // Oracle JDBC 驱动程序类
    final String URL ="jdbc:oracle:thin:@localhost:1521:pengfei"; // Oracle 数据库连接字符串，包括主机、端口和 SID
    final String USER ="your_username"; // 替换为你的 Oracle 用户名
    final String PWD="your_password"; // 替换为你的 Oracle 密码
    @Override
    public Set<Question> questionRepository() {
        Set<Question> questions = new HashSet<>();
        try {
            // 加载驱动
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "select * from questions";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String optionsStr = rs.getString("options");
                String[] options = optionsStr.split(",");
                String answer = rs.getString("answer");
                Question question = new Question(title, options, answer);
                questions.add(question); // 将问题添加到集合中
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psmt != null) {
                    psmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return questions;
    }


    @Override
    public Map<String, Integer> getAllTeachers() {
        Map<String, Integer> teacher = new HashMap<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);
            String sql ="select * from teacher";
            psmt=conn.prepareStatement(sql);
            rs =psmt.executeQuery();
            while (rs.next()){
                String name = rs .getString("name");
                int password = rs .getInt("password");
                teacher.put(name, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs!=null){
                    rs.close();
                }
                if(psmt!=null)
                {
                    psmt.close();
                }
                if (conn!=null)
                {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return teacher;

    }


    @Override
    public Map<String, Integer> getAllstudent() {
        Map<String, Integer> student  = new HashMap<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);
            String sql ="select * from student";
            psmt=conn.prepareStatement(sql);
            rs =psmt.executeQuery();
            while (rs.next()){
                String name = rs .getString("name");
                int password = rs .getInt("password");
                student.put(name, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs!=null){
                    rs.close();
                }
                if(psmt!=null)
                {
                    psmt.close();
                }
                if (conn!=null)
                {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return student;

    }

    @Override
    public boolean addquestion(String title, String[] options, String answer) {
        // 检查答案是否在选项中
        boolean isAnswerInOptions = false;
        for (String option : options) {
            if (option.equals(answer)) {
                isAnswerInOptions = true;
                break;
            }
        }

        if (!isAnswerInOptions) {
            return false; // 如果答案不在选项中，则返回false,插入失败
        }

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);
            psmt = conn.prepareStatement(
                    "INSERT INTO questions (title, options, answer) VALUES ( ?, ?, ?)");

            psmt.setString(1, title);
            psmt.setString(2, String.join(",", options));
            psmt.setString(3, answer);

            int rowsAffected = psmt.executeUpdate();

            // 检查是否成功插入了数据
            if (rowsAffected > 0) {
                return true;  // 插入成功
            } else {
                return false;  // 插入失败
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (psmt != null) {
                    psmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean addstudent(String name, Integer password) {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);
            psmt = conn.prepareStatement("INSERT INTO student (name, password) VALUES (?, ?)");
            psmt.setString(1, name);
            psmt.setInt(2,  password);
            int rowsAffected =psmt.executeUpdate();
            // 检查是否成功插入了数据
            if (rowsAffected > 0) {
                return true;  // 插入成功
            } else {
                return false;  // 插入失败
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (psmt != null) {
                    psmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean addteacher(String name, Integer password) {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,USER,PWD);
            psmt = conn.prepareStatement("INSERT INTO teacher (name, password) VALUES (?, ?)");
            psmt.setString(1, name);
            psmt.setInt(2,  password);
            int rowsAffected =psmt.executeUpdate();
            // 检查是否成功插入了数据
            if (rowsAffected > 0) {
                return true;  // 插入成功
            } else {
                return false;  // 插入失败
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (psmt != null) {
                    psmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
