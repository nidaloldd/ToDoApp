package com.example.todoapplication;

import Model.Cetli;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CardController {
    @FXML
    private Label taskName;
    @FXML
    private TreeView taskTree;
    @FXML
    private Label dateLabel;
    @FXML
    private Button expandButton ,deleteButton;
    @FXML
    private ImageView priorityImage;

    public void setData(Cetli cetli) {
        taskName.setText(cetli.getMainTask());
        dateLabel.setText(cetli.getDeadLineString());

        Image star = new Image(getClass().getResourceAsStream("/img/stars/star.png"));
        switch (cetli.getPriorityLevel()){
            case Red -> star = new Image(getClass().getResourceAsStream("/img/stars/starRed.png"));
            case Yellow -> star = new Image(getClass().getResourceAsStream("/img/stars/starYellow.png"));
            case Green -> star = new Image(getClass().getResourceAsStream("/img/stars/starGreen.png"));

        }
        priorityImage.setImage(star);

    }
    /*
    private TreeView<String> createTreeView() {
        TreeView<String> treeView = new TreeView<>();

        TreeItem<String> rootItem = new TreeItem<>("Files" , new CheckBox());

        TreeItem<String> branchItem1 = new TreeItem<>("Pictures",new CheckBox());
        TreeItem<String> branchItem2 = new TreeItem<>("Videos",new CheckBox());
        TreeItem<String> branchItem3 = new TreeItem<>("Music",new CheckBox());

        TreeItem<String> leafItem1 = new TreeItem<>("picture1",new CheckBox());
        TreeItem<String> leafItem2 = new TreeItem<>("picture2",new CheckBox());
        TreeItem<String> leafItem3 = new TreeItem<>("video1",new CheckBox());
        TreeItem<String> leafItem4 = new TreeItem<>("video2",new CheckBox());
        TreeItem<String> leafItem5 = new TreeItem<>("music1",new CheckBox());
        TreeItem<String> leafItem6 = new TreeItem<>("music2",new CheckBox());

        branchItem1.getChildren().addAll(leafItem1, leafItem2);
        branchItem2.getChildren().addAll(leafItem3, leafItem4);
        branchItem3.getChildren().addAll(leafItem5, leafItem6);

        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3);


        treeView.setOnMouseClicked(e -> {
            treeView.setPrefHeight(treeView.getExpandedItemCount()*25);

            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            if(item != null) {
                System.out.println(item.getValue());
            }
        });

        treeView.setRoot(rootItem);
        treeView.setPrefHeight(treeView.getExpandedItemCount()*25);

        return treeView;
    }

     */


}
