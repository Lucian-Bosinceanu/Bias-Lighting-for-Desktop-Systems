package controller.ui.alerts;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AlertBuilder {

    private Alert alert;


    public AlertBuilder() {
        alert = new Alert(Alert.AlertType.NONE);
    }

    public AlertBuilder setAlertType(Alert.AlertType type) {
        alert.setAlertType(type);
        return this;
    }

    public AlertBuilder setTitle(String title) {
        alert.setTitle(title);
        return this;
    }

    public AlertBuilder setHeader(String header) {
        alert.setHeaderText(header);
        return this;
    }

    public AlertBuilder setContent(String content) {
        alert.setContentText(content);
        return this;
    }

    public AlertBuilder setOwner(Window owner) {
        alert.initOwner(owner);
        return this;
    }

    public Alert build() {
        return alert;
    }
}
