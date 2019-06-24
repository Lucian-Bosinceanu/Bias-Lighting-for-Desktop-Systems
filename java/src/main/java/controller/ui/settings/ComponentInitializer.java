package controller.ui.settings;

import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class ComponentInitializer {

    public void initSlider(Slider slider, double min, double max, double blockIncrement, double majorTickUnit, double initialValue) {
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(blockIncrement);
        slider.setMajorTickUnit(majorTickUnit);
        slider.setValue(initialValue);
    }

    public void initNumberTextField(TextField textField, long number, boolean disabledState) {
        textField.setText(String.valueOf(number));
        textField.setDisable(disabledState);
        textField.setTextFormatter(new TextFormatter<>(c -> {
            if (!c.getControlNewText().matches("\\d*")) {
                return null;
            } else {
                return c;
            }
        }));
    }
}
