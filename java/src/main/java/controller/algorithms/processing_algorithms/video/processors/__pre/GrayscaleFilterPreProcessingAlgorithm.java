package controller.algorithms.processing_algorithms.video.processors.__pre;

import controller.processing_units.units.ProcessingUnit;
import controller.processing_units.units.VideoProcessingUnit;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GrayscaleFilterPreProcessingAlgorithm implements VideoPreProcessingAlgorithm {
    @Override
    public void preProcess(ProcessingUnit processingUnit) {
        VideoProcessingUnit updatedUnit = (VideoProcessingUnit) processingUnit;

        BufferedImage updatedImage = updatedUnit.getImageChunk();

        int xStop = updatedUnit.getWidth();
        int yStop = updatedUnit.getHeight();

        for (int x = 0; x < xStop; x++) {
            for (int y = 0; y < yStop; y++) {
                int red = (updatedImage.getRGB(x, y) >> 16) & 0xFF;
                int green = (updatedImage.getRGB(x, y) >> 8) & 0xFF;
                int blue = updatedImage.getRGB(x, y) & 0xFF;
                int mean = (int)(0.2126 * red + 0.7152 * green + 0.0722 * blue);
                updatedImage.setRGB(x, y, new Color(mean, mean, mean).getRGB());
            }
        }

        updatedUnit.setImageChunk(updatedImage);
    }
}
