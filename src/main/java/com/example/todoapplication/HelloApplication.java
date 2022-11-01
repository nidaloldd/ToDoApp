package com.example.todoapplication;

import Database.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelloApplication extends Application {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    public static DB db = new DB();
    private double x,y = 0;

    public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Terv.fxml"));

        primaryStage.setScene(new Scene(root));

        this.stage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("TODO App");
        primaryStage.setResizable(true);

        primaryStage.show();
        ResizeHelper.addResizeListener(stage);


    }

    public static void main(String[] args) {
        launch();
    }
}