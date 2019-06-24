package controller.algorithms.processing_algorithms.audio.processors._pro;

import controller.processing_units.units.AudioProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import javafx.scene.paint.Color;
import model.config.ConfigManager;
import model.config.enums.general.FrameConstructionType;
import model.config.enums.general.FrameSegment;

public class FrequencyWaveAudioProcessingAlgorithm implements AudioProcessingAlgorithm {

    //private static double ledQuota = ConfigManager.getAudioConfig().getSensibility() / ConfigManager.getGlobalConfig().getHeight();

    @Override
    public void process(ProcessingUnit processingUnit) {
        AudioProcessingUnit updatedUnit = (AudioProcessingUnit) processingUnit;
        int unitAudioHeight = 0;
        int heightLedCount = ConfigManager.getGlobalConfig().getHeight();
        int widthLedCount = ConfigManager.getGlobalConfig().getWidth();

        if (ConfigManager.getGlobalConfig().getConstructionType() == FrameConstructionType.SIDE_ONLY) {
            widthLedCount = 0;
        }

        int ledIndex = updatedUnit.getLedIndex();
        double ledQuota = updatedUnit.getSensibility() / ConfigManager.getGlobalConfig().getHeight();
        FrameSegment frameSegment = updatedUnit.getAssociatedFrameSegment();
        Color baseColor = updatedUnit.getBaseColor();

        if (ConfigManager.getAudioConfig().isRhythmicInterpolation()) {
            baseColor = updatedUnit.getLedCurrentColor();
        }

        int onLedCount = (int) (updatedUnit.getCurrentSignal() / ledQuota);
        onLedCount = onLedCount > heightLedCount ? heightLedCount : onLedCount;

        if (frameSegment == FrameSegment.BOTTOM) {
            updatedUnit.setLedCurrentColor(baseColor);
            return;
        }

        if (frameSegment == FrameSegment.LEFT) {
            unitAudioHeight = ledIndex;
        } else if (frameSegment == FrameSegment.TOP) {
            unitAudioHeight = heightLedCount;
        } else if (frameSegment == FrameSegment.RIGHT) {
            unitAudioHeight = heightLedCount - (ledIndex - heightLedCount - widthLedCount);
        }

        if (unitAudioHeight < onLedCount) {
            updatedUnit.setLedCurrentColor(baseColor);
        } else if (unitAudioHeight > onLedCount) {
            updatedUnit.setLedCurrentColor(Color.BLACK);
        } else {
            double lastOnLedQuota = (updatedUnit.getCurrentSignal() % ledQuota) / ledQuota;
            Color newColor = Color.color(
                    baseColor.getRed() * lastOnLedQuota,
                    baseColor.getGreen() * lastOnLedQuota,
                    baseColor.getBlue() * lastOnLedQuota
            );

            updatedUnit.setLedCurrentColor(newColor);
        }
    }
}
