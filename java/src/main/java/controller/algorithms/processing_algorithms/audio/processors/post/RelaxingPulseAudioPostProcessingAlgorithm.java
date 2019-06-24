package controller.algorithms.processing_algorithms.audio.processors.post;

import controller.processing_units.units.AudioProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import javafx.scene.paint.Color;
import model.config.ConfigManager;

public class RelaxingPulseAudioPostProcessingAlgorithm implements AudioPostProcessingAlgorithm {

    @Override
    public void postProcess(ProcessingUnit processingUnit) {
        AudioProcessingUnit updatedUnit = (AudioProcessingUnit) processingUnit;
        double upBeatSmoothFactor = ConfigManager.getAudioConfig().getUpbeatSmoothFactor();
        double downBeatSmoothFactor = ConfigManager.getAudioConfig().getDownBeatSmoothFactor();

        upBeatSmoothFactor = upBeatSmoothFactor > 1 ? 1 : upBeatSmoothFactor;
        downBeatSmoothFactor = downBeatSmoothFactor > 1 ? 1 : downBeatSmoothFactor;

        Color lastColor = updatedUnit.getLastColor();
        Color currentColor = updatedUnit.getLedCurrentColor();

        double deltaRed = currentColor.getRed() - lastColor.getRed();
        double deltaGreen = currentColor.getGreen() - lastColor.getGreen();
        double deltaBlue = currentColor.getBlue() - lastColor.getBlue();

        if (deltaRed < 0 && deltaGreen < 0 && deltaBlue < 0) {
            currentColor = Color.color(
                    lastColor.getRed() + deltaRed * downBeatSmoothFactor,
                    lastColor.getGreen() + deltaGreen * downBeatSmoothFactor,
                    lastColor.getBlue() + deltaBlue * downBeatSmoothFactor
            );
        } else {
            currentColor = Color.color(
                    lastColor.getRed() + deltaRed * upBeatSmoothFactor,
                    lastColor.getGreen() + deltaGreen * upBeatSmoothFactor,
                    lastColor.getBlue() + deltaBlue * upBeatSmoothFactor
            );
        }

        updatedUnit.setLedCurrentColor(currentColor);
    }
}
