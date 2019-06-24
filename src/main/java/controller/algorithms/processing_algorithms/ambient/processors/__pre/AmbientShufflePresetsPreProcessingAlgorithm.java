package controller.algorithms.processing_algorithms.ambient.processors.__pre;

import controller.processing_units.units.AmbientProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import controller.source.SourceManager;
import javafx.scene.paint.Color;
import model.FrameColorPreset;
import model.config.ConfigManager;

import java.util.List;
import java.util.Random;

public class AmbientShufflePresetsPreProcessingAlgorithm implements AmbientPreProcessingAlgorithm {

    @Override
    public void preProcess(ProcessingUnit processingUnit) {
        AmbientProcessingUnit updatedUnit = (AmbientProcessingUnit) processingUnit;
        if (updatedUnit.isAnimationFinished()) {
            if (processingUnit.getLedIndex() == 0) {
                changeColorPreset();
            }

            Color updatedColor = Color.BLACK;

            if (SourceManager.getColorPresetSource().getNextState().getIndividualColors().size() != 0) {
                if (SourceManager.getColorPresetSource().getSource().getIndividualColors().size() != 0) {
                    updatedColor = Color.valueOf(
                            SourceManager
                                    .getColorPresetSource()
                                    .getSource()
                                    .getIndividualColors()
                                    .get(processingUnit.getLedIndex()));
                }
            }

            switch (ConfigManager.getAmbientConfig().getAnimationStyle()) {
                case INTERPOLATE:
                    updatedUnit.setNextColor(updatedColor);
                    break;
                case FADE:
                    updatedUnit.setBaseColor(updatedColor);

            }

            updatedUnit.setAnimationFinished(false);
        }

    }

    private void changeColorPreset() {
        List<FrameColorPreset> colorStates = SourceManager.getColorPresetSource().getAllPresets();
        FrameColorPreset nextState = SourceManager.getColorPresetSource().getNextState();
        SourceManager.getColorPresetSource().update(nextState);
        Random random = new Random();
        FrameColorPreset selectedPreset;


        while (true) {
            int randomPresetIndex = random.nextInt(colorStates.size());
            selectedPreset = colorStates.get(randomPresetIndex);

            if (selectedPreset.getIndividualColors().size() != 0 && !selectedPreset.equals(nextState)) {
                break;
            }
        }

        SourceManager.getColorPresetSource().updateNextState(selectedPreset);
    }
}
