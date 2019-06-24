package controller.ui.settings;

import javafx.stage.Stage;

public interface SettingsController {

    boolean isOkClicked();
    void setDialogStage(Stage dialogStage);
    void handleOk();
    void handleApply();
    void handleCancel();
}
