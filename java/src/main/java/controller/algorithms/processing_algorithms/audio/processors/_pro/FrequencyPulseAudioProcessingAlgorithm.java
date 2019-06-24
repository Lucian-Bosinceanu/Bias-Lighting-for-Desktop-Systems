package controller.algorithms.processing_algorithms.audio.processors._pro;

import controller.processing_units.units.AudioProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import javafx.scene.paint.Color;
import model.config.ConfigManager;

public class FrequencyPulseAudioProcessingAlgorithm implements AudioProcessingAlgorithm {
    @Override
    public void process(ProcessingUnit processingUnit) {
        AudioProcessingUnit updatedUnit = (AudioProcessingUnit) processingUnit;
        double currentSignal = updatedUnit.getCurrentSignal();
        double colorIntensity = currentSignal / updatedUnit.getSensibility();
        colorIntensity = colorIntensity > 1.0 ? 1.0 : colorIntensity;
        Color baseColor = updatedUnit.getBaseColor();

        if (ConfigManager.getAudioConfig().isRhythmicInterpolation()) {
            baseColor = updatedUnit.getLedCurrentColor();
        }

        double updatedRed = baseColor.getRed() * colorIntensity;
        double updatedGreen = baseColor.getGreen() * colorIntensity;
        double updatedBlue = baseColor.getBlue() * colorIntensity;

        Color updatedColor = Color.color(updatedRed, updatedGreen, updatedBlue);

        updatedUnit.setLedCurrentColor(updatedColor);
    }
}
