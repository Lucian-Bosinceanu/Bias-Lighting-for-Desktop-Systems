package controller.ui;

import app.MainApp;
import controller.ui.settings.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsViewManager {

    public static final String STATIC_SETTINGS_CREATE_GRADIENT = "view/settings/static_mode/EditPresetCreateGradientLayout.fxml";
    public static final String STATIC_SETTINGS_INDIVIDUAL_LED = "view/settings/static_mode/EditPresetIndividualLedsLayout.fxml";
    public static final String STATIC_SETTINGS_ADD_PRESET = "view/settings/static_mode/AddPresetLayout.fxml";
    public static final String STATIC_SETTINGS_EDIT_PRESET = "view/settings/static_mode/EditPresetLayout.fxml";

    public static final String GENERAL_SETTINGS = "view/settings/GeneralSettingsLayout.fxml";
    public static final String STATIC_SETTINGS = "view/settings/static_mode/StaticSettingsLayout.fxml";
    public static final String VIDEO_SETTINGS = "view/settings/VideoSettingsLayout.fxml";
    public static final String AUDIO_SETTINGS = "view/settings/AudioSettingsLayout.fxml";

    public static boolean showSettingsDialog(String resourceName, String title, boolean resizable, boolean maximized) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SettingsViewManager.class.getClassLoader().getResource(resourceName));
            AnchorPane dialog = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainApp.getPrimaryStage());
            Scene scene = new Scene(dialog);

            dialogStage.setResizable(resizable);
            dialogStage.setMaximized(maximized);
            dialogStage.setScene(scene);

            SettingsController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean showSettingsDialog(String resourceName, SettingsController customController, String title, boolean resizable, boolean maximized) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SettingsViewManager.class.getClassLoader().getResource(resourceName));
            AnchorPane dialog = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainApp.getPrimaryStage());
            Scene scene = new Scene(dialog);

            dialogStage.setResizable(resizable);
            dialogStage.setMaximized(maximized);
            dialogStage.setScene(scene);

            customController.setDialogStage(dialogStage);
            dialogStage.showAndWait();

            return customController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
