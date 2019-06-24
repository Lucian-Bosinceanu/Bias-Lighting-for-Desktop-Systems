package controller.algorithms.processing_algorithms.audio.processors.post;

import controller.algorithms.processing_algorithms.PostProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface AudioPostProcessingAlgorithm extends PostProcessingAlgorithm {

    void postProcess(ProcessingUnit processingUnit);
}
