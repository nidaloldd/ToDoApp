package com.example.todoapplication;

import  Database.DB;
import Model.Cetli;
import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HelloApplication extends Application {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    public static DB db = new DB();

    public static Stage stage = null;
    public static ObservableList<Cetli> Cetlik = FXCollections.observableArrayList();
    public Timeline notificationTimer=new Timeline(new KeyFrame(Duration.seconds(5), event -> notification()));
    public List<Cetli> noticationDone = new ArrayList<>();
    public List<Cetli> deadLineOver = new ArrayList<>();

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
        notificationTimer.play();
    }

    @FXML
    private void notification(){
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                ArrayList<Cetli> data = HelloApplication.db.getAllToDo();
                for (Cetli cetli:data) {
                    long delay = ChronoUnit.SECONDS.between(LocalDateTime.now(),cetli.getDeadLine());
                    long timeForTask = ChronoUnit.SECONDS.between(cetli.getDated(),cetli.getDeadLine());
                    if (timeForTask * 0.3 >= delay && delay>0 && !noticationDone.contains(cetli)) {
                        Platform.runLater(()->{
                            var alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Do it!");
                            alert.setContentText(cetli.getTask() + " have to be done soon!");
                            noticationDone.add(cetli);
                            alert.showAndWait();
                        });
                    }
                    if (delay<=0 && !deadLineOver.contains(cetli)) {
                        Platform.runLater(()->{
                            var alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Did you forget it?");
                            alert.setContentText(cetli.getTask() + " needed to be done by now!");
                            deadLineOver.add(cetli);
                            alert.showAndWait();
                        });
                    }
                }
            }
        };
        timer.schedule(timerTask,0,5*1000);
    }

    public static void main(String[] args) {
        launch();
    }
}