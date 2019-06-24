package controller.algorithms.processing_algorithms.video.processors.__pre;

import controller.algorithms.processing_algorithms.PreProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface VideoPreProcessingAlgorithm extends PreProcessingAlgorithm {

    void preProcess(ProcessingUnit processingUnit);
}
