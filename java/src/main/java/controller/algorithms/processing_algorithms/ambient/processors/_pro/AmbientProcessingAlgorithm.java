package controller.algorithms.processing_algorithms.ambient.processors._pro;

import controller.algorithms.processing_algorithms.ProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface AmbientProcessingAlgorithm extends ProcessingAlgorithm {

    void process(ProcessingUnit processingUnit);
}
