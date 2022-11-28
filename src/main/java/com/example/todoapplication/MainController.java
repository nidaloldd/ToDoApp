package com.example.todoapplication;

import Model.Cetli;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainController {
    @FXML
    AnchorPane rootPane;
    @FXML
    AnchorPane expandedTaskPane;
    @FXML
    HBox topBar;
    @FXML
    TilePane tilePane;
    @FXML
    AnchorPane cetliMuhelyPane;
    @FXML
    Button slideInButton;
    @FXML
    Button slideOutButton;
    @FXML
    TilePane inputCetliPane;
    @FXML
    Label taskLabel;
    @FXML
    Label priorityLabel;
    @FXML
    Label deadlineLabel;
    @FXML
    TilePane mainTaskTilePane;
    @FXML
    TilePane subTasksTilePane;

    private double xOffset=0,yOffset=0;
    CreateTaskController createTaskController;


    public void initialize(){
        //makeDraggable(HelloApplication.stage, rootPane);

        makeStageDragable();

        cetliMuhelyPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            cetliMuhelyPane.setVisible(false);
            slideIn();

        });

        createTasks();

        cetliMuhelyPane.setTranslateX(-160);
        expandedTaskPane.setVisible(false);
        slideOutButton.setVisible(false);
        slideInButton.setVisible(true);

        setInputTaskPane();

    }

    public void setExpandedTaskPaneValues(){
        ArrayList<Cetli> data = HelloApplication.db.getAllToDo();
        expandedTaskPane.setVisible(true);
        for (Cetli c : data) {
            if (c.getParent().equals(taskLabel.getText())){
                subTasksTilePane.getChildren().add(createCetliFxml(c));
            }
        }
        for (Cetli c : data) {
            if (c.getTask().equals(taskLabel.getText())){
                for (Cetli i : data){
                    if (c.getParent().equals(i.getTask())){
                        mainTaskTilePane.getChildren().add(createCetliFxml(i));
                    }
                }
            }
        }
    }
    public void checkDeletedRoot(){
        ArrayList<Cetli> data = HelloApplication.db.getAllToDo();
        for (Cetli c : data) {
            int j = 0;
            for (Cetli i : data){
                if (!c.getParent().equals(i.getTask()) && !c.getParent().equals("root")){
                    j++;
                }
            }
            if (j == data.size()){
                HelloApplication.db.removeTask(c);
            }
        }
    }
    public void setInputTaskPane(){
        inputCetliPane.getChildren().clear();
        Node inputCetli = createInputCetliFxml();
        inputCetliPane.getChildren().add(inputCetli);
    }
    public void createTasks(){
        ArrayList<Cetli> data1 = HelloApplication.db.getAllToDo();
        for (int i = 0; i < data1.size(); i++) {
            checkDeletedRoot();
        }
        ArrayList<Cetli> data = HelloApplication.db.getAllToDo();
        tilePane.getChildren().clear();
        for (Cetli c : data) {
            tilePane.getChildren().add(createCetliFxml(c));
        }
    }
    private Node createCetliFxml(Cetli cetli){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/TaskCard.fxml"));
            Node node = fxmlLoader.load();

            TaskCardController taskCArdController = fxmlLoader.getController();
            taskCArdController.setData(cetli);

            return  node;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pane();
    }
    private Node createInputCetliFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/CreateTask.fxml"));
            Node node = fxmlLoader.load();
            createTaskController = fxmlLoader.getController();
            createTaskController.setComboBoxValues();

            return  node;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pane();
    }
    @FXML
    void slideOut() {
        cetliMuhelyPane.setVisible(true);
        slideOutButton.setVisible(false);
        slideInButton.setVisible(true);

        System.out.println("slideOut");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(cetliMuhelyPane);

        slide.setToX(0);
        slide.play();
        cetliMuhelyPane.setTranslateX(cetliMuhelyPane.getWidth());
        slide.setOnFinished((ActionEvent e) -> {

        });
    }

    @FXML
    void slideIn(){

        slideOutButton.setVisible(true);
        slideInButton.setVisible(false);
        System.out.println("slideIn");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(cetliMuhelyPane);

        slide.setToX(cetliMuhelyPane.getWidth());
        slide.play();
        cetliMuhelyPane.setTranslateX(0);
        slide.setOnFinished((ActionEvent e) -> {

        });
    }
    @FXML
    private void minimizeStage(){
        HelloApplication.stage.setIconified(true);
    }
    @FXML
    public void closeApp() {
        HelloApplication.stage.close();
//      System.exit(0);
    }

    public void clickAddButton() {
        if (createTaskController.getData()){
            createTaskController.setComboBoxValues();
            createTasks();
            setInputTaskPane();
        }
    }

    public void clickExitButton() {
        expandedTaskPane.setVisible(false);
    }
    private void makeStageDragable(){
        topBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = HelloApplication.stage.getX() - event.getScreenX();
                yOffset = HelloApplication.stage.getY() - event.getScreenY();
            }
        });
        topBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HelloApplication.stage.setX(event.getScreenX() + xOffset);
                HelloApplication.stage.setY(event.getScreenY() + yOffset);
                HelloApplication.stage.setOpacity(0.6f);
            }
        });

        topBar.setOnDragDone((event)-> {
            HelloApplication.stage.setOpacity(1.0f);
        });

        topBar.setOnMouseReleased((event)-> {
            HelloApplication.stage.setOpacity(1.0f);
        });
    }

    static class Delta { double x, y; }
    /** makes a stage draggable using a given node */
    public static void makeDraggable(final Stage stage, final Node byNode) {
        final Delta dragDelta = new Delta();
        byNode.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = stage.getY() - mouseEvent.getScreenY();
                byNode.setCursor(Cursor.MOVE);
            }
        });
        byNode.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                byNode.setCursor(Cursor.HAND);
            }
        });
        byNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });
        byNode.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    byNode.setCursor(Cursor.HAND);
                }
            }
        });
        byNode.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    byNode.setCursor(Cursor.DEFAULT);
                }
            }
        });
    }
}
