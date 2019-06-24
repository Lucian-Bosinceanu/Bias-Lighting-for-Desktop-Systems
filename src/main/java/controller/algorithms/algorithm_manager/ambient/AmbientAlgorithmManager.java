package controller.algorithms.algorithm_manager.ambient;

public class AmbientAlgorithmManager {

    private AmbientPreProcessingAlgorithmManager preProcessingAlgorithmManager;
    private AmbientProcessingAlgorithmManager processingAlgorithmManager;
    private AmbientPostProcessingAlgorithmManager postProcessingAlgorithmManager;

    public AmbientAlgorithmManager() {
        preProcessingAlgorithmManager = new AmbientPreProcessingAlgorithmManager();
        processingAlgorithmManager = new AmbientProcessingAlgorithmManager();
        postProcessingAlgorithmManager = new AmbientPostProcessingAlgorithmManager();
    }

    public AmbientPreProcessingAlgorithmManager getPreProcessingAlgorithms() {
        return preProcessingAlgorithmManager;
    }

    public AmbientProcessingAlgorithmManager getProcessingAlgorithms() {
        return processingAlgorithmManager;
    }

    public AmbientPostProcessingAlgorithmManager getPostProcessingAlgorithms() {
        return postProcessingAlgorithmManager;
    }
}
