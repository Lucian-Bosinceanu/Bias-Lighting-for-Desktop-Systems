package controller.ui.settings.ambient;

import controller.io.FileManager;
import controller.ui.alerts.AlertBuilder;
import controller.ui.alerts.AlertManager;
import controller.ui.settings.SettingsController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FrameColorPreset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;


public class AddPresetController implements SettingsController {

    private boolean okClicked;
    private Stage dialogStage;

    @FXML
    private TextField presetNameTextField;

    @Override
    public boolean isOkClicked() {
        return okClicked;
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        okClicked = false;
    }

    @Override
    public void handleOk() {
        if (isValidInput()) {
            if (isUniqueOrRewritable()) {
                okClicked = true;
                addNewPreset();
                dialogStage.close();
            }
        }
    }

    private boolean isUniqueOrRewritable() {
        String path = FileManager.getPresetPath();
        boolean isUniquePresetName = true;
        boolean isEditablePreset = true;
        File[] files = new File(path).listFiles();
        String presetName = presetNameTextField.getText().toLowerCase();

        for (File f : files) {
            if (fileExist(f.toPath())) {
                isUniquePresetName = false;
                try {
                    FrameColorPreset frameColorPreset = FrameColorPreset.loadColorState(f.toPath().getFileName().toString());
                    isEditablePreset = frameColorPreset.getEditable();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        if (!isEditablePreset) {
            AlertManager.showCustomError("Error!",
                    "Invalid preset name! A preset named " + presetName + " already exists and it's not editable!",
                    "Preset name cannot have the name of a preset that is not editable!");
            return false;
        }

        if (!isUniquePresetName) {
            Alert rewriteConfirmation = new AlertBuilder()
                    .setAlertType(Alert.AlertType.CONFIRMATION)
                    .setTitle("Rewrite preset")
                    .setHeader("Attention!")
                    .setContent("A preset named " + presetName + " already exists! Do you want to overwrite it?")
                    .setOwner(dialogStage)
                    .build();

            Optional<ButtonType> option = rewriteConfirmation.showAndWait();
            if (option.get() == ButtonType.OK) {
                return true;
            }

        }

        return true;
    }

    private boolean fileExist(Path path) {
        return path.getFileName().toString().contains(presetNameTextField.getText().toLowerCase());
    }

    private void addNewPreset() {
        String presetName = presetNameTextField.getText().toLowerCase();
        FrameColorPreset newPreset = new FrameColorPreset();
        newPreset.setName(presetName);

        try {
            newPreset.saveColorState();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidInput() {
        StringBuilder errorMessage = new StringBuilder();
        String userInput = presetNameTextField.getText();

        if (userInput.isEmpty()) {
            errorMessage.append("Preset name cannot be null!");
        }else if (userInput.contains(".")) {
            errorMessage.append("Preset name must not contain the '.' character!");
        } else if (userInput.length() > 20) {
            errorMessage.append("Preset name must have at most 20 characters!");
        }

        if (errorMessage.toString().length() == 0) {
            return true;
        }
        else {
            Alert invalidPresetNameAlert = new AlertBuilder()
                    .setAlertType(Alert.AlertType.ERROR)
                    .setTitle("Error!")
                    .setHeader("Invalid preset name!")
                    .setOwner(dialogStage)
                    .setContent(errorMessage.toString())
                    .build();

            invalidPresetNameAlert.showAndWait();
            return false;
        }
    }


    @Override
    public void handleApply() {

    }

    @Override
    public void handleCancel() {
        dialogStage.close();
    }
}
