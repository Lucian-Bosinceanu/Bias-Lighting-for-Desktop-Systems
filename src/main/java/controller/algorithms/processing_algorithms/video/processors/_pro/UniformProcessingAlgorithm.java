package controller.algorithms.processing_algorithms.video.processors._pro;

import controller.processing_units.units.ProcessingUnit;
import controller.processing_units.units.VideoProcessingUnit;
import controller.source.SourceManager;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class UniformProcessingAlgorithm implements VideoProcessingAlgorithm {

    private VideoProcessingUnit updatedUnit;
    private int red;
    private int green;
    private int blue;
    private int xStart;
    private int xStop;
    private int yStart;
    private int yStop;
    private int pixelCount;
    private BufferedImage currentImage;
    int pixelRGB;


    @Override
    public void process(ProcessingUnit processingUnit) {
        updatedUnit = (VideoProcessingUnit) processingUnit;
        red = 0;
        green = 0;
        blue = 0;
        xStart = updatedUnit.getxOrigin();
        xStop = xStart + updatedUnit.getWidth();
        yStart = updatedUnit.getyOrigin();
        yStop = yStart + updatedUnit.getHeight();
        pixelCount = 0;
        currentImage = SourceManager.getVideoSource().getSource();

        for (int x = xStart; x < xStop; ++x) {
            for (int y = yStart; y < yStop; ++y) {
                pixelCount++;
                pixelRGB = currentImage.getRGB(x, y);
                red += ((pixelRGB >> 16) & 0xFF);
                green += ((pixelRGB >> 8) & 0xFF);
                blue += (pixelRGB & 0xFF);
            }
        }

        red = red / pixelCount;
        green = green / pixelCount;
        blue = blue / pixelCount;

        updatedUnit.setLedCurrentColor(Color.rgb(red, green, blue));
    }
}
