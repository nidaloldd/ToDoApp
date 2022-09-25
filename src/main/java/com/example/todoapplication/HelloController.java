package com.example.todoapplication;

import Model.Cetli;
import Model.PriorityLevel;
import Model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.time.LocalDateTime;

public class HelloController {

    @FXML
    private TableView table;
    @FXML
    private Button addNewButton;
    @FXML
    private TextField inputTask;
    @FXML
    private TextField inputPriorityLevel;
    @FXML
    private TextField inputDeadLine;

    ObservableList<Task> Cetlik = FXCollections.observableArrayList();

    public void initialize(){
        TableColumn taskCol = new TableColumn("Task");
        taskCol.setMinWidth(350);
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskCol.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));

        TableColumn priorityCol = new TableColumn("PriorityLevel");
        priorityCol.setMinWidth(100);
        priorityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        priorityCol.setCellValueFactory(new PropertyValueFactory<Task, PriorityLevel>("priorityLevel"));

        TableColumn deadLineCol = new TableColumn("DeadLine");
        deadLineCol.setMinWidth(150);
        deadLineCol.setCellFactory(TextFieldTableCell.forTableColumn());
        deadLineCol.setCellValueFactory(new PropertyValueFactory<Task, LocalDateTime>("deadLine"));

        table.getColumns().addAll(taskCol, priorityCol, deadLineCol);
        table.setItems(Cetlik);
    }

    public void clickAddNewButton(ActionEvent event) {
        Task newCetli = new Task(inputTask.getText(), inputPriorityLevel.getText(), inputDeadLine.getText());
        Cetlik.add(newCetli);
        inputTask.clear();
        inputPriorityLevel.clear();
        inputDeadLine.clear();
    }
}