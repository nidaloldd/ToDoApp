package com.example.todoapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Cetli;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;

public class TervController  implements Initializable {

    public AnchorPane rootPane;
    @FXML
    HBox topBar;
    @FXML
    TilePane tilePane;
    @FXML
    AnchorPane cetliMuhelyPane;
    @FXML
    Button slideInButton;
    @FXML
    Button slideOutButton;
    @FXML
    TilePane inputCetliPane;
    ArrayList<TreeView> trees = new ArrayList<>();

    private double xOffset=0,yOffset=0;
    GetInputCardController getInputCardController;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        ArrayList<Cetli> data = HelloApplication.db.getAllContacts();
        //makeDraggable(HelloApplication.stage, rootPane);

        makeStageDragable();

        cetliMuhelyPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            cetliMuhelyPane.setVisible(false);
            slideIn();

        });

        for (Cetli c:data) {
            tilePane.getChildren().add(createCetliFxml(c));
        }

        cetliMuhelyPane.setTranslateX(-160);
        slideOutButton.setVisible(false);
        slideInButton.setVisible(true);

        Node inputCetli = createInputCetliFxml();
        inputCetliPane.getChildren().add(inputCetli);



    }
    public void clickAddButton(ActionEvent actionEvent) {
        getInputCardController.getData();
    }

    /*
    private Node createCetli(Cetli cetli){

        BorderPane borderPane = new BorderPane();

        Label taskLabel = new Label();
        taskLabel.setText(cetli.getMainTask());
        BorderPane.setAlignment(taskLabel,Pos.CENTER);
        BorderPane.setMargin(taskLabel, new Insets(30));

        borderPane.setTop(taskLabel);

         TreeView treeView = createTreeview();
        borderPane.setCenter(treeView);
        BorderPane.setMargin(treeView, new Insets(10));
        treeView.prefHeight(0);


        Label dateLabel = new Label();
        dateLabel.setText(cetli.getDeadLineString());
        HBox.setMargin(dateLabel,new Insets(10,20,20,20));

        Button button = new Button("Button");
        button.prefHeight(25);
        button.prefWidth(90);
        HBox.setMargin(button,new Insets(10,20,20,20));

        HBox hBox = new HBox(dateLabel,button);
        hBox.setAlignment(Pos.CENTER);

        borderPane.setBottom(hBox);

        borderPane.setStyle("-fx-background-color:  #A5C9CA; " +
                "-fx-border-width: 2; " +
                "-fx-border-color: black; " +
                "-fx-border-radius: 10;");

        BorderPane.setMargin(treeView, new Insets(10,30,10,10));
        borderPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        borderPane.setPrefWidth(Region.USE_COMPUTED_SIZE);

        Image star = new Image(getClass().getResourceAsStream("/img/stars/star.png"));

        switch (cetli.getPriorityLevel()){
            case Red -> {
                star = new Image(getClass().getResourceAsStream("/img/stars/starRed.png"));
            }
            case Yellow -> {
                star = new Image(getClass().getResourceAsStream("/img/stars/starYellow.png"));
            }
            case Green -> {
                star = new Image(getClass().getResourceAsStream("/img/stars/starGreen.png"));
            }
        }
        ImageView priorityImage = new ImageView(star);

        priorityImage.setLayoutX(-40);
        priorityImage.setLayoutY(-30);
        priorityImage.setFitHeight(100);
        priorityImage.setFitWidth(100);

        Pane pane = new Pane(borderPane,priorityImage);
        pane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        pane.setPrefWidth(Region.USE_COMPUTED_SIZE);

        TilePane.setMargin(pane, new Insets(30,0,0,20));


        return pane;


    }
    */


    private Node createCetliFxml(Cetli cetli){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Card.fxml"));
            Node node = fxmlLoader.load();

            CardController cardController = fxmlLoader.getController();
            cardController.setData(cetli);

            return  node;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pane();
    }
    private Node createInputCetliFxml(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/getInputCard.fxml"));
            Node node = fxmlLoader.load();
            getInputCardController = fxmlLoader.getController();


            return  node;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pane();
    }
    private TreeView<String> createTreeview() {
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

            TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
            if(item != null) {
                System.out.println(item.getValue());
            }
        });

        treeView.setRoot(rootItem);
        treeView.setPrefHeight(treeView.getExpandedItemCount()*25);

        trees.add(treeView);
        return treeView;
    }

    @FXML
    void slideOut(){
        cetliMuhelyPane.setVisible(true);
        slideOutButton.setVisible(false);
        slideInButton.setVisible(true);

        System.out.println("slideOut");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(cetliMuhelyPane);

        slide.setToX(0);
        slide.play();
        cetliMuhelyPane.setTranslateX(cetliMuhelyPane.getWidth());
        slide.setOnFinished((ActionEvent e) -> {

        });
    }

    @FXML
    void slideIn(){

        slideOutButton.setVisible(true);
        slideInButton.setVisible(false);
        System.out.println("slideIn");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(cetliMuhelyPane);

        slide.setToX(cetliMuhelyPane.getWidth());
        slide.play();
        cetliMuhelyPane.setTranslateX(0);
        slide.setOnFinished((ActionEvent e) -> {

        });
    }

    @FXML
    private void minimizeStage(){
        HelloApplication.stage.setIconified(true);
    }
    @FXML
    private void closeApp(){
        System.exit(0);
    }
    private void makeStageDragable(){
        topBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = HelloApplication.stage.getX() - event.getScreenX();
                yOffset = HelloApplication.stage.getY() - event.getScreenY();
            }
        });
        topBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HelloApplication.stage.setX(event.getScreenX() + xOffset);
                HelloApplication.stage.setY(event.getScreenY() + yOffset);
                HelloApplication.stage.setOpacity(0.6f);
            }
        });

        topBar.setOnDragDone((event)-> {
            HelloApplication.stage.setOpacity(1.0f);
        });

        topBar.setOnMouseReleased((event)-> {
            HelloApplication.stage.setOpacity(1.0f);
        });
    }

    static class Delta { double x, y; }
    /** makes a stage draggable using a given node */
    public static void makeDraggable(final Stage stage, final Node byNode) {
        final Delta dragDelta = new Delta();
        byNode.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = stage.getY() - mouseEvent.getScreenY();
                byNode.setCursor(Cursor.MOVE);
            }
        });
        byNode.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                byNode.setCursor(Cursor.HAND);
            }
        });
        byNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });
        byNode.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    byNode.setCursor(Cursor.HAND);
                }
            }
        });
        byNode.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    byNode.setCursor(Cursor.DEFAULT);
                }
            }
        });
    }



}


