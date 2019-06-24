package controller.algorithms.processing_algorithms.video.processors.post.frame_update;

import controller.algorithms.processing_algorithms.video.processors.post.VideoPostProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;
import controller.processing_units.units.VideoProcessingUnit;
import javafx.scene.paint.Color;
import model.config.ConfigManager;


public class LazyFrameUpdatePostProcessingAlgorithm implements VideoPostProcessingAlgorithm {

    private VideoProcessingUnit updatedUnit;

    private Color currentColor;
    private Color previousColor;

    private int range;
    private int deltaRed;
    private int deltaGreen;
    private int deltaBlue;

    @Override
    public void postProcess(ProcessingUnit processingUnit) {
        updatedUnit = (VideoProcessingUnit) processingUnit;
        currentColor = updatedUnit.getLedCurrentColor();
        previousColor = updatedUnit.getPreviousColor();
        range = ConfigManager.getVideoConfig().getLazyUpdateStyleComfortRange();

        deltaRed = (int)(Math.abs(currentColor.getRed() - previousColor.getRed()) * 255);
        deltaGreen = (int)(Math.abs(currentColor.getGreen() - previousColor.getGreen()) * 255);
        deltaBlue = (int)(Math.abs(currentColor.getBlue() - previousColor.getBlue()) * 255);

        if (deltaRed < range && deltaGreen < range && deltaBlue < range) {
            updatedUnit.setLedCurrentColor(previousColor);
        }

    }
}
