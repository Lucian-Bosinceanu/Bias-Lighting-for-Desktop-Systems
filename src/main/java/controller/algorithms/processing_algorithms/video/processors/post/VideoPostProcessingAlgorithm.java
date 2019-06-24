package controller.algorithms.processing_algorithms.video.processors.post;

import controller.algorithms.processing_algorithms.PostProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface VideoPostProcessingAlgorithm extends PostProcessingAlgorithm {
    @Override
    void postProcess(ProcessingUnit processingUnit) ;
}
