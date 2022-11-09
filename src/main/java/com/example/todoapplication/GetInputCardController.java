package com.example.todoapplication;

import Model.Cetli;
import Model.PriorityLevel;
import Model.Tree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class GetInputCardController implements Initializable {
    @FXML
    public ImageView priorityImage;
    @FXML
    private TextField mainTaskText;
    @FXML
    private TreeView taskTreeView;
    @FXML
    private ChoiceBox<PriorityLevel> priorityChoice;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Integer> hourBox;
    @FXML
    private ComboBox<Integer> minuteBox;
    @FXML
    private Button rootAddButton, rootRemoveButton;
    @FXML
    private VBox rootVbox;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        setComboBoxValues();
        rootAddButton.setOnMouseClicked(e -> {
            createNewSubTask(rootVbox);
        });


    }
    public void setComboBoxValues(){
        priorityChoice.getItems().add(PriorityLevel.Red);
        priorityChoice.getItems().add(PriorityLevel.Yellow);
        priorityChoice.getItems().add(PriorityLevel.Green);

        priorityChoice.setOnAction(e -> {
            Image star = new Image(getClass().getResourceAsStream("/img/stars/star.png"));
            switch (priorityChoice.getValue()){
                case Red -> star = new Image(getClass().getResourceAsStream("/img/stars/starRed.png"));
                case Yellow -> star = new Image(getClass().getResourceAsStream("/img/stars/starYellow.png"));
                case Green -> star = new Image(getClass().getResourceAsStream("/img/stars/starGreen.png"));
            }
            priorityImage.setImage(star);
        });

        for (int i = 0; i < 24; i++) {
            hourBox.getItems().add(i);
        }
        for (int i = 0; i < 60; i++) {
            minuteBox.getItems().add(i);
        }
    }
    public void getData(){

        Cetli newCetli = new Cetli(10,mainTaskText.getText(),
                priorityChoice.getValue().toString(),
                LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourBox.getValue(), minuteBox.getValue())).format(HelloApplication.formatter),
                "TTAskTree");

        //db.addContact(newCetli);
        HelloApplication.Cetlik.add(newCetli);
    }

    public void createNewSubTask(VBox parent){

        HBox nodeBox = new HBox();
        TextField task = new TextField();
        task.setPromptText("SubTask");
        task.setAlignment(Pos.CENTER);
        task.setPrefWidth(150);
        task.setPrefHeight(25);
        Button addButton = new Button("+");
        Button removeButton = new Button("-");
        VBox vBoxContainer = new VBox();
        nodeBox.getChildren().addAll(task,addButton,removeButton,vBoxContainer);


        addButton.setPrefWidth(30);
        addButton.setPrefHeight(10);
        removeButton.setPrefWidth(30);
        removeButton.setPrefHeight(10);



        parent.getChildren().add(nodeBox);

        //hBox.setMargin(hbox, new Insets(30));
        addButton.setOnMouseClicked(e -> {
            createNewSubTask(vBoxContainer);

            removeButton.setOnMouseClicked(ee -> {
                removeSubTask(vBoxContainer);
            });
        });


    }
    public void removeSubTask(VBox parent){
        System.out.print("removeSubTask");
        parent.getChildren().clear();
    }


}
