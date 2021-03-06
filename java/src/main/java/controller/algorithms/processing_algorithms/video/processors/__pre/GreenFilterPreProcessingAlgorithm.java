package controller.algorithms.processing_algorithms.video.processors.__pre;

import controller.processing_units.units.ProcessingUnit;
import controller.processing_units.units.VideoProcessingUnit;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GreenFilterPreProcessingAlgorithm implements VideoPreProcessingAlgorithm {
    @Override
    public void preProcess(ProcessingUnit processingUnit) {
        VideoProcessingUnit updatedUnit = (VideoProcessingUnit) processingUnit;

        BufferedImage updatedImage = updatedUnit.getImageChunk();

        int xStop = updatedUnit.getWidth();
        int yStop = updatedUnit.getHeight();

        for (int x = 0; x < xStop; x++) {
            for (int y = 0; y < yStop; y++) {
                int green = (updatedImage.getRGB(x, y) >> 8) & 0xFF;
                updatedImage.setRGB(x, y, new Color(green, green, green).getRGB());
            }
        }

        updatedUnit.setImageChunk(updatedImage);
    }
}
