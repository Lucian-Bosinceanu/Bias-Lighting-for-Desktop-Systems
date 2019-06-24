package controller.processing_units.units;

import controller.algorithms.processing_algorithms.PostProcessingAlgorithm;
import controller.algorithms.processing_algorithms.PreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.ProcessingAlgorithm;
import javafx.scene.paint.Color;
import model.config.enums.general.FrameSegment;

import java.awt.image.BufferedImage;

public class VideoProcessingUnit extends ProcessingUnit{

    private int xOrigin;
    private int yOrigin;
    private int width;
    private int height;

    private Color previousColor;
    private BufferedImage imageChunk;

    public VideoProcessingUnit(int xOrigin, int yOrigin, int width, int height, int ledIndex, FrameSegment associatedFrameSegment) {
        super(ledIndex, associatedFrameSegment);
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.width = width;
        this.height = height;
        ledCurrentColor = Color.color(0, 0, 0);
        previousColor = Color.color(0, 0, 0);
    }

    public Color process() {
        //System.out.println(associatedLedIndex + " " + associatedFrameSegment.toString() + " " + xOrigin + " " + yOrigin + " " + width + " " + height);
        //this.imageChunk = SourceManager.getVideoSource().getImageChunk(xOrigin, yOrigin, width, height);
        for (PreProcessingAlgorithm algorithm : preProcessingAlgorithms) {
            algorithm.preProcess(this);
            //updateProcessingUnit(updatedUnit);
        }

        for (ProcessingAlgorithm algorithm : processingAlgorithms) {
            algorithm.process(this);
            //updateProcessingUnit(updatedUnit);
        }

        for (PostProcessingAlgorithm algorithm : postProcessingAlgorithms) {
            algorithm.postProcess(this);
            //updateProcessingUnit(updatedUnit);
        }

        previousColor = ledCurrentColor;
        return ledCurrentColor;
    }

    public BufferedImage getImageChunk() {
        return imageChunk;
    }

    public int getxOrigin() {
        return xOrigin;
    }

    public int getyOrigin() {
        return yOrigin;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setImageChunk(BufferedImage imageChunk) {
        this.imageChunk = imageChunk;
    }

    public Color getPreviousColor() {
        return previousColor;
    }

    public void setPreviousColor(Color previousColor) {
        this.previousColor = previousColor;
    }

    public void setxOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    public void setyOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
