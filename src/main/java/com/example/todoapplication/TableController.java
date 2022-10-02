package com.example.todoapplication;

import Database.DB;
import Model.Cetli;
import Model.PriorityLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TableController {

    @FXML
    private TableView<Cetli> table;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public static ObservableList<Cetli> Cetlik = FXCollections.observableArrayList();



    public void initialize(){

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
        Cetlik.addAll(HelloApplication.db.getAllContacts());
        table.setItems(Cetlik);

    }

    public void clickNewButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("workShop.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Cetli Műhely");
        stage.setScene(scene);
        stage.show();
    }

    public void clickDeleteButton(ActionEvent event) {
        Cetli selectedTask = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedTask);
        HelloApplication.db.removeContact(selectedTask);
    }

    public void clickAddButton(ActionEvent actionEvent) {
    }
}
