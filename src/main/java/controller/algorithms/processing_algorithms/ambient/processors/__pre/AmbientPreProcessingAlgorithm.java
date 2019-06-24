package controller.algorithms.processing_algorithms.ambient.processors.__pre;

import controller.algorithms.processing_algorithms.PreProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface AmbientPreProcessingAlgorithm extends PreProcessingAlgorithm {

    void preProcess(ProcessingUnit processingUnit);
}
