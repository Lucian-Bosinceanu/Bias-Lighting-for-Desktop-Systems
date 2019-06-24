package controller.algorithms.algorithm_manager.audio;

import controller.algorithms.processing_algorithms.audio.processors.post.RelaxingPulseAudioPostProcessingAlgorithm;

public class AudioPostProcessingAlgorithmManager {

    private RelaxingPulseAudioPostProcessingAlgorithm relaxingPulseAlgorithm;

    public AudioPostProcessingAlgorithmManager() {
        relaxingPulseAlgorithm = new RelaxingPulseAudioPostProcessingAlgorithm();
    }

    public RelaxingPulseAudioPostProcessingAlgorithm getRelaxingPulseAlgorithm() {
        return relaxingPulseAlgorithm;
    }
}
