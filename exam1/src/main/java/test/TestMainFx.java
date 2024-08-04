package test;


import dao.impl.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class TestMainFx extends Application {

    private Stage primaryStage;
    private BorderPane layout;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("考试系统");

        layout = new BorderPane();
        createLoginScene();

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createLoginScene() {
        layout.getChildren().clear();
        Image image=new Image("file:绘图1.png",800,500,false,false);
        // 将背景图片放入背景中

        ImageView imageView =new ImageView(image);

        Pane loginBox = new Pane();
        loginBox.setStyle("-fx-alignment: center; -fx-padding: 20;");


        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");//去除边框样式
        Line line1 = new Line(0, 0, 240, 0);
        line1.setStyle("-fx-stroke: white;");
        VBox textlinebox1 = new VBox(usernameField,line1);
        usernameField.setPromptText("用户名");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("密码");
        passwordField.setStyle("-fx-background-color: transparent;-fx-text-fill: white;");//去除边框样式
        Line line2 = new Line(0, 0, 240, 0);
        line2.setStyle("-fx-stroke: white;");
        VBox textlinebox2 = new VBox(passwordField,line2);

        Button loginButton = new Button("登录");
        loginButton.setPrefWidth(70); // 设置按钮宽度为 70
        loginButton.setPrefHeight(20);
        loginButton.setOnAction(e -> handleLogin(usernameField,passwordField));

        textlinebox1.setLayoutX(25);
        textlinebox1.setLayoutY(200);

        textlinebox2.setLayoutX(25);
        textlinebox2.setLayoutY(240);

        loginButton.setLayoutX(120);
        loginButton.setLayoutY(280);

        loginBox.getChildren().addAll( imageView,textlinebox1, textlinebox2, loginButton);

        layout.setCenter(loginBox);
    }


    private void handleLogin(TextField usernameField, PasswordField passwordField) {
        String name = usernameField.getText();
        String passwordStr = passwordField.getText();

        // 解析密码为整数
        Integer password = Integer.parseInt(passwordStr);

        ExamMachine examMachine =new ExamMachine(layout);
        Teacher teacher = new Teacher(name,password);
        Student student = new Student(name,password);

        if (student.loginStudentCheck(name, password)) {
            examMachine.createExamScene();
        }

        else if(teacher.loginTeacherCheck(name,password)){
            teacher.creatFunctionScene(layout);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("登录失败");
            alert.setHeaderText(null);
            alert.setContentText("用户名或密码不正确");
            alert.showAndWait();
        }
    }
}
