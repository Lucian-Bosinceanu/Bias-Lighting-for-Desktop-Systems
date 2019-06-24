package controller.processing_units.factories;

import controller.algorithms.algorithm_manager.AlgorithmManager;
import controller.processing_units.units.AudioProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import model.config.ConfigManager;
import model.config.enums.general.FrameSegment;

public class AudioProcessingUnitFactory implements ProcessingUnitFactory {

    @Override
    public ProcessingUnit getUnit(int ledIndex, FrameSegment associatedFrameSegment, Integer... params) {
        AudioProcessingUnit unit = new AudioProcessingUnit(ledIndex, associatedFrameSegment);
        double sensibility = ConfigManager.getAudioConfig().getSensibility();
        unit.setSensibility(sensibility);

        setPreProcessingAlgorithms(unit);
        setProcessingAlgorithms(unit);
        setPostProcessingAlgorithms(unit);

        return unit;
    }

    @Override
    public void setPreProcessingAlgorithms(ProcessingUnit unit) {
        boolean isRelaxedTransition = ConfigManager.getAudioConfig().isRelaxedTransition();
        boolean isDynamicSensibility = ConfigManager.getAudioConfig().isDynamicSensibility();

        if (isDynamicSensibility) {
            unit.getPreProcessingAlgorithms().add(
                    AlgorithmManager
                            .getAudioAlgorithms()
                            .getPreProcessingAlgorithms()
                            .getDynamicSensibilityDetectionAlgorithm()
            );
        }


        if (isRelaxedTransition) {
            unit.getPostProcessingAlgorithms().add(
                    AlgorithmManager
                            .getAudioAlgorithms()
                            .getPostProcessingAlgorithms()
                            .getRelaxingPulseAlgorithm()
            );
        }
    }

    @Override
    public void setProcessingAlgorithms(ProcessingUnit unit) {
        switch (ConfigManager.getAudioConfig().getProcessingType()){
            case PULSE:
                unit.getProcessingAlgorithms().add(
                        AlgorithmManager
                                .getAudioAlgorithms()
                                .getProcessingAlgorithms()
                                .getRmsPulseAudioAlgorithm()
                );
                break;
            case WAVE:
                unit.getProcessingAlgorithms().add(
                        AlgorithmManager
                                .getAudioAlgorithms()
                                .getProcessingAlgorithms()
                                .getRmsWaveAudioAlgorithm()
                );
        }
    }

    @Override
    public void setPostProcessingAlgorithms(ProcessingUnit unit) {
        if (ConfigManager.getAudioConfig().isShufflingPresets()) {
            unit.getPreProcessingAlgorithms().add(
                    AlgorithmManager
                            .getAudioAlgorithms()
                            .getPreProcessingAlgorithms()
                            .getAudioShufflePresetsAlgorithm()
            );
        }

        if (ConfigManager.getAudioConfig().isRhythmicInterpolation()) {
            unit.getPreProcessingAlgorithms().add(
                    AlgorithmManager
                            .getAudioAlgorithms()
                            .getPreProcessingAlgorithms()
                            .getRhythmicInterpolationAlgorithm()
            );
        }
    }
}
