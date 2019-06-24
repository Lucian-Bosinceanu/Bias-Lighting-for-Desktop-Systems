package controller.algorithms.algorithm_manager.audio;

import controller.algorithms.processing_algorithms.audio.processors._pro.FrequencyPulseAudioProcessingAlgorithm;
import controller.algorithms.processing_algorithms.audio.processors._pro.FrequencyWaveAudioProcessingAlgorithm;

public class AudioProcessingAlgorithmManager {

    private FrequencyPulseAudioProcessingAlgorithm rmsPulseAudioAlgorithm;
    private FrequencyWaveAudioProcessingAlgorithm rmsWaveAudioAlgorithm;

    public AudioProcessingAlgorithmManager() {

        rmsWaveAudioAlgorithm = new FrequencyWaveAudioProcessingAlgorithm();
        rmsPulseAudioAlgorithm = new FrequencyPulseAudioProcessingAlgorithm();
    }

    public FrequencyPulseAudioProcessingAlgorithm getRmsPulseAudioAlgorithm() {
        return rmsPulseAudioAlgorithm;
    }

    public FrequencyWaveAudioProcessingAlgorithm getRmsWaveAudioAlgorithm() {
        return rmsWaveAudioAlgorithm;
    }
}
