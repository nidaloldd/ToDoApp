package com.example.todoapplication;

import Model.Cetli;
import Model.PriorityLevel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CreateTaskController {
    public Pane alertPane;
    public Label alertLabel;
    public ImageView priorityImage;
    @FXML
    private TextField inputTask;
    @FXML
    private ComboBox<PriorityLevel> priorityPicker;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Integer> hourPicker;
    @FXML
    private ComboBox<Integer> minutePicker;
    @FXML
    private ComboBox<String> parentPicker;

    public void setComboBoxValues(){
        setParentPickerValues();
        setPriorityPickerValues();
        setHourPickerValues();
        setMinutePickerValues();
    }
    public void setParentPickerValues(){
        ArrayList<Cetli> data = HelloApplication.db.getAllToDo();
        parentPicker.getItems().clear();
        parentPicker.getItems().add("root");
        for (Cetli c : data){
            parentPicker.getItems().add(c.getTask());
        }
    }
    public void setPriorityPickerValues(){
        priorityPicker.getItems().add(PriorityLevel.Red);
        priorityPicker.getItems().add(PriorityLevel.Yellow);
        priorityPicker.getItems().add(PriorityLevel.Green);

        priorityPicker.setOnAction(e -> {
            Image star = new Image(getClass().getResourceAsStream("/img/stars/star.png"));
            switch (priorityPicker.getValue()){
                case Red -> star = new Image(getClass().getResourceAsStream("/img/stars/starRed.png"));
                case Yellow -> star = new Image(getClass().getResourceAsStream("/img/stars/starYellow.png"));
                case Green -> star = new Image(getClass().getResourceAsStream("/img/stars/starGreen.png"));
            }
            priorityImage.setImage(star);
        });
    }
    public void setHourPickerValues() {
        hourPicker.getItems().clear();
        for (int i = 0; i < 24; i++) {
            hourPicker.getItems().add(i);
        }
    }
    public void setMinutePickerValues() {
        minutePicker.getItems().clear();
        for (int i = 0; i < 60; i++) {
            minutePicker.getItems().add(i);
        }
    }

    public boolean getData(){
        if (alert()){
            Cetli newCetli = new Cetli(
                    inputTask.getText(),
                    parentPicker.getValue(),
                    priorityPicker.getValue().toString(),
                    LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourPicker.getValue(), minutePicker.getValue())).format(HelloApplication.formatter),
                    LocalDateTime.now().format(HelloApplication.formatter));

            HelloApplication.Cetlik.add(newCetli);
            HelloApplication.db.addTask(newCetli);
            return true;
        }
        return false;
    }
    public void removeData(String name){
        ArrayList<Cetli> data = HelloApplication.db.getAllToDo();
        HelloApplication.Cetlik.removeIf(c -> c.getTask().equals(name));
        for (Cetli c : data){
            if (c.getTask().equals(name)){
                HelloApplication.db.removeTask(c);
            }
        }
    }
    private boolean checkUniqueName(String name){
        ArrayList<Cetli> data = HelloApplication.db.getAllToDo();
        for (Cetli c : data) {
            if (c.getTask().equals(name)){
                return true;
            }
        }
        return false;
    }
    private boolean alert(){
        if (inputTask.getText().equals("") || checkUniqueName(inputTask.getText())){
            alertLabel.setText("wrong task name!");
            alertPane.setVisible(true);
            return false;
        }
        if (priorityPicker.getValue() == null){
            alertLabel.setText("wrong priority!");
            alertPane.setVisible(true);
            return false;
        }
        if (parentPicker.getValue() == null){
            alertLabel.setText("wrong parent!");
            alertPane.setVisible(true);
            return false;
        }
        if (datePicker.getValue() == null){
            alertLabel.setText("wrong date!");
            alertPane.setVisible(true);
            return false;
        }
        if (hourPicker.getValue() == null){
            alertLabel.setText("wrong hour!");
            alertPane.setVisible(true);
            return false;
        }
        if (minutePicker.getValue() == null){
            alertLabel.setText("wrong minute!");
            alertPane.setVisible(true);
            return false;
        }
        return true;
    }

    public void clickAlertButton() {
        alertPane.setVisible(false);
    }
}
