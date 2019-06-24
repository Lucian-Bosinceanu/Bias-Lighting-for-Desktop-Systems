package controller.algorithms.processing_algorithms.ambient.processors._pro;

import controller.processing_units.units.AmbientProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import controller.source.SourceManager;
import javafx.scene.paint.Color;
import model.config.ConfigManager;

public class InterpolateAnimationProcessingAlgorithm implements AmbientProcessingAlgorithm {
    @Override
    public void process(ProcessingUnit processingUnit) {

        AmbientProcessingUnit updatedUnit = (AmbientProcessingUnit) processingUnit;
        long animationTime = ConfigManager.getAmbientConfig().getAnimationSpeed();
        long elapsedTime = SourceManager.getTimeSource().getElapsedTime();
        double intensityPercentage = (float)elapsedTime % animationTime / animationTime;

        if (intensityPercentage - updatedUnit.getLastIntensity() < 0) {
            updatedUnit.swapBaseAndNextColors();
            updatedUnit.setAnimationFinished(true);
        }

        Color baseColor = updatedUnit.getBaseColor();
        Color endColor = updatedUnit.getNextColor();

        double deltaRed = endColor.getRed() - baseColor.getRed();
        double deltaGreen = endColor.getGreen() - baseColor.getGreen();
        double deltaBlue = endColor.getBlue() - baseColor.getBlue();

        Color updatedColor = Color.color(
                baseColor.getRed() + deltaRed * intensityPercentage,
                baseColor.getGreen() + deltaGreen * intensityPercentage,
                baseColor.getBlue() + deltaBlue * intensityPercentage
        );

        updatedUnit.setLedCurrentColor(updatedColor);
        updatedUnit.setLastIntensity(intensityPercentage);
    }
}
