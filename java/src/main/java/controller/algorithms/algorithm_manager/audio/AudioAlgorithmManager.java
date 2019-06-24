package controller.algorithms.algorithm_manager.audio;

public class AudioAlgorithmManager {

    private AudioProcessingAlgorithmManager processingAlgorithmManager;
    private AudioPreProcessingAlgorithmManager preProcessingAlgorithmManager;
    private AudioPostProcessingAlgorithmManager postProcessingAlgorithmManager;

    public AudioAlgorithmManager() {
        preProcessingAlgorithmManager = new AudioPreProcessingAlgorithmManager();
        processingAlgorithmManager = new AudioProcessingAlgorithmManager();
        postProcessingAlgorithmManager = new AudioPostProcessingAlgorithmManager();
    }

    public AudioPreProcessingAlgorithmManager getPreProcessingAlgorithms() {
        return preProcessingAlgorithmManager;
    }

    public AudioProcessingAlgorithmManager getProcessingAlgorithms() {
        return processingAlgorithmManager;
    }

    public AudioPostProcessingAlgorithmManager getPostProcessingAlgorithms() {
        return postProcessingAlgorithmManager;
    }
}
