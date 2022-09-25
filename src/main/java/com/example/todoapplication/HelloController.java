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
import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelloController {

    @FXML
    private TableView table;
    @FXML
    private Button addNewButton;
    @FXML
    private TextField inputTask;
    @FXML
    private ComboBox<PriorityLevel> comboBox;
    @FXML
    private DatePicker datePicker;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");

    ObservableList<Cetli> Cetlik = FXCollections.observableArrayList();


    public void initialize(){
        comboBox.getItems().add(PriorityLevel.Red);
        comboBox.getItems().add(PriorityLevel.Yellow);
        comboBox.getItems().add(PriorityLevel.Green);

        TableColumn taskCol = new TableColumn("Task");
        taskCol.setMinWidth(350);
        taskCol.setCellFactory(TextFieldTableCell.forTableColumn());
        taskCol.setCellValueFactory(new PropertyValueFactory<Cetli, String>("task"));

        TableColumn priorityCol = new TableColumn("PriorityLevel");
        priorityCol.setMinWidth(100);
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

        TableColumn deadLineCol = new TableColumn("DeadLine");
        deadLineCol.setMinWidth(150);
        deadLineCol.setCellFactory(column -> {
            return new TableCell<Cetli, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(formatter.format(item));

                    }
                }
            };
        });
        deadLineCol.setCellValueFactory(new PropertyValueFactory<Cetli, LocalDateTime>("deadLine"));

        table.getColumns().addAll(taskCol, priorityCol, deadLineCol);
        table.setItems(Cetlik);
    }

    public void clickAddNewButton(ActionEvent event) {
        Cetli newCetli = new Cetli(inputTask.getText(), comboBox.getValue(), datePicker.getValue().atStartOfDay());
        Cetlik.add(newCetli);
        inputTask.clear();
        datePicker.setValue(null);
    }
}