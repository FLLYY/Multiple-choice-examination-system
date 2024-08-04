package dao.impl;

import dao.examdao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import java.io.File;
import java.util.*;

public class Teacher
{
    private static Map<String, Integer> Box = new HashMap<>();//用户信息


    private String name;
    private Integer password;

    public Teacher(String name, Integer password)
    {
        this.name = name;
        this.password = password;
        examdao em = new ExamDAOImpl();
        Box = em.getAllTeachers();
    }

    //老师的登录校验
    public boolean loginTeacherCheck(String name, Integer pwd)
    {
        if (this.Box.get(name) != null && this.Box.get(name).equals(pwd))
        {
            return true;
        } else
            return false;
    }

    //往题库里添加新的题目
    public boolean addQuestionToFile(String title, String[] options, String answer)
    {

        examdao em = new ExamDAOImpl();
        return em.addquestion(title, options, answer);

    }

    //批阅卷子，打分数
    public static float checkPaper(ArrayList<Question> paper, String[] answer)
    {
        System.out.println("系统正在提交答案···");
        System.out.println("老师们正在批阅，请耐心等候····");

        float oneQueScore = 100F / paper.size();//一道题的分数
        float finalScore = 0;
        for (int i = 0; i < paper.size(); i++)
        {
            String trueAnswer = paper.get(i).getAnswer().substring(0, 1);//把选项提取出来，charAt也可以
            if (trueAnswer.equalsIgnoreCase(answer[i]))
            {
                finalScore += oneQueScore;
            }
        }
        return finalScore;
    }

    public void creatFunctionScene(BorderPane layout)
    {
        layout.getChildren().clear();

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER); // 设置垂直居中
        vbox.setPadding(new Insets(40));
        File file1 = new File("背景(1).jpg");
        Image Functionimage=new Image(file1.toURI().toString(),800,500,false,false);
        BackgroundImage backgroundImg = new BackgroundImage(
                Functionimage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        // 创建一个背景对象
        Background Functionbackground = new Background(backgroundImg);

        Button addQuestionButton = new Button("1. 添加题目");
        addQuestionButton.setStyle("-fx-font-size: 16pt;"); // 设置按钮字体大小
        addQuestionButton.setPrefWidth(180);
        addQuestionButton.setOnAction(e ->
        {
            addQuestionsScene(layout); // 执行添加题目操作
        });

        Button addTeacherButton = new Button("2. 添加教师信息");
        addTeacherButton.setStyle("-fx-font-size: 16pt;"); // 设置按钮字体大小
        addTeacherButton.setOnAction(e ->
        {
            addTeacherScene(layout); // 执行添加教师信息操作
        });

        Button addStudentButton = new Button("3. 添加学生信息");
        addStudentButton.setStyle("-fx-font-size: 16pt;"); // 设置按钮字体大小
        addStudentButton.setOnAction(e ->
        {
            addStudentScene(layout); // 执行添加学生信息操作
        });

        vbox.getChildren().addAll(addQuestionButton, addTeacherButton, addStudentButton);
        vbox.setBackground(Functionbackground);

        layout.setCenter(vbox);
    }

    private void addStudentScene(BorderPane layout) {
        Pane loginBox = new Pane();
        File file2 = new File("背景(1).jpg");
        Image studentimage = new Image(file2.toURI().toString(), 800, 500, false, false);
        BackgroundImage backgroundImg = new BackgroundImage(
                studentimage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        // 创建一个背景对象
        Background studentbackground = new Background(backgroundImg);

        Label nameLabel = new Label("请输入学生用户名:");
        nameLabel.setStyle("-fx-font-size: 14px;-fx-text-fill: white;");
        TextField nameField = new TextField();
        nameField.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        Line line1 = new Line(0, 0, 220, 0);
        line1.setStyle("-fx-stroke: white;");
        VBox nameFieldbox = new VBox(nameField, line1);

        Label passwordLabel = new Label("请输入学生密码:");
        passwordLabel.setStyle("-fx-font-size: 14px;-fx-text-fill: white;");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-color: transparent;-fx-text-fill: white;");//去除边框样式
        Line line2 = new Line(0, 0, 220, 0);
        line2.setStyle("-fx-stroke: white;");
        VBox passwordFieldbox = new VBox(passwordField, line2);

        Label studentIdLabel = new Label("请输入学生学号:");
        studentIdLabel.setStyle("-fx-font-size: 14px;-fx-text-fill: white;");
        TextField studentIdField = new TextField();
        studentIdField.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        Line line3 = new Line(0, 0, 220, 0);
        line3.setStyle("-fx-stroke: white;");
        VBox studentIdFieldbox = new VBox(studentIdField, line3);

        Label emailLabel = new Label("请输入学生邮箱:");
        emailLabel.setStyle("-fx-font-size: 14px;-fx-text-fill: white;");
        TextField emailField = new TextField();
        emailField.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        Line line4 = new Line(0, 0, 220, 0);
        line4.setStyle("-fx-stroke: white;");
        VBox emailFieldbox = new VBox(emailField, line4);

        nameLabel.setLayoutX(5);
        nameLabel.setLayoutY(200);
        nameFieldbox.setLayoutX(120);
        nameFieldbox.setLayoutY(200);

        passwordLabel.setLayoutX(18);
        passwordLabel.setLayoutY(240);
        passwordFieldbox.setLayoutX(120);
        passwordFieldbox.setLayoutY(240);

        studentIdLabel.setLayoutX(18);
        studentIdLabel.setLayoutY(280);
        studentIdFieldbox.setLayoutX(120);
        studentIdFieldbox.setLayoutY(280);

        emailLabel.setLayoutX(18);
        emailLabel.setLayoutY(320);
        emailFieldbox.setLayoutX(120);
        emailFieldbox.setLayoutY(320);

        loginBox.getChildren().addAll(nameLabel, nameFieldbox);
        loginBox.getChildren().addAll(passwordLabel, passwordFieldbox);
        loginBox.getChildren().addAll(studentIdLabel, studentIdFieldbox);
        loginBox.getChildren().addAll(emailLabel, emailFieldbox);
        loginBox.setBackground(studentbackground);

        Button submitButton = new Button("提交");
        // 添加返回按钮
        Button backButton = new Button("返回");
        backButton.setOnAction(e -> creatFunctionScene(layout));
        submitButton.setLayoutX(140);
        submitButton.setLayoutY(360);

        backButton.setLayoutX(280);
        backButton.setLayoutY(360);

        loginBox.getChildren().addAll(submitButton, backButton);

        submitButton.setOnAction(event ->
        {
            String name = nameField.getText();
            Integer password = Integer.valueOf(passwordField.getText());
            String studentId = studentIdField.getText();
            String email = emailField.getText();
            examdao em = new ExamDAOImpl();
            boolean re = em.addstudent(name, password);
            if (re)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("成功");
                alert.setHeaderText(null);
                alert.setContentText("添加成功！");
                alert.showAndWait();
            }
        });
        layout.setCenter(loginBox);
    }


    private void addTeacherScene(BorderPane layout)
    {

        Pane loginBox = new Pane();

        File file3 = new File("背景(1).jpg");
        Image teacherimage=new Image(file3.toURI().toString(),800,500,false,false);
        BackgroundImage backgroundImg = new BackgroundImage(
                teacherimage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        // 创建一个背景对象
        Background teacherbackground = new Background(backgroundImg);

        Label nameLabel = new Label("请输入老师用户名:");
        nameLabel.setStyle("-fx-font-size: 14px;-fx-text-fill: white;");
        TextField nameField = new TextField();
        nameField.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        Line line1 = new Line(0, 0, 220, 0);
        line1.setStyle("-fx-stroke: white;");
        VBox nameFieldbox = new VBox(nameField,line1);

        Label passwordLabel = new Label("请输入老师密码:");
        passwordLabel.setStyle("-fx-font-size: 14px;-fx-text-fill: white");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-color: transparent;-fx-text-fill: white;");//去除边框样式
        Line line2 = new Line(0, 0, 220, 0);
        line2.setStyle("-fx-stroke: white;");
        VBox passwordFieldbox = new VBox(passwordField,line2);

        Button submitButton = new Button("提交");
        nameLabel.setLayoutX(5);
        nameLabel.setLayoutY(200);
        nameFieldbox.setLayoutX(120);
        nameFieldbox.setLayoutY(200);

        passwordLabel.setLayoutX(18);
        passwordLabel.setLayoutY(240);
        passwordFieldbox.setLayoutX(120);
        passwordFieldbox.setLayoutY(240);


        loginBox.getChildren().addAll(nameLabel, nameFieldbox);
        loginBox.getChildren().addAll(passwordLabel,passwordFieldbox);

        // 添加返回按钮
        Button backButton =new Button("返回");
        backButton.setOnAction(e -> creatFunctionScene(layout));

        submitButton.setLayoutX(140);
        submitButton.setLayoutY(280);

        backButton.setLayoutX(280);
        backButton.setLayoutY(280);

        loginBox.getChildren().addAll(submitButton, backButton);
        loginBox.setBackground(teacherbackground);

        submitButton.setOnAction(event ->
        {
            String name = nameField.getText();
            Integer password = Integer.valueOf(passwordField.getText());
            examdao em = new ExamDAOImpl();
            boolean re = em.addteacher(name, password);
            if (re)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("成功");
                alert.setHeaderText(null);
                alert.setContentText("添加成功！");
                alert.showAndWait();
            }
        });
        layout.setCenter(loginBox);
    }

    private void addQuestionsScene(BorderPane layout)
    {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        File file3 = new File("考试背景.jpg");
        Image questionimage=new Image(file3.toURI().toString(),800,500,false,false);
        BackgroundImage backgroundImg1 = new BackgroundImage(
                questionimage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        // 创建一个背景对象
        Background questionbackground = new Background(backgroundImg1);
        for (int i = 0; i < 1; i++) {
            Label titleLabel = new Label("请输入题目的信息：");
            titleLabel.setStyle("-fx-font-size: 16px;-fx-text-fill: white");
            TextField titleField = new TextField();
            Label optionsLabel = new Label("请输入选项：");
            optionsLabel.setStyle("-fx-font-size: 16px;-fx-text-fill: white");
            TextField[] optionsFields = new TextField[4];
            for (int j = 0; j < optionsFields.length; j++) {
                optionsFields[j] = new TextField();
            }
            Label answerLabel = new Label("请输入答案：");
            answerLabel.setStyle("-fx-font-size: 16px;-fx-text-fill: white");
            TextField answerField = new TextField();
            Button submitButton = new Button("提交");

            // 添加返回按钮
            Button backButton =new Button("返回");
            backButton.setOnAction(e -> creatFunctionScene(layout));

            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(submitButton, backButton);

            vbox.getChildren().addAll(titleLabel, titleField, optionsLabel);
            for (int j = 0; j < optionsFields.length; j++) {
                vbox.getChildren().add(optionsFields[j]);
            }
            vbox.setBackground(questionbackground);
            vbox.getChildren().addAll(answerLabel, answerField,buttonBox);

            submitButton.setOnAction(e -> {
                String title = titleField.getText();
                String[] options = new String[4];
                for (int j = 0; j < options.length; j++) {
                    options[j] = optionsFields[j].getText();
                }
                String answer = answerField.getText();

                boolean re =addQuestionToFile(title, options, answer);
                if (re)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("成功");
                    alert.setHeaderText(null);
                    alert.setContentText("添加成功！");
                    alert.showAndWait();
                }
            });
        }
        layout.setCenter(vbox);
    }

}
