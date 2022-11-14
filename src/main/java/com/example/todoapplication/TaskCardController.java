package com.example.todoapplication;

import Model.Cetli;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskCardController{
    @FXML
    public Label taskName;
    @FXML
    private Label dateLabel;
    @FXML
    private ImageView priorityImage;
    @FXML
    private Button deleteButton;

    public String priorityLevel;

    public void setData(Cetli cetli) {
        taskName.setText(cetli.getTask());
        dateLabel.setText(String.valueOf(cetli.getDeadLine()).replace('T',' '));

        Image star = new Image(getClass().getResourceAsStream("/img/stars/star.png"));
        switch (cetli.getPriorityLevel()){
            case Red -> star = new Image(getClass().getResourceAsStream("/img/stars/starRed.png"));
            case Yellow -> star = new Image(getClass().getResourceAsStream("/img/stars/starYellow.png"));
            case Green -> star = new Image(getClass().getResourceAsStream("/img/stars/starGreen.png"));

        }
        priorityLevel = String.valueOf(cetli.getPriorityLevel());
        priorityImage.setImage(star);
    }

    public void clickDeleteButton(ActionEvent event) throws IOException {
        CreateTaskController createTaskController = new CreateTaskController();
        createTaskController.removeData(taskName.getText());
        deleteButton.setStyle("-fx-background-color: red");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void clickExpandButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        Parent root = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.taskLabel.setText(taskName.getText());
        mainController.priorityLabel.setText(priorityLevel);
        mainController.deadlineLabel.setText(dateLabel.getText());
        mainController.setExpandedTaskPaneValues();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
