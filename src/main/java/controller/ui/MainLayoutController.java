package controller.ui;

import app.MainApp;
import controller.workers.MainWorker;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.config.ConfigManager;
import model.config.enums.general.FrameMode;


public class MainLayoutController {

    private MainApp mainApp;

    @FXML
    private ImageView imageView;

    @FXML
    public void openGeneralSettings() {
        boolean okClicked = SettingsViewManager.showSettingsDialog(
                SettingsViewManager.GENERAL_SETTINGS,
                "General settings",
                false,
                false
        );

        if (okClicked) {
            ConfigManager.saveProperties();
            MainWorker.restart();
        }

        updateCurrentModeImage();
    }

    @FXML
    public void openAmbientModeSettings() {
        boolean okClicked = SettingsViewManager.showSettingsDialog(
                SettingsViewManager.STATIC_SETTINGS,
                "Ambient settings",
                false,
                false
        );

        if (okClicked) {
            ConfigManager.saveProperties();
            changeModeTo(FrameMode.AMBIENT);
        }

        updateCurrentModeImage();
    }

    @FXML
    public void openVideoSettings() {
        boolean okClicked = SettingsViewManager.showSettingsDialog(
                SettingsViewManager.VIDEO_SETTINGS,
                "Video settings",
                false,
                false
        );

        if (okClicked) {
            ConfigManager.saveProperties();
            changeModeTo(FrameMode.VIDEO);
        }
    }

    @FXML
    public void openAudioSettings() {
        boolean okClicked = SettingsViewManager.showSettingsDialog(
                SettingsViewManager.AUDIO_SETTINGS,
                "Audio settings",
                false,
                false
        );

        if (okClicked) {
            ConfigManager.saveProperties();
            changeModeTo(FrameMode.AUDIO);
        }
    }

    @FXML
    public void setVideoMode() {
        FrameMode currentMode = ConfigManager.getGlobalConfig().getFrameMode();

        if (currentMode != FrameMode.VIDEO) {
            changeModeTo(FrameMode.VIDEO);
        }

    }

    @FXML
    public void setAudioMode() {
        FrameMode currentMode = ConfigManager.getGlobalConfig().getFrameMode();

        if (currentMode != FrameMode.AUDIO) {
            changeModeTo(FrameMode.AUDIO);
        }
    }

    @FXML
    public void setStaticMode() {
        FrameMode currentMode = ConfigManager.getGlobalConfig().getFrameMode();

        if (currentMode != FrameMode.AMBIENT) {
            changeModeTo(FrameMode.AMBIENT);
        }
    }

    @FXML
    public void setNoMode() {
        FrameMode currentMode = ConfigManager.getGlobalConfig().getFrameMode();

        if (currentMode != FrameMode.NONE) {
            changeModeTo(FrameMode.NONE);
        }
    }

    @FXML
    private void initialize() {
        updateCurrentModeImage();
    }

    public void updateCurrentModeImage() {
        FrameMode currentMode = ConfigManager.getGlobalConfig().getFrameMode();
        Image image = null;

        switch (currentMode) {
            case AMBIENT:
                image = new Image("images/ambient_mode.png");
                break;
            case VIDEO:
                image = new Image("images/video_mode.png");
                break;
            case AUDIO:
                image = new Image("images/audio_mode.png");
                break;
            case NONE:
                image = new Image("images/no_mode.png");
        }

        imageView.setImage(image);
    }

    private void changeModeTo(FrameMode frameMode) {
        ConfigManager.getGlobalConfig().setFrameMode(frameMode);
        ConfigManager.saveProperties();
        MainWorker.restart();
        updateCurrentModeImage();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
