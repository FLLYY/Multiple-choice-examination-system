package com.example.exam1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        VBox examBox = new VBox(10);
        Image image1=new Image("file://E:/桌面/界面.jpg",800,500,false,false);
        BackgroundImage backgroundImg = new BackgroundImage(
                image1,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
         //创建一个背景对象
       Background background = new Background(backgroundImg);
         examBox.setBackground(background);
//        ImageView imageView=new ImageView(image1);
//        examBox.getChildren().add(imageView);
        Scene scene = new Scene(examBox, 320, 240);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}