package controller.algorithms.processing_algorithms.video.processors.post;

import com.sun.javafx.util.Utils;
import controller.processing_units.units.ProcessingUnit;
import controller.processing_units.units.VideoProcessingUnit;
import javafx.scene.paint.Color;
import model.config.ConfigManager;


public class BrightnessRegulatorPostProcessingAlgorithm implements VideoPostProcessingAlgorithm {

    private VideoProcessingUnit updatedUnit;
    private float brightnessModifier;
    private Color currentColor;
    private double[] hsbColor;

    @Override
    public void postProcess(ProcessingUnit processingUnit) {
        updatedUnit = (VideoProcessingUnit) processingUnit;
        brightnessModifier = ConfigManager.getVideoConfig().getBrightness();

        currentColor = updatedUnit.getLedCurrentColor();

        hsbColor = Utils.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());

        hsbColor[2] = brightnessModifier * hsbColor[2];
        hsbColor[2] = hsbColor[2] > 1 ? 1 : hsbColor[2];

        updatedUnit.setLedCurrentColor(Color.hsb(hsbColor[0], hsbColor[1], hsbColor[2]));
    }
}
