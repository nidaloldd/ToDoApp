package com.example.todoapplication;

import Model.Cetli;
import Model.PriorityLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ToDoController {
    @FXML
    public TableView<Cetli> table;
    @FXML
    public TextField inputTask;
    @FXML
    public DatePicker datePicker;
    @FXML
    public ComboBox<PriorityLevel> priorityLevelPicker;
    @FXML
    public ComboBox<Integer> minutePicker;
    @FXML
    public ComboBox<Integer> hourPicker;
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;

    public static ObservableList<Cetli> Cetlik = FXCollections.observableArrayList();

    public void initialize() {
        setComboBoxValues();

        TableColumn taskCol = new TableColumn("Task");
        setTaskCol(taskCol);

        TableColumn priorityCol = new TableColumn("PriorityLevel");
        setPriorityColCol(priorityCol);

        TableColumn deadLineCol = new TableColumn("DeadLine");
        setDeadLineCol(deadLineCol);

        table.getColumns().addAll(taskCol, priorityCol, deadLineCol);
        //Cetlik.addAll(HelloApplication.db.getAllContacts());
        table.setItems(Cetlik);


    }

    public void setComboBoxValues(){
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

    public void setTaskCol(TableColumn taskCol){
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskCol.setCellValueFactory(new PropertyValueFactory<Cetli, String>("task"));
        taskCol.setMinWidth(200);
    }

    public void setPriorityColCol(TableColumn priorityCol){
        priorityCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<PriorityLevel>() {
            @Override
            public String toString(PriorityLevel priorityLevel) {
                return priorityLevel.toString();
            }

            @Override
            public PriorityLevel fromString(String s) {
                return PriorityLevel.valueOf(s);
            }
        }));
        priorityCol.setCellValueFactory(new PropertyValueFactory<Cetli, PriorityLevel>("priorityLevel"));
        priorityCol.setMinWidth(50);
    }

    public void setDeadLineCol(TableColumn deadLineCol){
        deadLineCol.setCellFactory(column -> {
            return new TableCell<Cetli, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(HelloApplication.formatter.format(item));

                    }
                }
            };
        });
        deadLineCol.setCellValueFactory(new PropertyValueFactory<Cetli, LocalDateTime>("deadLine"));
        deadLineCol.setMinWidth(120);
    }

    public void clickDeleteButton(ActionEvent actionEvent) {
        Cetli selectedTask = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedTask);
       // HelloApplication.db.removeContact(selectedTask);
    }

    public void clickAddButton(ActionEvent actionEvent) {
        Cetli newCetli = new Cetli(10,inputTask.getText(),
                priorityLevelPicker.getValue().toString(),
                LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourPicker.getValue(), minutePicker.getValue())).format(HelloApplication.formatter));

        //HelloApplication.db.addContact(newCetli);
        Cetlik.add(newCetli);
    }
}
