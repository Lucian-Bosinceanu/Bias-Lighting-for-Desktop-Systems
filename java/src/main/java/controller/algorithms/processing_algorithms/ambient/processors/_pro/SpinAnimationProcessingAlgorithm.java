package controller.algorithms.processing_algorithms.ambient.processors._pro;

import controller.processing_units.units.AmbientProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import controller.source.SourceManager;
import javafx.scene.paint.Color;

import java.util.List;

public class SpinAnimationProcessingAlgorithm implements AmbientProcessingAlgorithm {

    @Override
    public void process(ProcessingUnit processingUnit) {
        AmbientProcessingUnit updatedUnit = (AmbientProcessingUnit) processingUnit;

        List<String> frameBaseColors = SourceManager.getColorPresetSource().getCurrentState().getIndividualColors();

        if (frameBaseColors.size() == 0) {
            return;
        }

        long spinAnimationTransitionTime = SourceManager.getTimeSource().getSpinAnimationTransitionTime();
        long elapsedTime = SourceManager.getTimeSource().getElapsedTime();
        int leadingLedPosition = (int) (elapsedTime / spinAnimationTransitionTime) % frameBaseColors.size();
        int x = updatedUnit.getLedIndex() + leadingLedPosition;
        //int x = updatedUnit.getLedIndex() - leadingLedPosition;
        //x = x > 0 ? x : frameBaseColors.size() - x;

        x = x % frameBaseColors.size();

        Color nextColor = Color.valueOf(frameBaseColors.get(x));
        Color currentColor = updatedUnit.getLedCurrentColor();

        Color updatedColor = Color.color(
                currentColor.getRed() + (nextColor.getRed() - currentColor.getRed()) * 0.25,
                currentColor.getGreen() + (nextColor.getGreen() - currentColor.getGreen()) * 0.25,
                currentColor.getBlue() + (nextColor.getBlue() - currentColor.getBlue()) * 0.25
        );

        updatedUnit.setLedCurrentColor(updatedColor);
    }
}
