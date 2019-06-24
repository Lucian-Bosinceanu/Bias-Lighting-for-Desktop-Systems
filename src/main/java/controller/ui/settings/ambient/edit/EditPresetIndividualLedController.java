package controller.ui.settings.ambient.edit;

import com.sun.javafx.scene.control.skin.CustomColorDialog;
import controller.processing_units.units.AmbientProcessingUnit;
import controller.ui.settings.SettingsController;
import controller.workers.MainWorker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.FrameColorPreset;
import model.config.ConfigManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditPresetIndividualLedController implements SettingsController {

    private Stage dialogStage;
    private boolean okClicked = false;

    private Button okButton;
    private Button cancelButton;
    private Button applyButton;

    private Double customColorDialogWidth = 580.0;
    private Double customColorDialogHeight = 275.0;

    private Rectangle currentScreenSize = ConfigManager.getVideoConfig().getCurrentScreen();

    private List<Button> individualLedButtons;

    private List<AmbientProcessingUnit> processingUnits;
    private FrameColorPreset frameColorPreset;

    private void init() {
        dialogStage.getScene().getStylesheets().add(EditPresetIndividualLedController.class.getClassLoader().getResource("view/AppTheme.css").toExternalForm());
        int buttonWidth = (int) (currentScreenSize.width * 0.1);
        int buttonHeight = (int) (currentScreenSize.height * 0.05);
        int interButtonDistance = (int) (currentScreenSize.width * 0.01);

        int firstButtonOffsetX = (int) (currentScreenSize.width * 0.45);
        int firstButtonOffsetY = (int) (currentScreenSize.height * 0.415);

        okButton = createButton("Ok",
                firstButtonOffsetX,
                firstButtonOffsetY,
                buttonWidth,
                buttonHeight,
                event -> handleOk());

        applyButton = createButton("Apply",
                firstButtonOffsetX,
                firstButtonOffsetY + buttonHeight + interButtonDistance,
                buttonWidth,
                buttonHeight,
                event -> handleApply());

        cancelButton = createButton("Cancel",
                firstButtonOffsetX,
                firstButtonOffsetY + 2 * buttonHeight + 2 * interButtonDistance,
                buttonWidth,
                buttonHeight,
                event -> handleCancel());

        individualLedButtons = new ArrayList<>();

        for (AmbientProcessingUnit unit : processingUnits) {
            Button button = createButton(
                    String.valueOf(unit.getLedIndex()),
                    unit.getxOrigin(),
                    unit.getyOrigin(),
                    unit.getWidth(),
                    unit.getHeight(),
                    event -> handleIndividualButton(unit.getLedIndex())
            );
            individualLedButtons.add(button);
        }

        setButtonColors();

        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(okButton);
        pane.getChildren().add(applyButton);
        pane.getChildren().add(cancelButton);

        for (Button button : individualLedButtons) {
            pane.getChildren().add(button);
        }

        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
    }

    private void setButtonColors() {
        for (int i = 0; i < individualLedButtons.size(); i++) {
            Color color;
            try {
                color = Color.valueOf(frameColorPreset.getIndividualColors().get(i));
            } catch (IndexOutOfBoundsException e) {
                color = Color.BLACK;
            }
            setButtonStyle(individualLedButtons.get(i), color);
        }
    }

    private void setButtonStyle(Button button, Color backgroundColor) {
        double red = backgroundColor.getRed();
        double green = backgroundColor.getGreen();
        double blue = backgroundColor.getBlue();
        Color textColor = Color.color(1 - red, 1 - green, 1 - blue);
        button.setBackground(
                new Background(
                        new BackgroundFill(
                                backgroundColor,
                                CornerRadii.EMPTY,
                                Insets.EMPTY)));

        button.setTextFill(textColor);

        button.setBorder(new Border(
                new BorderStroke(
                        textColor,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderWidths.DEFAULT
                )));
    }


    private void handleIndividualButton(int index) {
        Color currentLedColor = Color.valueOf(frameColorPreset.getIndividualColors().get(index));

        CustomColorDialog customColorDialog = new CustomColorDialog(dialogStage);
        customColorDialog.getDialog().setTitle("LED " + index + " - Custom color");

        Point2D customDialogPosition = computePositionForCustomColorDialog(index);


        customColorDialog.getDialog().setX(customDialogPosition.getX());
        customColorDialog.getDialog().setY(customDialogPosition.getY());


        customColorDialog.setCurrentColor(currentLedColor);
        customColorDialog.getDialog().show();

        customColorDialog.setOnSave(() -> {
            Color newColor = customColorDialog.getCustomColor();
            frameColorPreset.getIndividualColors().set(index, newColor.toString());
            setButtonStyle(individualLedButtons.get(index), newColor);
        });

    }

    private Point2D computePositionForCustomColorDialog(int associatedButtonIndex) {
        List<Point2D> possibleStartingPositions = new ArrayList<>();
        Button associatedButton = individualLedButtons.get(associatedButtonIndex);
        double dialogWidth = customColorDialogWidth;
        double dialogHeight = customColorDialogHeight;

        //UPPER-RIGHT
        possibleStartingPositions.add(
                new Point2D(
                associatedButton.getLayoutX() + associatedButton.getWidth(),
                associatedButton.getLayoutY() - dialogHeight
        ));

        //LOWER-RIGHT
        possibleStartingPositions.add(
                new Point2D(
                        associatedButton.getLayoutX() + associatedButton.getWidth(),
                        associatedButton.getLayoutY() + associatedButton.getHeight())
        );

        //LOWER-LEFT
        possibleStartingPositions.add(
                new Point2D(
                        associatedButton.getLayoutX() - dialogWidth,
                        associatedButton.getLayoutY() + associatedButton.getHeight()
                )
        );

        //UPPER-LEFT
        possibleStartingPositions.add(
                new Point2D(
                        associatedButton.getLayoutX() - dialogWidth,
                        associatedButton.getLayoutY() - dialogHeight)
        );

        for (Point2D point : possibleStartingPositions) {
            if (isPointOnScreen(point) && isPointOnScreen(new Point2D(point.getX() + dialogWidth, point.getY() + dialogHeight))) {
                return point;
            }
        }

        return possibleStartingPositions.get(0);
    }

    private boolean isPointOnScreen(Point2D point) {
        double x = point.getX();
        double y = point.getY();

        return x >= 0 && x < currentScreenSize.getWidth() && y >= 0 && y < currentScreenSize.getHeight();
    }

    private Button createButton(String text, double startX, double startY, double width, double height, EventHandler<ActionEvent> event) {
        Button button = new Button(text);
        button.setMinWidth(width);
        button.setMinHeight(height);
        button.setLayoutX(startX);
        button.setLayoutY(startY);
        button.setVisible(true);
        button.setOnAction(event);
        return button;
    }

    @Override
    public boolean isOkClicked() {
        return okClicked;
    }

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        init();
    }

    @Override
    public void handleOk() {
        okClicked = true;
        dialogStage.close();
    }

    @Override
    public void handleApply() {
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

    void setProcessingUnits(List<AmbientProcessingUnit> processingUnits) {
        this.processingUnits = processingUnits;
    }

    void setFrameColorPreset(FrameColorPreset frameColorPreset) {
        this.frameColorPreset = frameColorPreset;
    }
}
