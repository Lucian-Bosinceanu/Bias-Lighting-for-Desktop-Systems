package controller.algorithms.algorithm_manager.video;

import controller.algorithms.processing_algorithms.video.processors.post.BrightnessRegulatorPostProcessingAlgorithm;
import controller.algorithms.processing_algorithms.video.processors.post.frame_update.GradientFrameUpdatePostProcessingAlgorithm;
import controller.algorithms.processing_algorithms.video.processors.post.frame_update.LazyFrameUpdatePostProcessingAlgorithm;

public class VideoPostProcessingAlgorithmManager {

    private BrightnessRegulatorPostProcessingAlgorithm brightnessRegulatorAlgorithm;
    private GradientFrameUpdatePostProcessingAlgorithm gradientFrameUpdateAlgorithm;
    private LazyFrameUpdatePostProcessingAlgorithm lazyFrameUpdateAlgorithm;

    public VideoPostProcessingAlgorithmManager() {
        brightnessRegulatorAlgorithm = new BrightnessRegulatorPostProcessingAlgorithm();
        gradientFrameUpdateAlgorithm = new GradientFrameUpdatePostProcessingAlgorithm();
        lazyFrameUpdateAlgorithm = new LazyFrameUpdatePostProcessingAlgorithm();
    }

    public BrightnessRegulatorPostProcessingAlgorithm getBrightnessRegulatorAlgorithm() {
        return brightnessRegulatorAlgorithm;
    }

    public GradientFrameUpdatePostProcessingAlgorithm getGradientFrameUpdateAlgorithm() {
        return gradientFrameUpdateAlgorithm;
    }

    public LazyFrameUpdatePostProcessingAlgorithm getLazyFrameUpdateAlgorithm(){
        return lazyFrameUpdateAlgorithm;
    }
}
