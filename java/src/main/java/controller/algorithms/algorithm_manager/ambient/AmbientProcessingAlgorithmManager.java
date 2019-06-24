package controller.algorithms.algorithm_manager.ambient;

import controller.algorithms.processing_algorithms.ambient.processors._pro.FadeAnimationProcessingAlgorithm;
import controller.algorithms.processing_algorithms.ambient.processors._pro.InterpolateAnimationProcessingAlgorithm;
import controller.algorithms.processing_algorithms.ambient.processors._pro.SpinAnimationProcessingAlgorithm;

public class AmbientProcessingAlgorithmManager {

    private FadeAnimationProcessingAlgorithm fadeAnimationProcessingAlgorithm;
    private SpinAnimationProcessingAlgorithm spinAnimationProcessingAlgorithm;
    private InterpolateAnimationProcessingAlgorithm interpolateAnimationProcessingAlgorithm;

    public AmbientProcessingAlgorithmManager() {
        fadeAnimationProcessingAlgorithm = new FadeAnimationProcessingAlgorithm();
        spinAnimationProcessingAlgorithm = new SpinAnimationProcessingAlgorithm();
        interpolateAnimationProcessingAlgorithm = new InterpolateAnimationProcessingAlgorithm();
    }

    public FadeAnimationProcessingAlgorithm getFadeAnimationProcessingAlgorithm() {
        return fadeAnimationProcessingAlgorithm;
    }

    public SpinAnimationProcessingAlgorithm getSpinAnimationProcessingAlgorithm() {
        return spinAnimationProcessingAlgorithm;
    }

    public InterpolateAnimationProcessingAlgorithm getInterpolateAnimationProcessingAlgorithm() {
        return interpolateAnimationProcessingAlgorithm;
    }
}
