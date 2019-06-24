package controller.algorithms.processing_algorithms.ambient.processors._pro;

import controller.processing_units.units.AmbientProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import controller.source.SourceManager;
import javafx.scene.paint.Color;
import model.config.ConfigManager;

public class FadeAnimationProcessingAlgorithm implements AmbientProcessingAlgorithm {

    private boolean isFadeIn;
    private boolean previousFadeInState;

    public FadeAnimationProcessingAlgorithm() {
        isFadeIn = true;
    }

    @Override
    public void process(ProcessingUnit processingUnit) {
        AmbientProcessingUnit updatedUnit = (AmbientProcessingUnit) processingUnit;
        long animationTime = ConfigManager.getAmbientConfig().getAnimationSpeed();
        long elapsedTime = SourceManager.getTimeSource().getElapsedTime();
//        double intensityPercentage = Math.abs(Math.sin( Math.PI * elapsedTime / animationTime ));
        double intensityPercentage = (float) elapsedTime % animationTime / animationTime;

        //boolean fadeInState = isFadeIn;

        if (updatedUnit.getLedIndex() == 0) {
            previousFadeInState = isFadeIn;
            if (intensityPercentage - updatedUnit.getLastIntensity() < 0.0d) {
                isFadeIn = !isFadeIn;
            }
        }

        if (isFadeIn && (isFadeIn != previousFadeInState)) {
            updatedUnit.setAnimationFinished(true);
        }

        Color baseColor = updatedUnit.getBaseColor();

        double intensityModifier = intensityPercentage;

        if (!isFadeIn) {
            intensityModifier = 1 - intensityPercentage;
        }


        Color updatedColor = Color.color(
                baseColor.getRed() * intensityModifier,
                baseColor.getGreen()* intensityModifier,
                baseColor.getBlue() * intensityModifier
        );

        updatedUnit.setLedCurrentColor(updatedColor);
        updatedUnit.setLastIntensity(intensityPercentage);
    }

}
