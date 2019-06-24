package controller.algorithms.algorithm_manager.audio;

import controller.algorithms.processing_algorithms.audio.processors.__pre.AudioShufflePresetsPreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.audio.processors.__pre.DynamicSensibilityDetectionPreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.audio.processors.__pre.RhythmicInterpolationPreProcessingAlgorithm;

public class AudioPreProcessingAlgorithmManager {

    private RhythmicInterpolationPreProcessingAlgorithm rhythmicInterpolationPreProcessingAlgorithm;
    private AudioShufflePresetsPreProcessingAlgorithm audioShufflePresetsPreProcessingAlgorithm;
    private DynamicSensibilityDetectionPreProcessingAlgorithm dynamicSensibilityDetectionPreProcessingAlgorithm;

    public AudioPreProcessingAlgorithmManager() {
        rhythmicInterpolationPreProcessingAlgorithm = new RhythmicInterpolationPreProcessingAlgorithm();
        audioShufflePresetsPreProcessingAlgorithm = new AudioShufflePresetsPreProcessingAlgorithm();
        dynamicSensibilityDetectionPreProcessingAlgorithm = new DynamicSensibilityDetectionPreProcessingAlgorithm();
    }

    public RhythmicInterpolationPreProcessingAlgorithm getRhythmicInterpolationAlgorithm() {
        return rhythmicInterpolationPreProcessingAlgorithm;
    }

    public AudioShufflePresetsPreProcessingAlgorithm getAudioShufflePresetsAlgorithm() {
        return audioShufflePresetsPreProcessingAlgorithm;
    }

    public DynamicSensibilityDetectionPreProcessingAlgorithm getDynamicSensibilityDetectionAlgorithm() {
        return dynamicSensibilityDetectionPreProcessingAlgorithm;
    }
}
