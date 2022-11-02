package com.example.todoapplication;

import Model.Cetli;
import Model.PriorityLevel;
import Model.Tree;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.example.todoapplication.HelloApplication.db;

public class GetInputCardController {
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

    public void setComboBoxValues(){
        priorityChoice.getItems().add(PriorityLevel.Red);
        priorityChoice.getItems().add(PriorityLevel.Yellow);
        priorityChoice.getItems().add(PriorityLevel.Green);

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


        db.addContact(newCetli);
        HelloApplication.Cetlik.add(newCetli);

    }
}
