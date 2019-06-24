package controller.ui.settings;

import controller.exceptions.MyException;
import controller.io.FileManager;
import controller.ui.alerts.AlertManager;
import controller.workers.MainWorker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FrameColorPreset;
import model.config.ConfigManager;
import model.config.config.AudioConfig;
import model.config.enums.audio.AudioProcessingType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class AudioSettingsController extends ComponentInitializer implements SettingsController {

    private Stage dialogStage;
    private boolean isOkClicked;

    @FXML
    private ChoiceBox<AudioProcessingType> audioProcessingTypeChoiceBox;

    @FXML
    private CheckBox useDynamicSensibilityCheckBox;

    @FXML
    private Slider sensibilitySlider;

    @FXML
    private ChoiceBox<FrameColorPreset> basePresetChoiceBox;

    @FXML
    private CheckBox useAnimationSmoothingCheckbox;

    @FXML
    private Slider upBeatSmoothingSlider;

    @FXML
    private Slider downBeatSmoothingSlider;

    @FXML
    private CheckBox useColorInterpolationCheckbox;

    @FXML
    private ChoiceBox<FrameColorPreset> interpolationPresetChoiceBox;

    @FXML
    private TextField animationSpeedTextField;

    @FXML
    private CheckBox shuffleColorPresetCheckbox;

    @FXML
    private void initialize() {
        initializeBasicSettings();
        initializeAdvancedSettings();
        loadPresets();
    }

    private void loadPresets() {
        interpolationPresetChoiceBox.setItems(FXCollections.observableArrayList());
        basePresetChoiceBox.setItems(FXCollections.observableArrayList());
        String path = FileManager.getPresetPath();
        File[] files = new File(path).listFiles();

        for (File f : files) {
            addPreset(f.toPath());
        }

        for (FrameColorPreset state : basePresetChoiceBox.getItems()) {
            if ((state.getName() + ".json").equals(ConfigManager.getAudioConfig().getFramePreset())) {
                basePresetChoiceBox.setValue(state);
                break;
            }
        }

        for (FrameColorPreset state : interpolationPresetChoiceBox.getItems()) {
            if ((state.getName() + ".json").equals(ConfigManager.getAudioConfig().getInterpolationPresetName())) {
                interpolationPresetChoiceBox.setValue(state);
                break;
            }
        }

    }

    private void addPreset(Path presetPath) {
        try {
            FrameColorPreset colorState = FrameColorPreset.loadColorState(presetPath.getFileName().toString());
            basePresetChoiceBox.getItems().add(colorState);
            interpolationPresetChoiceBox.getItems().add(colorState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeBasicSettings() {
        audioProcessingTypeChoiceBox.setItems(FXCollections.observableArrayList(AudioProcessingType.values()));
        audioProcessingTypeChoiceBox.setValue(ConfigManager.getAudioConfig().getProcessingType());

        useDynamicSensibilityCheckBox.setSelected(ConfigManager.getAudioConfig().isDynamicSensibility());
        useDynamicSensibilityCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                sensibilitySlider.setDisable(newValue);
            }
        });

        sensibilitySlider.setDisable(useDynamicSensibilityCheckBox.isSelected());

        initSlider(sensibilitySlider, 0, 100, 25f, 25f,
                ConfigManager.getAudioConfig().getSensibility());
    }

    private void initializeAdvancedSettings() {
        useAnimationSmoothingCheckbox.setSelected(ConfigManager.getAudioConfig().isRelaxedTransition());
        useAnimationSmoothingCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            upBeatSmoothingSlider.setDisable(!newValue);
            downBeatSmoothingSlider.setDisable(!newValue);
        });

        initSlider(upBeatSmoothingSlider, 0, 1, 0.25f, 0.25f,
                ConfigManager.getAudioConfig().getUpbeatSmoothFactor());
        upBeatSmoothingSlider.setDisable(!useAnimationSmoothingCheckbox.isSelected());

        initSlider(downBeatSmoothingSlider, 0, 1, 0.25f, 0.25f,
                ConfigManager.getAudioConfig().getDownBeatSmoothFactor());
        downBeatSmoothingSlider.setDisable(!useAnimationSmoothingCheckbox.isSelected());


        useColorInterpolationCheckbox.setSelected(ConfigManager.getAudioConfig().isRhythmicInterpolation());
        useColorInterpolationCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            interpolationPresetChoiceBox.setDisable(!newValue);
            animationSpeedTextField.setDisable(!newValue);
            shuffleColorPresetCheckbox.setDisable(!newValue);
        });

        interpolationPresetChoiceBox.setDisable(!useColorInterpolationCheckbox.isSelected());

        initNumberTextField(
                animationSpeedTextField,
                ConfigManager.getAudioConfig().getBaseAnimationSpeed() / 1000,
                !useAnimationSmoothingCheckbox.isSelected()
        );
        animationSpeedTextField.setDisable(!useColorInterpolationCheckbox.isSelected());

        shuffleColorPresetCheckbox.setSelected(ConfigManager.getAudioConfig().isShufflingPresets());
        shuffleColorPresetCheckbox.setDisable(!useColorInterpolationCheckbox.isSelected());
    }

    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        isOkClicked = false;
    }

    @Override
    public void handleOk() {
        if (isValidInput()) {
            saveSettingsToConfigManager();
            isOkClicked = true;
            dialogStage.close();
        }
    }

    @Override
    public void handleApply() {
        if (isValidInput()) {
            saveSettingsToConfigManager();
            MainWorker.restart();
        }
    }

    private boolean isValidInput() {
        try {
            Integer animationSpeed = Integer.valueOf(animationSpeedTextField.getText());

            if (animationSpeed == 0) {
                throw new MyException("Input error!", "Invalid animation speed!", "Animation speed cannot be 0!");
            }

            return true;
        } catch (NumberFormatException e) {
            AlertManager.showCustomError(
                    "Input error!",
                    "Invalid animation speed!",
                    "Animation speed text field must contain integer numbers!");
            return false;
        }catch (MyException e) {
            AlertManager.showError(e);
            return false;
        }
    }

    @Override
    public void handleCancel() {
        isOkClicked = false;
        dialogStage.close();
    }

    private void saveSettingsToConfigManager() {
        AudioConfig audioConfig = ConfigManager.getAudioConfig();
        audioConfig.setProcessingType(audioProcessingTypeChoiceBox.getValue());
        audioConfig.setBasePreset(basePresetChoiceBox.getValue().getName() + ".json");
        audioConfig.setDynamicSensibility(useDynamicSensibilityCheckBox.isSelected());
        audioConfig.setSensibility(sensibilitySlider.getValue());

        audioConfig.setRelaxedTransition(useAnimationSmoothingCheckbox.isSelected());
        audioConfig.setUpbeatSmoothFactor(upBeatSmoothingSlider.getValue());
        audioConfig.setDownBeatSmoothFactor(downBeatSmoothingSlider.getValue());

        audioConfig.setRhythmicInterpolation(useColorInterpolationCheckbox.isSelected());
        audioConfig.setInterpolationPresetName(interpolationPresetChoiceBox.getValue().getName() + ".json");
        audioConfig.setBaseAnimationSpeed(Long.valueOf(animationSpeedTextField.getText()) * 1000);

        audioConfig.setShufflePresets(shuffleColorPresetCheckbox.isSelected());
    }
}
