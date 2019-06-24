package controller.algorithms.processing_algorithms.video.processors._pro;

import controller.algorithms.processing_algorithms.ProcessingAlgorithm;
import controller.processing_units.units.ProcessingUnit;

public interface VideoProcessingAlgorithm  extends ProcessingAlgorithm {

    void process(ProcessingUnit processingUnit);
}
