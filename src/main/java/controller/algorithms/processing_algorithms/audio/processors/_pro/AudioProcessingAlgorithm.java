package controller.algorithms.processing_algorithms.audio.processors._pro;

import controller.algorithms.processing_algorithms.ProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface AudioProcessingAlgorithm extends ProcessingAlgorithm {

    void process(ProcessingUnit processingUnit);
}
