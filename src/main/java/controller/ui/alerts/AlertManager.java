package controller.ui.alerts;

import app.MainApp;
import controller.exceptions.MyException;
import javafx.scene.control.Alert;

public class AlertManager {

   private AlertManager() {}

   public static void showError(MyException exception) {
       Alert alert = new AlertBuilder()
               .setAlertType(Alert.AlertType.ERROR)
               .setTitle(exception.getErrorTitle())
               .setHeader(exception.getErrorHeader())
               .setContent(exception.getMessage())
               .setOwner(MainApp.getPrimaryStage())
               .build();

       alert.showAndWait();
   }

   public static void showCustomError(String title, String header, String content) {
       Alert alert = new AlertBuilder()
               .setAlertType(Alert.AlertType.ERROR)
               .setTitle(title)
               .setHeader(header)
               .setContent(content)
               .setOwner(MainApp.getPrimaryStage())
               .build();

       alert.showAndWait();
   }
}
