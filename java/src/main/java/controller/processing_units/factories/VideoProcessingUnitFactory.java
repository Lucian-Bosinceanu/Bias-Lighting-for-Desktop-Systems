package controller.processing_units.factories;

import controller.algorithms.algorithm_manager.AlgorithmManager;
import controller.processing_units.units.ProcessingUnit;
import controller.processing_units.units.VideoProcessingUnit;
import model.config.ConfigManager;
import model.config.enums.general.FrameSegment;

public class VideoProcessingUnitFactory implements ProcessingUnitFactory{

    @Override
    public VideoProcessingUnit getUnit(int ledIndex, FrameSegment associatedFrameSegment, Integer... params) {
        int xOrigin = params[0];
        int yOrigin = params[1];
        int width = params[2];
        int height = params[3];

        Integer screenWidth = ConfigManager.getVideoConfig().getCurrentScreen().width;
        Integer screenHeight = ConfigManager.getVideoConfig().getCurrentScreen().height;

        int divider = 1;

        switch (ConfigManager.getVideoConfig().getProcessingDepth()) {
            case HALF:
                divider = 2;
                break;
            case QUARTER:
                divider = 4;
                break;
            case MARGIN:
                divider = 0;
        }

        if (divider != 0) {
            switch (associatedFrameSegment) {
                case BOTTOM:
                    yOrigin = screenHeight - (screenHeight/divider);
                    height = screenHeight/divider;
                    break;
                case LEFT:
                    width = screenWidth / divider;
                    break;
                case RIGHT:
                    xOrigin = screenWidth - (screenWidth/divider);
                    width = screenWidth/divider;
                    break;
                case TOP:
                    height = screenHeight / divider;
            }

        }

        VideoProcessingUnit unit = new VideoProcessingUnit(xOrigin, yOrigin, width, height, ledIndex, associatedFrameSegment);
        setPreProcessingAlgorithms(unit);
        setProcessingAlgorithms(unit);
        setPostProcessingAlgorithms(unit);
        return unit;
    }

    @Override
    public void setPreProcessingAlgorithms(ProcessingUnit unit) {
        switch (ConfigManager.getVideoConfig().getImageFilter()) {
            case GRAYSCALE:
                unit.getPreProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getPreProcessingAlgorithms()
                                .getGrayscaleImageFilterAlgorithm()
                        );
                break;
            case RED:
                unit.getPreProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getPreProcessingAlgorithms()
                                .getRedImageFilterAlgorithm()
                        );
                break;
            case GREEN:
                unit.getPreProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getPreProcessingAlgorithms()
                                .getGreenImageFilterAlgorithm()
                        );
                break;
            case BLUE:
                unit.getPreProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getPreProcessingAlgorithms()
                                .getBlueImageFilterAlgorithm()
                        );
                break;

        }
    }

    @Override
    public void setProcessingAlgorithms(ProcessingUnit unit) {
        switch (ConfigManager.getVideoConfig().getProcessingType()) {
            case UNIFORM:
                unit.getProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getProcessingAlgorithms()
                                .getUniformProcessingAlgorithm()
                        );
                break;
            case LOGARITHMIC:
                unit.getProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getProcessingAlgorithms()
                                .getLogarithmicProcessingAlgorithm()
                        );
                break;
        }
    }

    @Override
    public void setPostProcessingAlgorithms(ProcessingUnit unit) {
        if (ConfigManager.getVideoConfig().isBrightnessRegulated()) {
            unit.getPostProcessingAlgorithms()
                    .add(AlgorithmManager
                            .getVideoAlgorithms()
                            .getPostProcessingAlgorithm()
                            .getBrightnessRegulatorAlgorithm()
                    );
        }

        switch (ConfigManager.getVideoConfig().getFrameToFrameUpdateStyle()) {
            case GRADIENT:
                unit.getPostProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getPostProcessingAlgorithm()
                                .getGradientFrameUpdateAlgorithm()
                        );
                break;
            case LAZY:
                unit.getPostProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getPostProcessingAlgorithm()
                                .getLazyFrameUpdateAlgorithm()
                        );
                break;
            case LAZY_GRADIENT:
                unit.getPostProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getPostProcessingAlgorithm()
                                .getLazyFrameUpdateAlgorithm()
                        );
                unit.getPostProcessingAlgorithms()
                        .add(AlgorithmManager
                                .getVideoAlgorithms()
                                .getPostProcessingAlgorithm()
                                .getGradientFrameUpdateAlgorithm());
        }
    }
}
