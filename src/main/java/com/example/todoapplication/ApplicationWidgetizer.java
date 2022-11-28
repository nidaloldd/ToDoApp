package com.example.todoapplication;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ApplicationWidgetizer {

            // make the widget app always (mostly) stay behind all applications


            public static void trayToBackHelper(Stage stage) {
                stage.show();
                stage.toBack();
                stage.setIconified(false);
            }

            // create the icon on the system tray, initialize the functionalities on the menus
            public static void createTrayIcon(Stage stage) {
                stage.setOnShowing(e -> stage.toBack());
                FXTrayIcon trayIcon = new FXTrayIcon.Builder(stage,ApplicationWidgetizer.class.getResource("/img/tray_icon.png"))
//                        .addTitleItem(true)
                        .onAction(e -> trayToBackHelper(stage))
                        .menuItem("Open ToDoApp", e -> trayToBackHelper(stage))
                        .separator()
                        .menuItem("Exit ToDoApp", e -> System.exit(0))
                        //.menuItem("My Menu 2", e -> myMenuMethod2())
                        .show()
                        .build();

            }
}
