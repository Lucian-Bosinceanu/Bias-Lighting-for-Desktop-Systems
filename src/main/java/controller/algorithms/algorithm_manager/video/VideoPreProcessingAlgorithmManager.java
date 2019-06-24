package controller.algorithms.algorithm_manager.video;

import controller.algorithms.processing_algorithms.video.processors.__pre.BlueFilterPreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.video.processors.__pre.GrayscaleFilterPreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.video.processors.__pre.GreenFilterPreProcessingAlgorithm;
import controller.algorithms.processing_algorithms.video.processors.__pre.RedFilterPreProcessingAlgorithm;

public class VideoPreProcessingAlgorithmManager {

    private GrayscaleFilterPreProcessingAlgorithm grayscaleFilterAlgorithm;
    private RedFilterPreProcessingAlgorithm redFilterPreProcessingAlgorithm;
    private GreenFilterPreProcessingAlgorithm greenFilterPreProcessingAlgorithm;
    private BlueFilterPreProcessingAlgorithm blueFilterPreProcessingAlgorithm;

    public VideoPreProcessingAlgorithmManager() {
        grayscaleFilterAlgorithm = new GrayscaleFilterPreProcessingAlgorithm();
        redFilterPreProcessingAlgorithm = new RedFilterPreProcessingAlgorithm();
        greenFilterPreProcessingAlgorithm = new GreenFilterPreProcessingAlgorithm();
        blueFilterPreProcessingAlgorithm = new BlueFilterPreProcessingAlgorithm();
    }

    public RedFilterPreProcessingAlgorithm getRedImageFilterAlgorithm() {
        return redFilterPreProcessingAlgorithm;
    }

    public GreenFilterPreProcessingAlgorithm getGreenImageFilterAlgorithm() {
        return greenFilterPreProcessingAlgorithm;
    }

    public BlueFilterPreProcessingAlgorithm getBlueImageFilterAlgorithm() {
        return blueFilterPreProcessingAlgorithm;
    }

    public GrayscaleFilterPreProcessingAlgorithm getGrayscaleImageFilterAlgorithm() {
        return grayscaleFilterAlgorithm;
    }
}
