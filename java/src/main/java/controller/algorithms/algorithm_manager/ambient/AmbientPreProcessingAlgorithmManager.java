package controller.algorithms.algorithm_manager.ambient;

import controller.algorithms.processing_algorithms.ambient.processors.__pre.AmbientShufflePresetsPreProcessingAlgorithm;

public class AmbientPreProcessingAlgorithmManager {

    private AmbientShufflePresetsPreProcessingAlgorithm AmbientShufflePresetsPreProcessingAlgorithm;

    public AmbientPreProcessingAlgorithmManager() {
        AmbientShufflePresetsPreProcessingAlgorithm = new AmbientShufflePresetsPreProcessingAlgorithm();
    }


    public AmbientShufflePresetsPreProcessingAlgorithm getShufflePresetsAlgorithm() {
        return AmbientShufflePresetsPreProcessingAlgorithm;
    }
}
