package com.example.todoapplication;

import Database.DB;
import Model.Cetli;
import Model.PriorityLevel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WorkShopController {

    @FXML
    private TextField inputTask;
    @FXML
    private ComboBox<PriorityLevel> priorityLevelPicker;
    @FXML
    private ComboBox<Integer> hourPicker;
    @FXML
    private ComboBox<Integer> minutePicker;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Cetli newCetli;


    public void initialize() {
        priorityLevelPicker.getItems().add(PriorityLevel.Red);
        priorityLevelPicker.getItems().add(PriorityLevel.Yellow);
        priorityLevelPicker.getItems().add(PriorityLevel.Green);

        for (int i = 0; i < 24; i++) {
            hourPicker.getItems().add(i);
        }

        for (int i = 0; i < 60; i++) {
            minutePicker.getItems().add(i);
        }
    }

    public void clickAddButton(ActionEvent event) throws IOException {
        newCetli = new Cetli(10,inputTask.getText(),
                priorityLevelPicker.getValue().toString(),
                LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourPicker.getValue(), minutePicker.getValue())).format(HelloApplication.formatter));

        HelloApplication.db.addContact(newCetli);
        TableController.Cetlik.add(newCetli);
    }
}
