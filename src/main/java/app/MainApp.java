package app;

import controller.io.FileManager;
import controller.io.serial.SerialCommunicator;
import controller.ui.MainLayoutController;
import controller.ui.alerts.AlertBuilder;
import controller.workers.MainWorker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jssc.SerialPortException;
import model.config.ConfigManager;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage;
    private AnchorPane mainLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ConfigManager.init();
        createHomeDirectory();
        MainWorker.start();
        
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("Desktop Lights");
        MainApp.primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closingProgram();
        });

        initMainLayout();
    }

    private void createHomeDirectory() {
        if (!FileManager.createHomeDirectory()) {
            Alert alert = new AlertBuilder()
                    .setAlertType(Alert.AlertType.ERROR)
                    .setTitle("Fatal error!")
                    .setHeader("Could not create folder in user Documents!")
                    .setContent("Please make sure that Documents folder is not write protected!")
                    .setOwner(primaryStage)
                    .build();
            alert.showAndWait();
            closingProgram();
        }
    }

    private void closingProgram() {
        MainWorker.stop();
        ConfigManager.saveProperties();
        try {
            SerialCommunicator.sendTurnOffCommand();
            SerialCommunicator.sendTurnOffCommand();
            SerialCommunicator.sendTurnOffCommand();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        primaryStage.close();
        System.exit(0);
    }

    private void initMainLayout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/MainLayout.fxml"));
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);

        MainLayoutController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
