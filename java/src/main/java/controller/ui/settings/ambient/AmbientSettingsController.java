package controller.ui.settings.ambient;

import controller.io.FileManager;
import controller.ui.SettingsViewManager;
import controller.ui.alerts.AlertBuilder;
import controller.ui.settings.ComponentInitializer;
import controller.ui.settings.SettingsController;
import controller.workers.MainWorker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.FrameColorPreset;
import model.config.ConfigManager;
import model.config.enums.ambient.AmbientAnimationStyle;
import model.config.enums.general.FrameMode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class AmbientSettingsController extends ComponentInitializer implements SettingsController {

    private Boolean isOkClicked;
    private Stage dialogStage;

    @FXML
    private ChoiceBox<FrameColorPreset> presetChoiceBox;

    @FXML
    private Button addPresetButton;

    @FXML
    private Button editPresetButton;

    @FXML
    private Button deletePresetButton;

    @FXML
    private ChoiceBox<AmbientAnimationStyle> animationStyleChoiceBox;

    @FXML
    private TextField speedTextField;

    @FXML
    private ChoiceBox<FrameColorPreset> interpolationPresetChoiceBox;

    @FXML
    private CheckBox isShufflingPresets;

    @Override
    public boolean isOkClicked() {
        return isOkClicked;
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        isOkClicked = false;
        this.dialogStage = dialogStage;

        editPresetButton.setOnMouseClicked(event -> handleEditPreset());
        addPresetButton.setOnMouseClicked(event -> handleAddPreset());
        deletePresetButton.setOnMouseClicked(event -> handleDeletePreset());
    }

    @FXML
    private void initialize() {
        init();
    }

    private void init() {
        ConfigManager.getGlobalConfig().setFrameMode(FrameMode.AMBIENT);
        initPresetChoiceBox();
        animationStyleChoiceBox.setItems(FXCollections.observableArrayList(AmbientAnimationStyle.values()));
        animationStyleChoiceBox.setValue(ConfigManager.getAmbientConfig().getAnimationStyle());

//        interpolationPresetChoiceBox.setDisable(
//                animationStyleChoiceBox.getValue() == AmbientAnimationStyle.STATIC ||
//                animationStyleChoiceBox.getValue() == AmbientAnimationStyle.SPIN
//        );

        animationStyleChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (animationStyleChoiceBox.getValue() == AmbientAnimationStyle.INTERPOLATE) {
                    interpolationPresetChoiceBox.setDisable(false);
                } else {
                    interpolationPresetChoiceBox.setDisable(true);
                }

                if (animationStyleChoiceBox.getValue() == AmbientAnimationStyle.FADE) {
                    interpolationPresetChoiceBox.setDisable(false);
                }

                if (animationStyleChoiceBox.getValue() == AmbientAnimationStyle.SPIN) {
                    isShufflingPresets.setSelected(false);
                    isShufflingPresets.setDisable(true);
                } else {
                    isShufflingPresets.setDisable(false);
                }
            }
        });

        initNumberTextField(
                speedTextField,
                ConfigManager.getAmbientConfig().getAnimationSpeed() / 1000,
                false
        );

        isShufflingPresets.setSelected(ConfigManager.getAmbientConfig().isShufflePresets());
    }

    private void initPresetChoiceBox() {
        String path = FileManager.getPresetPath();

        File[] files = new File(path).listFiles();

        for (File f : files) {
            addPreset(f.toPath());
        }

        for (FrameColorPreset state : presetChoiceBox.getItems()) {
            if ((state.getName() + ".json").equals(ConfigManager.getAmbientConfig().getFramePresetName())) {
                presetChoiceBox.setValue(state);
                changeSelectedPreset(state);
                break;
            }
        }

        for (FrameColorPreset state : interpolationPresetChoiceBox.getItems()) {
            if ((state.getName() + ".json").equals(ConfigManager.getAmbientConfig().getInterpolationPresetName())) {
                interpolationPresetChoiceBox.setValue(state);
                break;
            }
        }

        presetChoiceBox.setOnAction(event -> {
            changeSelectedPreset(presetChoiceBox.getValue());
            handleApply();
        });
    }

    private void changeSelectedPreset(FrameColorPreset value) {
        ConfigManager.getAmbientConfig().setFramePresetName(presetChoiceBox.getValue().getName() + ".json");
        editPresetButton.setDisable(!value.getEditable());
        deletePresetButton.setDisable(!value.getEditable());
    }

    private void addPreset(Path presetPath) {
        try {
            FrameColorPreset colorState = FrameColorPreset.loadColorState(presetPath.getFileName().toString());
            boolean shouldAddPreset = true;

            for (FrameColorPreset preset : presetChoiceBox.getItems()) {
                if (colorState.getName().equals(preset.getName())) {
                    shouldAddPreset = false;
                    break;
                }
            }

            if (shouldAddPreset) {
                presetChoiceBox.getItems().add(colorState);
                interpolationPresetChoiceBox.getItems().add(colorState);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleOk() {
        if (isValidInput()) {
            isOkClicked = true;
            updateConfigs();
            dialogStage.close();
        }
    }

    @Override
    public void handleApply() {
        if (isValidInput()) {
            updateConfigs();
            MainWorker.restart();
        }
    }

    private boolean isValidInput() {
        String inputSpeed = speedTextField.getText();
        String errorMessage = "";

        if (inputSpeed.isEmpty()) {
            errorMessage += "Animation speed field must not be empty!";
        } else if (Integer.valueOf(inputSpeed) == 0) {
            errorMessage += "Animation speed cannot be 0!";
        }

        if (errorMessage.length() > 0) {
            Alert errorAlert = new AlertBuilder()
                    .setAlertType(Alert.AlertType.ERROR)
                    .setTitle("Error!")
                    .setHeader("Invalid animation speed!")
                    .setContent(errorMessage)
                    .build();

            errorAlert.showAndWait();
        }

        return errorMessage.length() == 0;
    }


    private void updateConfigs() {
        ConfigManager.getAmbientConfig().setFramePresetName(presetChoiceBox.getValue().getName() + ".json");
        ConfigManager.getAmbientConfig().setAnimationSpeed(Integer.valueOf(speedTextField.getText()) * 1000);
        ConfigManager.getAmbientConfig().setAnimationStyle(animationStyleChoiceBox.getValue());
        ConfigManager.getAmbientConfig().setInterpolationPreset(interpolationPresetChoiceBox.getValue().getName() + ".json");
        ConfigManager.getAmbientConfig().setShufflePresets(isShufflingPresets.isSelected());
    }

    @Override
    public void handleCancel() {
        dialogStage.close();
    }

    private void handleEditPreset() {
        ConfigManager.getAmbientConfig().setFramePresetName(presetChoiceBox.getValue().getName() + ".json");
        boolean editPresetControllerOkClicked = SettingsViewManager.showSettingsDialog(
                SettingsViewManager.STATIC_SETTINGS_EDIT_PRESET,
                "Edit preset",
                false,
                false
        );

        if (editPresetControllerOkClicked) {
            handleApply();
        }
    }

    private void handleAddPreset() {
        boolean okClicked = SettingsViewManager.showSettingsDialog(
                SettingsViewManager.STATIC_SETTINGS_ADD_PRESET,
                "Add preset",
                false,
                false
        );

        if (okClicked) {
            initPresetChoiceBox();
            handleApply();
        }
    }

    private void handleDeletePreset() {
        Alert deleteConfirmation = new AlertBuilder()
                .setAlertType(Alert.AlertType.CONFIRMATION)
                .setTitle("Delete preset confirmation")
                .setHeader("Attention!")
                .setContent("Are you sure you want to delete the preset named " + presetChoiceBox.getValue().getName() + "?")
                .build();

        Optional<ButtonType> option = deleteConfirmation.showAndWait();
        boolean isSuccessfulDelete = false;

        if (option.get() == ButtonType.OK) {
            FrameColorPreset stateToBeDeleted = presetChoiceBox.getValue();
            presetChoiceBox.getItems().remove(stateToBeDeleted);

            try {
                isSuccessfulDelete = stateToBeDeleted.deleteColorState();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!isSuccessfulDelete) {
                Alert couldNotDeleteAlert = new AlertBuilder()
                        .setAlertType(Alert.AlertType.ERROR)
                        .setTitle("Delete error!")
                        .setHeader("Could not delete the preset named " + presetChoiceBox.getValue().getName() + "!")
                        .setOwner(dialogStage)
                        .build();
                presetChoiceBox.getItems().add(stateToBeDeleted);
                couldNotDeleteAlert.showAndWait();
            } else {
                interpolationPresetChoiceBox.getItems().remove(stateToBeDeleted);
                if (interpolationPresetChoiceBox.getValue().equals(stateToBeDeleted)) {
                    interpolationPresetChoiceBox.setValue(interpolationPresetChoiceBox.getItems().get(0));
                }

                presetChoiceBox.setValue(presetChoiceBox.getItems().get(0));
            }
        }


    }
}
