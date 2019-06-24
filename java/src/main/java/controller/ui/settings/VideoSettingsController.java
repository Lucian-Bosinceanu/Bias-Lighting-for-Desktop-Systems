package controller.ui.settings;

import controller.workers.MainWorker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import model.config.ConfigManager;
import model.config.enums.general.FrameMode;
import model.config.enums.video.VideoFrameUpdateStyle;
import model.config.enums.video.VideoImageFilter;
import model.config.enums.video.VideoProcessingDepth;
import model.config.enums.video.VideoProcessingType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VideoSettingsController extends ComponentInitializer implements SettingsController {

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private ChoiceBox<Integer> displayChoiceBox;

    @FXML
    private ChoiceBox<Integer> updateRateChoiceBox;

    @FXML
    private CheckBox useBrightnessCheckBox;

    @FXML
    private Slider brightnessSlider;

    @FXML
    private ChoiceBox<VideoImageFilter> imageFilterChoiceBox;

    @FXML
    private ChoiceBox<VideoProcessingType> videoProcessingTypeChoiceBox;

    @FXML
    private ChoiceBox<VideoProcessingDepth> videoProcessingDepthChoiceBox;

    @FXML
    private ChoiceBox<VideoFrameUpdateStyle> videoFrameUpdateStyleChoiceBox;

    @FXML
    private Slider lazyComfortRangeSlider;

    @FXML
    private Slider smoothFactorSlider;

    @FXML
    private void initialize() {
        initGeneralTab();
        initPreProcessingTab();
        initProcessingTab();
        initPostProcessingTab();

    }

    private void initPostProcessingTab() {
        videoFrameUpdateStyleChoiceBox.setValue(ConfigManager.getVideoConfig().getFrameToFrameUpdateStyle());
        videoFrameUpdateStyleChoiceBox.setItems(FXCollections.observableArrayList(VideoFrameUpdateStyle.values()));

        videoFrameUpdateStyleChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lazyComfortRangeSlider.setDisable(newValue.intValue() < 2);
                smoothFactorSlider.setDisable(newValue.intValue() % 2 == 0);
            }
        });

        initSlider(lazyComfortRangeSlider, 0f, 255f, 0.1f, 32f,
                ConfigManager.getVideoConfig().getLazyUpdateStyleComfortRange());

        initSlider(smoothFactorSlider, 0f, 0.9f, 0.25f, 0.1f,
                ConfigManager.getVideoConfig().getSmoothFactor());

        lazyComfortRangeSlider.setDisable(videoFrameUpdateStyleChoiceBox.getSelectionModel().getSelectedIndex() < 2);
        smoothFactorSlider.setDisable(videoFrameUpdateStyleChoiceBox.getSelectionModel().getSelectedIndex() % 2 == 0);
    }

    private void initProcessingTab() {
        videoProcessingTypeChoiceBox.setValue(ConfigManager.getVideoConfig().getProcessingType());
        videoProcessingTypeChoiceBox.setItems(FXCollections.observableArrayList(VideoProcessingType.values()));

        videoProcessingDepthChoiceBox.setValue(ConfigManager.getVideoConfig().getProcessingDepth());
        videoProcessingDepthChoiceBox.setItems(FXCollections.observableArrayList(VideoProcessingDepth.values()));
    }

    private void initPreProcessingTab() {
        imageFilterChoiceBox.setValue(ConfigManager.getVideoConfig().getImageFilter());
        imageFilterChoiceBox.setItems(FXCollections.observableArrayList(VideoImageFilter.values()));
    }

    private void initGeneralTab() {
        displayChoiceBox.setValue(ConfigManager.getVideoConfig().getDisplayId());

        List<Integer> screenIds = new ArrayList<>();
        int screenCount = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
        for (int i = 0; i < screenCount; i++) {
            screenIds.add(i);
        }
        displayChoiceBox.setItems(FXCollections.observableArrayList(screenIds));


        updateRateChoiceBox.setValue(ConfigManager.getVideoConfig().getUpdateRate());
        updateRateChoiceBox.setItems(FXCollections.observableArrayList(15, 25, 30, 45, 60));

        useBrightnessCheckBox.setSelected(ConfigManager.getVideoConfig().isBrightnessRegulated());

        useBrightnessCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                brightnessSlider.setDisable(!newValue);
            }
        });

        initSlider(brightnessSlider, 0, 2, 0.1f, 0.25f,
                ConfigManager.getVideoConfig().getBrightness());

        brightnessSlider.setDisable(!useBrightnessCheckBox.isSelected());
    }

    @Override
    public boolean isOkClicked() {

        return okClicked;
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void handleOk() {

        updateConfigs();
        okClicked = true;
        dialogStage.close();
    }

    @Override
    public void handleApply() {
        ConfigManager.getGlobalConfig().setFrameMode(FrameMode.VIDEO);
        updateConfigs();
        MainWorker.restart();
    }

    @Override
    public void handleCancel() {
        dialogStage.close();
    }

    private void updateConfigs() {
        ConfigManager.getVideoConfig().setDisplayId(displayChoiceBox.getValue());
        ConfigManager.getVideoConfig().setUpdateRate(updateRateChoiceBox.getValue());
        System.out.println(updateRateChoiceBox.getValue());
        ConfigManager.getVideoConfig().setBrightnessRegulated(useBrightnessCheckBox.isSelected());

        if (useBrightnessCheckBox.isSelected()) {
            ConfigManager.getVideoConfig().setBrightness((float) brightnessSlider.getValue());
        }

        System.out.println(imageFilterChoiceBox.getValue());
        ConfigManager.getVideoConfig().setImageFilter(imageFilterChoiceBox.getValue());
        ConfigManager.getVideoConfig().setProcessingType(videoProcessingTypeChoiceBox.getValue());
        ConfigManager.getVideoConfig().setProcessingDepth(videoProcessingDepthChoiceBox.getValue());
        ConfigManager.getVideoConfig().setFrameToFrameUpdateStyle(videoFrameUpdateStyleChoiceBox.getValue());
        ConfigManager.getVideoConfig().setLazyUpdateStyleComfortRange((int) lazyComfortRangeSlider.getValue());
        ConfigManager.getVideoConfig().setSmoothFactor(smoothFactorSlider.getValue());
    }
}
