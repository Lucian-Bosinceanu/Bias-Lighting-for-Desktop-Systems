package controller.ui.settings;

import controller.exceptions.InvalidTextFieldValue;
import controller.exceptions.MyException;
import controller.frame.assembler.LedCountComputer;
import controller.ui.alerts.AlertManager;
import controller.workers.MainWorker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jssc.SerialPortList;
import model.config.ConfigManager;
import model.config.enums.general.FrameConstructionType;
import model.config.enums.general.FrameDirection;
import model.config.enums.general.FrameStartPosition;
import model.config.enums.general.StripRgbOrder;


public class GeneralSettingsController extends ComponentInitializer implements SettingsController {

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private TextField widthLedCountField;

    @FXML
    private TextField heightLedCountField;

    @FXML
    private ChoiceBox<FrameConstructionType> frameConstructionTypeChoiceBox;

    @FXML
    private ChoiceBox<FrameStartPosition> frameStartPositionChoiceBox;

    @FXML
    private ChoiceBox<FrameDirection> frameDirectionChoiceBox;

    @FXML
    private ChoiceBox<String> serialPortChoiceBox;

    @FXML
    private ChoiceBox<StripRgbOrder> stripRgbOrderChoiceBox;

    @Override
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void initialize() {

        initNumberTextField(
                widthLedCountField,
                ConfigManager.getGlobalConfig().getWidth(),
                false
        );

        if (ConfigManager.getGlobalConfig().getConstructionType() == FrameConstructionType.SIDE_ONLY) {
            widthLedCountField.setDisable(true);
        }

        initNumberTextField(
                heightLedCountField,
                ConfigManager.getGlobalConfig().getHeight(),
                false
        );


        frameConstructionTypeChoiceBox.setItems(FXCollections.observableArrayList(FrameConstructionType.values()));
        frameConstructionTypeChoiceBox.setValue(ConfigManager.getGlobalConfig().getConstructionType());
        frameConstructionTypeChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (frameConstructionTypeChoiceBox.getValue().equals(FrameConstructionType.SIDE_ONLY)) {
                    widthLedCountField.setDisable(true);
                } else {
                    widthLedCountField.setDisable(false);
                }
            }
        });


        frameStartPositionChoiceBox.setItems(FXCollections.observableArrayList(FrameStartPosition.values()));
        frameStartPositionChoiceBox.setValue(ConfigManager.getGlobalConfig().getStartPosition());

        frameDirectionChoiceBox.setItems(FXCollections.observableArrayList(FrameDirection.values()));
        frameDirectionChoiceBox.setValue(ConfigManager.getGlobalConfig().getFrameDirection());

        serialPortChoiceBox.setItems(FXCollections.observableArrayList(SerialPortList.getPortNames()));
        serialPortChoiceBox.setValue(ConfigManager.getGlobalConfig().getSerialPortName());

        stripRgbOrderChoiceBox.setItems(FXCollections.observableArrayList(StripRgbOrder.values()));
        stripRgbOrderChoiceBox.setValue(ConfigManager.getGlobalConfig().getStripRgbOrder());

        okClicked = false;
    }


    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    @FXML
    public void handleOk() {
        if (isValidInput()) {
            updateConfigs();
            okClicked = true;
            dialogStage.close();
        }
    }

    private void updateConfigs() {
        ConfigManager.getGlobalConfig().setWidth(Integer.valueOf(widthLedCountField.getText()));
        ConfigManager.getGlobalConfig().setHeight(Integer.valueOf(heightLedCountField.getText()));
        ConfigManager.getGlobalConfig().setConstructionType(frameConstructionTypeChoiceBox.getValue());
        ConfigManager.getGlobalConfig().setStartPosition(frameStartPositionChoiceBox.getValue());
        ConfigManager.getGlobalConfig().setFrameDirection(frameDirectionChoiceBox.getValue());
        ConfigManager.getGlobalConfig().setSerialPortName(serialPortChoiceBox.getValue());
        ConfigManager.getGlobalConfig().setStripRgbOrder(stripRgbOrderChoiceBox.getValue());
    }

    private boolean isValidInput() {

        try {
            int widthLedCount = Integer.valueOf(widthLedCountField.getText());
            int heightLedCount = Integer.valueOf(heightLedCountField.getText());
            int stripLedCount = LedCountComputer.compute(frameConstructionTypeChoiceBox.getValue(), widthLedCount, heightLedCount);

            if (widthLedCount == 0) {
                throw new InvalidTextFieldValue(
                        "Input error!",
                        "Invalid Width Led Count value!",
                        "Width Led Count cannot be 0!"
                );
            }

            if (heightLedCount == 0) {
                throw new InvalidTextFieldValue(
                        "Input error!",
                        "Invalid Height Led Count value!",
                        "Height Led Count cannot be 0!"
                );
            }

            if (stripLedCount > 255) {
                throw new MyException("Input error!",
                        "Invalid Led Count values!",
                        "Too many total LEDs! Maximum limit is 255.");
            }

            return true;
        } catch (NumberFormatException e) {
            AlertManager.showCustomError(
                    "Input error!",
                    "Invalid Led Count values!",
                    "Led count text fields must contain integer numbers!");
            return false;
        } catch (MyException e) {
            AlertManager.showError(e);
            return false;
        }
    }

    @Override
    @FXML
    public void handleApply() {
        if (isValidInput()) {
            updateConfigs();
            ConfigManager.saveProperties();
            MainWorker.restart();
        }
    }

    @Override
    @FXML
    public void handleCancel() {
        dialogStage.close();
    }
}
