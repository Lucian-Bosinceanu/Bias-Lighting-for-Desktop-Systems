package controller.algorithms.processing_algorithms.video.processors.post.frame_update;

import controller.algorithms.processing_algorithms.video.processors.post.VideoPostProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;
import controller.processing_units.units.VideoProcessingUnit;
import javafx.scene.paint.Color;
import model.config.ConfigManager;


public class GradientFrameUpdatePostProcessingAlgorithm implements VideoPostProcessingAlgorithm {

    private VideoProcessingUnit updatedUnit;

    private double deltaRed;
    private double deltaGreen;
    private double deltaBlue;

    private double updatedRed;
    private double updatedGreen;
    private double updatedBlue;

    private double smoothFactor;

    private Color currentColor;
    private Color previousColor;

    @Override
    public void postProcess(ProcessingUnit processingUnit) {
        updatedUnit = (VideoProcessingUnit) processingUnit;
        currentColor = updatedUnit.getLedCurrentColor();
        previousColor = updatedUnit.getPreviousColor();
        smoothFactor = ConfigManager.getVideoConfig().getSmoothFactor();

        deltaRed = currentColor.getRed() - previousColor.getRed();
        deltaGreen = currentColor.getGreen() - previousColor.getGreen();
        deltaBlue = currentColor.getBlue() - previousColor.getBlue();

        updatedRed = previousColor.getRed() + deltaRed * smoothFactor;
        updatedGreen = previousColor.getGreen() + deltaGreen * smoothFactor;
        updatedBlue = previousColor.getBlue() + deltaBlue * smoothFactor;

        updatedUnit.setLedCurrentColor(Color.color(updatedRed, updatedGreen, updatedBlue));
    }
}
