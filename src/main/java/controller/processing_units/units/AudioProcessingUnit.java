package controller.processing_units.units;

import controller.algorithms.processing_algorithms.PostProcessingAlgorithm;
import controller.algorithms.processing_algorithms.PreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.ProcessingAlgorithm;
import controller.source.SourceManager;
import javafx.scene.paint.Color;
import model.config.enums.general.FrameSegment;

public class AudioProcessingUnit extends ProcessingUnit{

    private double sensibility;
    private double currentSignal;
    private Color baseColor;
    private Color lastColor;
    private Color nextColor;

    private double lastIntensity;
    private boolean isAnimationFinished;

    public AudioProcessingUnit(int ledIndex, FrameSegment associatedFrameSegment) {
        super(ledIndex, associatedFrameSegment);
        lastColor = Color.BLACK;
        baseColor = Color.BLACK;
        nextColor = Color.BLACK;
        isAnimationFinished = false;
        lastIntensity = 0;
    }

    @Override
    public Color process() {
        currentSignal = SourceManager.getAudioSource().getSource();
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

        lastColor = ledCurrentColor;
        return ledCurrentColor;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    public double getSensibility() {
        return sensibility;
    }

    public void setSensibility(double sensibility) {
        this.sensibility = sensibility;
    }

    public double getCurrentSignal() {
        return currentSignal;
    }

    public Color getLastColor() {
        return lastColor;
    }

    public void setNextColor(Color color) {
        nextColor = color;
    }

    public Color getNextColor() {
        return nextColor;
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

    public void swapBaseAndNextColors() {
        Color aux = baseColor;
        baseColor = nextColor;
        nextColor = aux;
    }

    public void setLastColor(Color updatedColor) {
        lastColor = updatedColor;
    }
}
