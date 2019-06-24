package controller.processing_units.units;

import controller.algorithms.processing_algorithms.PostProcessingAlgorithm;
import controller.algorithms.processing_algorithms.PreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.ProcessingAlgorithm;
import javafx.scene.paint.Color;
import model.config.enums.general.FrameSegment;

import java.util.ArrayList;
import java.util.List;


public abstract class ProcessingUnit {

    protected int associatedLedIndex;
    protected Color ledCurrentColor;
    protected FrameSegment associatedFrameSegment;

    protected List<PreProcessingAlgorithm> preProcessingAlgorithms;
    protected List<ProcessingAlgorithm> processingAlgorithms;
    protected List<PostProcessingAlgorithm> postProcessingAlgorithms;

    public ProcessingUnit(int ledIndex, FrameSegment associatedFrameSegment) {
        associatedLedIndex = ledIndex;
        this.associatedFrameSegment = associatedFrameSegment;
        preProcessingAlgorithms = new ArrayList<>();
        processingAlgorithms = new ArrayList<>();
        postProcessingAlgorithms = new ArrayList<>();
    }

    public abstract Color process();

    public int getLedIndex() {
        return associatedLedIndex;
    }

    public void setAssociatedLedIndex(int associatedLedIndex) {
        this.associatedLedIndex = associatedLedIndex;
    }

    public Color getLedCurrentColor() {
        return ledCurrentColor;
    }

    public void setLedCurrentColor(Color ledCurrentColor) {
        this.ledCurrentColor = ledCurrentColor;
    }

    public List<PreProcessingAlgorithm> getPreProcessingAlgorithms() {
        return preProcessingAlgorithms;
    }

    public List<ProcessingAlgorithm> getProcessingAlgorithms() {
        return processingAlgorithms;
    }

    public List<PostProcessingAlgorithm> getPostProcessingAlgorithms() {
        return postProcessingAlgorithms;
    }

    public FrameSegment getAssociatedFrameSegment() {
        return associatedFrameSegment;
    }

    public void setAssociatedFrameSegment(FrameSegment associatedFrameSegment) {
        this.associatedFrameSegment = associatedFrameSegment;
    }
}
