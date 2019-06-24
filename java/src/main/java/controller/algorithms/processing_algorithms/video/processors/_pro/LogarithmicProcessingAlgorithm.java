package controller.algorithms.processing_algorithms.video.processors._pro;

import controller.processing_units.units.ProcessingUnit;
import controller.processing_units.units.VideoProcessingUnit;
import controller.source.SourceManager;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class LogarithmicProcessingAlgorithm implements VideoProcessingAlgorithm {

    private VideoProcessingUnit updatedUnit;

    private static final int linDirection[] = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    private static final int colDirection[] = {0, 0, 1, 1, 1, 0, -1, -1, -1};
    private BufferedImage currentImage;

    private double red;
    private double green;
    private double blue;

    private int xStop;
    private int yStop;

    private int xStep;
    private int yStep;

    private int pixelCount;

    private Color pixelColor;

    private int x;
    private int y;

    @Override
    public void process(ProcessingUnit processingUnit) {
        updatedUnit = (VideoProcessingUnit) processingUnit;
        red = 0;
        green = 0;
        blue = 0;
        x = updatedUnit.getxOrigin() + 1;
        y = updatedUnit.getyOrigin() + 1;
        xStop = x + updatedUnit.getWidth();
        yStop = y + updatedUnit.getHeight();
        pixelCount = 0;
        currentImage = SourceManager.getVideoSource().getSource();//updatedUnit.getImageChunk();

        xStep = (int) (updatedUnit.getWidth() / Math.log10(updatedUnit.getWidth()));
        yStep = (int) (updatedUnit.getHeight() / Math.log10(updatedUnit.getHeight()));

        for (; x < xStop - 1; x += xStep) {
            for (; y < yStop - 1; y += yStep) {
                pixelCount++;
                pixelColor = getNeighboursMeanColor(x, y);
                red += pixelColor.getRed();
                green += pixelColor.getGreen();
                blue += pixelColor.getBlue();
            }
        }

        red = red / pixelCount;
        green = green / pixelCount;
        blue = blue / pixelCount;

        updatedUnit.setLedCurrentColor(Color.color(red, green, blue));
    }

    private Color getNeighboursMeanColor(int x, int y) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int dir;
        int rgb;

        for (dir = 0; dir <= 8; dir++) {
            rgb = currentImage.getRGB(x + linDirection[dir], y + colDirection[dir]);
            red += (rgb >> 16) & 0xFF;
            green += (rgb >> 8) & 0xFF;
            blue += rgb & 0xFF;
        }

        red /= 9;
        green /= 9;
        blue /= 9;

        return Color.rgb(red, green, blue);
    }
}
