package controller.processing_units.units;

import controller.algorithms.processing_algorithms.PostProcessingAlgorithm;
import controller.algorithms.processing_algorithms.PreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.ProcessingAlgorithm;
import javafx.scene.paint.Color;
import model.config.enums.general.FrameSegment;

public class AmbientProcessingUnit extends ProcessingUnit {

    private Color baseColor;
    private Color nextColor;
    private int xOrigin;
    private int yOrigin;
    private int width;
    private int height;
    private double lastIntensity;
    private boolean isAnimationFinished;

    public AmbientProcessingUnit(int xOrigin, int yOrigin, int width, int height, int ledIndex, FrameSegment associatedFrameSegment) {
        super(ledIndex, associatedFrameSegment);
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.width = width;
        this.height = height;
        baseColor = Color.BLACK;
        ledCurrentColor = Color.BLACK;
        nextColor = Color.BLACK;
        lastIntensity = 0;
        isAnimationFinished = false;
    }

    @Override
    public Color process(){
        for (PreProcessingAlgorithm algorithm : preProcessingAlgorithms) {
            algorithm.preProcess(this);
        }

        for (ProcessingAlgorithm algorithm : processingAlgorithms) {
            algorithm.process(this);
        }

        for (PostProcessingAlgorithm algorithm : postProcessingAlgorithms) {
            algorithm.postProcess(this);
        }

        return ledCurrentColor;
    }

    public int getxOrigin() {
        return xOrigin;
    }

    public void setxOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    public int getyOrigin() {
        return yOrigin;
    }

    public void setyOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public Color getNextColor() {
        return nextColor;
    }

    public void setNextColor(Color nextColor) {
        this.nextColor = nextColor;
    }

    public void swapBaseAndNextColors() {
        Color aux = baseColor;
        baseColor = nextColor;
        nextColor = aux;
    }

    public double getLastIntensity() {
        return lastIntensity;
    }

    public void setLastIntensity(double lastIntensity) {
        this.lastIntensity = lastIntensity;
    }

    public void setAnimationFinished(boolean b) {
        isAnimationFinished = b;
    }

    public boolean isAnimationFinished() {
        return isAnimationFinished;
    }
}
