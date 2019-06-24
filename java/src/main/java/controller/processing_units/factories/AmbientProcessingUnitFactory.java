package controller.processing_units.factories;

import controller.algorithms.algorithm_manager.AlgorithmManager;
import controller.processing_units.units.AmbientProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import model.config.ConfigManager;
import model.config.enums.ambient.AmbientAnimationStyle;
import model.config.enums.general.FrameSegment;

public class AmbientProcessingUnitFactory implements ProcessingUnitFactory {

    @Override
    public ProcessingUnit getUnit(int ledIndex, FrameSegment associatedFrameSegment, Integer... params) {
        int xOrigin = params[0];
        int yOrigin = params[1];
        int width = params[2];
        int height = params[3];
        AmbientProcessingUnit unit = new AmbientProcessingUnit(xOrigin, yOrigin, width, height, ledIndex, associatedFrameSegment);
        setPreProcessingAlgorithms(unit);
        setProcessingAlgorithms(unit);
        setPostProcessingAlgorithms(unit);
        return unit;
    }

    @Override
    public void setPreProcessingAlgorithms(ProcessingUnit unit) {
        boolean isShufflingPresets = ConfigManager.getAmbientConfig().isShufflePresets();

        if (isShufflingPresets) {
            unit.getPreProcessingAlgorithms().add(
                    AlgorithmManager
                            .getAmbientAlgorithms()
                            .getPreProcessingAlgorithms()
                            .getShufflePresetsAlgorithm()
            );
        }
    }

    @Override
    public void setProcessingAlgorithms(ProcessingUnit unit) {
        AmbientAnimationStyle animationStyle = ConfigManager.getAmbientConfig().getAnimationStyle();

        switch (animationStyle) {
            case FADE:
                unit.getProcessingAlgorithms().add(
                        AlgorithmManager
                                .getAmbientAlgorithms()
                                .getProcessingAlgorithms()
                                .getFadeAnimationProcessingAlgorithm()
                );
                break;
            case SPIN:
                unit.getProcessingAlgorithms().add(
                        AlgorithmManager
                                .getAmbientAlgorithms()
                                .getProcessingAlgorithms()
                                .getSpinAnimationProcessingAlgorithm()
                );
                break;
            case INTERPOLATE:
                unit.getProcessingAlgorithms().add(
                        AlgorithmManager
                                .getAmbientAlgorithms()
                                .getProcessingAlgorithms()
                                .getInterpolateAnimationProcessingAlgorithm()
                );
                break;
        }
    }

    @Override
    public void setPostProcessingAlgorithms(ProcessingUnit unit) {

    }
}
