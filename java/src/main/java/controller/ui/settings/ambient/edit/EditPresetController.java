package controller.ui.settings.ambient.edit;

import controller.frame.FrameManager;
import controller.processing_units.units.AmbientProcessingUnit;
import controller.ui.SettingsViewManager;
import controller.ui.settings.ComponentInitializer;
import controller.ui.settings.SettingsController;
import controller.workers.MainWorker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FrameColorPreset;
import model.config.ConfigManager;
import model.config.enums.general.FrameMode;
import model.config.enums.general.FrameSegment;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EditPresetController extends ComponentInitializer implements SettingsController {

    private Stage dialogStage;
    private boolean okClicked = false;

    private List<AmbientProcessingUnit> processingUnits;
    private FrameColorPreset frameColorPreset;

    @FXML
    private ColorPicker allLedsColorPicker;

    @FXML
    private ColorPicker leftSegmentColorPicker;

    @FXML
    private ColorPicker topSegmentColorPicker;

    @FXML
    private ColorPicker rightSegmentColorPicker;

    @FXML
    private ColorPicker bottomSegmentColorPicker;


    @Override
    public boolean isOkClicked() {
        return okClicked;
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize(){
        init();
    }

    private void init() {
        initProcessingUnits();
        loadPreset(ConfigManager.getAmbientConfig().getFramePresetName());

        allLedsColorPicker.setOnAction(event -> {
            frameColorPreset.setBaseColor(allLedsColorPicker.getValue().toString());
            changeFrameColor(allLedsColorPicker.getValue());
        });

        leftSegmentColorPicker.setOnAction(event -> {
            frameColorPreset.setLeftSegmentColor(leftSegmentColorPicker.getValue().toString());
            changeSegmentColor(FrameSegment.LEFT, leftSegmentColorPicker.getValue());
        });

        topSegmentColorPicker.setOnAction(event -> {
            frameColorPreset.setTopSegmentColor(topSegmentColorPicker.getValue().toString());
            changeSegmentColor(FrameSegment.TOP, topSegmentColorPicker.getValue());
        });

        rightSegmentColorPicker.setOnAction(event -> {
            frameColorPreset.setRightSegmentColor(rightSegmentColorPicker.getValue().toString());
            changeSegmentColor(FrameSegment.RIGHT, rightSegmentColorPicker.getValue());
        });

        bottomSegmentColorPicker.setOnAction(event -> {
            frameColorPreset.setBottomSegmentColor(bottomSegmentColorPicker.getValue().toString());
            changeSegmentColor(FrameSegment.BOTTOM, bottomSegmentColorPicker.getValue());
        });


    }

    private void initProcessingUnits() {
        //VideoProcessingDepth currentDepth = ConfigManager.getVideoConfig().getProcessingDepth();
        //ConfigManager.getVideoConfig().setProcessingDepth(VideoProcessingDepth.MARGIN);
        ConfigManager.getGlobalConfig().setFrameMode(FrameMode.AMBIENT);
        FrameManager frameManager = new FrameManager();
        frameManager.constructProcessingUnits();
        //ConfigManager.getVideoConfig().setProcessingDepth(currentDepth);

        processingUnits = frameManager.getProcessingUnits()
                .stream()
                .map(e -> (AmbientProcessingUnit)e)
                .collect(Collectors.toList());
    }

    private void loadPreset(String presetName) {
        try {
            frameColorPreset = FrameColorPreset.loadColorState(presetName);
            while (frameColorPreset.getIndividualColors().size() < processingUnits.size()) {
                frameColorPreset.getIndividualColors().add(Color.BLACK.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        allLedsColorPicker.setValue(Color.valueOf(frameColorPreset.getBaseColor()));
        leftSegmentColorPicker.setValue(Color.valueOf(frameColorPreset.getLeftSegmentColor()));
        topSegmentColorPicker.setValue(Color.valueOf(frameColorPreset.getTopSegmentColor()));
        rightSegmentColorPicker.setValue(Color.valueOf(frameColorPreset.getRightSegmentColor()));
        bottomSegmentColorPicker.setValue(Color.valueOf(frameColorPreset.getBottomSegmentColor()));

        allLedsColorPicker.setDisable(!frameColorPreset.getEditable());
        leftSegmentColorPicker.setDisable(!frameColorPreset.getEditable());
        topSegmentColorPicker.setDisable(!frameColorPreset.getEditable());
        rightSegmentColorPicker.setDisable(!frameColorPreset.getEditable());
        bottomSegmentColorPicker.setDisable(!frameColorPreset.getEditable());
    }

    private void changeSegmentColor(FrameSegment segment, Color newColor) {
        for (int i = 0; i < processingUnits.size(); i++) {
            if (processingUnits.get(i).getAssociatedFrameSegment() == segment) {
                frameColorPreset.getIndividualColors().set(i, newColor.toString());
            }
        }
    }

    private void changeFrameColor(Color newColor) {
        for (int i = 0; i < frameColorPreset.getIndividualColors().size(); i++) {
            frameColorPreset.getIndividualColors().set(i, newColor.toString());
        }

        leftSegmentColorPicker.setValue(newColor);
        frameColorPreset.setLeftSegmentColor(newColor.toString());

        topSegmentColorPicker.setValue(newColor);
        frameColorPreset.setTopSegmentColor(newColor.toString());

        rightSegmentColorPicker.setValue(newColor);
        frameColorPreset.setRightSegmentColor(newColor.toString());

        bottomSegmentColorPicker.setValue(newColor);
        frameColorPreset.setBottomSegmentColor(newColor.toString());
    }

    @Override
    public void handleOk() {
        okClicked = true;
        dialogStage.close();
    }

    @Override
    public void handleApply() {
        ConfigManager.getGlobalConfig().setFrameMode(FrameMode.AMBIENT);

        try {
            frameColorPreset.saveColorState();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainWorker.restart();
    }

    @Override
    public void handleCancel() {
        dialogStage.close();
    }


    public void loadIndividualLedController() {
        EditPresetIndividualLedController individualLedController = new EditPresetIndividualLedController();
        individualLedController.setProcessingUnits(processingUnits);
        individualLedController.setFrameColorPreset(frameColorPreset);

        boolean individualLedControllerOkClicked = SettingsViewManager.showSettingsDialog(
                SettingsViewManager.STATIC_SETTINGS_INDIVIDUAL_LED,
                individualLedController,
                "Change individual LEDs color",
                false,
                true
        );

        if (individualLedControllerOkClicked) {
            handleApply();
        }
    }


    public void loadCreateGradientController() {

        boolean createGradientControllerOkClicked = instantiateCreateGradientController();

        if (createGradientControllerOkClicked) {
            handleApply();
        }
    }

    public boolean instantiateCreateGradientController() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SettingsViewManager.class.getClassLoader().getResource(SettingsViewManager.STATIC_SETTINGS_CREATE_GRADIENT));
            AnchorPane dialog = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create gradient");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.dialogStage);
            Scene scene = new Scene(dialog);

            dialogStage.setResizable(false);
            dialogStage.setMaximized(false);
            dialogStage.setScene(scene);

            EditPresetCreateGradientController controller = loader.getController();
            controller.setProcessingUnits(processingUnits);
            controller.setFrameColorPreset(frameColorPreset);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
