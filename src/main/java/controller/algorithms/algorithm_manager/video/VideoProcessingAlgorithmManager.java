package controller.algorithms.algorithm_manager.video;

import controller.algorithms.processing_algorithms.video.processors._pro.LogarithmicProcessingAlgorithm;
import controller.algorithms.processing_algorithms.video.processors._pro.UniformProcessingAlgorithm;

public class VideoProcessingAlgorithmManager {

    private UniformProcessingAlgorithm uniformProcessingAlgorithm;
    private LogarithmicProcessingAlgorithm logarithmicProcessingAlgorithm;

    public VideoProcessingAlgorithmManager() {
        uniformProcessingAlgorithm = new UniformProcessingAlgorithm();
        logarithmicProcessingAlgorithm = new LogarithmicProcessingAlgorithm();
    }

    public LogarithmicProcessingAlgorithm getLogarithmicProcessingAlgorithm() {
        return logarithmicProcessingAlgorithm;
    }

    public UniformProcessingAlgorithm getUniformProcessingAlgorithm() {
        return uniformProcessingAlgorithm;
    }
}
