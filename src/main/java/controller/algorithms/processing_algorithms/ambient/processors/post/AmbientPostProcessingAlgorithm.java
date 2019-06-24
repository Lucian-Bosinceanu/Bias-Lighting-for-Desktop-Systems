package controller.algorithms.processing_algorithms.ambient.processors.post;

import controller.algorithms.processing_algorithms.PostProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface AmbientPostProcessingAlgorithm extends PostProcessingAlgorithm {

    @Override
    void postProcess(ProcessingUnit processingUnit);
}
