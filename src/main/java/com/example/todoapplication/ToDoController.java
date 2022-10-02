package com.example.todoapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ToDoController {
    @FXML
    public TableView table;
    @FXML
    public TextField inputTask;
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox priorityLevelPicker;
    @FXML
    public ComboBox minutePicker;
    @FXML
    public ComboBox hourPicker;
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;

    public void clickDeleteButton(ActionEvent actionEvent) {
    }

    public void clickAddButton(ActionEvent actionEvent) {
    }
}
