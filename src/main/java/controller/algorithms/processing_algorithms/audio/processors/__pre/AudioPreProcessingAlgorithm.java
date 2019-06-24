package controller.algorithms.processing_algorithms.audio.processors.__pre;

import controller.algorithms.processing_algorithms.PreProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface AudioPreProcessingAlgorithm extends PreProcessingAlgorithm {

    void preProcess(ProcessingUnit processingUnit);
}
