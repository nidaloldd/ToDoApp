package com.example.todoapplication;

import  Database.DB;
import Model.Cetli;
import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.time.format.DateTimeFormatter;


public class HelloApplication extends Application {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    public static DB db = new DB();

    public static Stage stage = null;
    public static ObservableList<Cetli> Cetlik = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));

        primaryStage.setScene(new Scene(root));

        this.stage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("TODO App");
        primaryStage.setResizable(true);
//      not needed with trayToBackHelper
//      primaryStage.show();
        ApplicationWidgetizer.trayToBackHelper(primaryStage);
        ResizeHelper.addResizeListener(stage);
//        ApplicationWidgetizer.Widgetizer(stage);
        ApplicationWidgetizer.createTrayIcon(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}