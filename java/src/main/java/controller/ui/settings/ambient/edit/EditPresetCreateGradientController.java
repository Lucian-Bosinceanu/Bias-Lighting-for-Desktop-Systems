package controller.ui.settings.ambient.edit;

import controller.processing_units.units.AmbientProcessingUnit;
import controller.ui.settings.SettingsController;
import controller.workers.MainWorker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.FrameColorPreset;
import model.config.enums.general.FrameSegment;

import java.io.IOException;
import java.util.List;

public class EditPresetCreateGradientController implements SettingsController {

    private Stage dialogStage;
    private boolean isOkClicked = false;

    private List<AmbientProcessingUnit> processingUnits;
    private FrameColorPreset frameColorPreset;

    @FXML
    private ColorPicker startColorPicker;

    @FXML
    private ColorPicker endColorPicker;

    @FXML
    private ChoiceBox<FrameSegment> frameSegmentChoiceBox;

    @FXML
    private ChoiceBox<Boolean> isReversedChoiceBox;

    @FXML
    private void initialize() {
        frameSegmentChoiceBox.setItems(FXCollections.observableArrayList(FrameSegment.values()));
        isReversedChoiceBox.setItems(FXCollections.observableArrayList(true, false));

        isReversedChoiceBox.setValue(false);
        frameSegmentChoiceBox.setValue(FrameSegment.TOP);
    }

    @Override
    public boolean isOkClicked() {

        return isOkClicked;
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        init();
    }

    private void init() {

    }

    @Override
    public void handleOk() {
        isOkClicked = true;
        createGradient();
        dialogStage.close();
    }

    @Override
    public void handleApply() {
        createGradient();

        try {
            frameColorPreset.saveColorState();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainWorker.restart();
    }

    private void createGradient() {

        FrameSegment selectedSegment = frameSegmentChoiceBox.getValue();
        boolean isReversed = isReversedChoiceBox.getValue();
        int ledCount = 1;
        int startLed = -1;
        int endLed;

        Color startColor = startColorPicker.getValue();
        Color endColor = endColorPicker.getValue();

        int startIndex = !isReversed ? 0 : processingUnits.size() - 1;
        int endIndex = !isReversed ? processingUnits.size() -1 : 0;
        int step = !isReversed ? 1 : -1;

        double deltaRed;
        double deltaGreen;
        double deltaBlue;
        int deltaLed;

        for (int i = startIndex; i != endIndex; i += step) {
            if (processingUnits.get(i).getAssociatedFrameSegment() == selectedSegment) {
                if (startLed == -1) {
                    startLed = processingUnits.get(i).getLedIndex();
                }

                ledCount++;
            }
        }

        deltaRed = (endColor.getRed() - startColor.getRed()) / ledCount;
        deltaGreen = (endColor.getGreen() - startColor.getGreen()) / ledCount;
        deltaBlue = (endColor.getBlue() - startColor.getBlue()) / ledCount;

        endLed = startLed + step * ledCount;

        for (int i = startLed; i != endLed; i +=step) {
            deltaLed = Math.abs(i - startLed);
            double newRed = startColor.getRed() + deltaRed * deltaLed;
            double newGreen = startColor.getGreen() + deltaGreen * deltaLed;
            double newBlue = startColor.getBlue() + deltaBlue * deltaLed;
            Color newColor = Color.color(
                    newRed,
                    newGreen,
                    newBlue
            );

            frameColorPreset.getIndividualColors().set(i, newColor.toString());
        }

    }

    @Override
    public void handleCancel() {
        dialogStage.close();
    }

    public void setProcessingUnits(List<AmbientProcessingUnit> processingUnits) {
        this.processingUnits = processingUnits;
    }

    public void setFrameColorPreset(FrameColorPreset frameColorPreset) {
        this.frameColorPreset = frameColorPreset;
    }
}
