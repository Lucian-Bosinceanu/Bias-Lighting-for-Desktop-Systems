package controller.algorithms.algorithm_manager.video;

public class VideoAlgorithmManager {

    private VideoProcessingAlgorithmManager processingAlgorithmManager;
    private VideoPreProcessingAlgorithmManager preProcessingAlgorithmManager;
    private VideoPostProcessingAlgorithmManager postProcessingAlgorithmManager;

    public VideoAlgorithmManager() {
        processingAlgorithmManager = new VideoProcessingAlgorithmManager();
        preProcessingAlgorithmManager = new VideoPreProcessingAlgorithmManager();
        postProcessingAlgorithmManager = new VideoPostProcessingAlgorithmManager();
    }

    public VideoProcessingAlgorithmManager getProcessingAlgorithms() {
        return processingAlgorithmManager;
    }

    public VideoPreProcessingAlgorithmManager getPreProcessingAlgorithms() {
        return preProcessingAlgorithmManager;
    }

    public VideoPostProcessingAlgorithmManager getPostProcessingAlgorithm() {
        return postProcessingAlgorithmManager;
    }
}
