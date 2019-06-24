package controller.algorithms.processing_algorithms.audio.processors.__pre;

import controller.processing_units.units.AudioProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import controller.source.SourceManager;
import javafx.scene.paint.Color;
import model.config.ConfigManager;

import java.util.LinkedList;
import java.util.Queue;

public class RhythmicInterpolationPreProcessingAlgorithm implements AudioPreProcessingAlgorithm {

    private Queue<Double> lastRmsValues;
    private static final int queueSize = 16;
    private long animationTime;
    private double rmsMean;
    private long baseAnimationSpeed;

    public RhythmicInterpolationPreProcessingAlgorithm() {
        lastRmsValues = new LinkedList<>();
        rmsMean = -1;
        baseAnimationSpeed = ConfigManager.getAudioConfig().getBaseAnimationSpeed();
    }

    @Override
    public void preProcess(ProcessingUnit processingUnit) {
        AudioProcessingUnit updatedUnit = (AudioProcessingUnit) processingUnit;
        long elapsedTime = SourceManager.getTimeSource().getElapsedTime();
        animationTime = ConfigManager.getAudioConfig().getBaseAnimationSpeed();

//        if (updatedUnit.getLedIndex() == 0) {
//            computeActualAnimationTime(SourceManager.getAudioSource().getRms());
//        }

        double intensityPercentage = (float) elapsedTime % animationTime / animationTime;

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

    private void computeActualAnimationTime(double currentRMS) {
        double lastRmsValue;
        if (lastRmsValues.size() < queueSize) {
            lastRmsValues.add(currentRMS);
            return;
        } else {
            lastRmsValue = lastRmsValues.remove();
            lastRmsValues.add(currentRMS);
        }

        updateRmsMean(currentRMS, lastRmsValue);

        double animationSpeedFactor = ConfigManager.getAudioConfig().getAnimationSpeedFactor();
        double animationVelocity = animationSpeedFactor / rmsMean;
        animationTime = (long) (baseAnimationSpeed * animationVelocity);
        System.out.println(rmsMean + " " + animationTime);
        if (animationTime < 500) {
            animationTime = 500;
        }
        ConfigManager.getAudioConfig().setBaseAnimationSpeed(animationTime);
    }

    private void updateRmsMean(double currentRms, double lastRmsValue) {
        double computedRmsMean = 0;
        if (rmsMean < 0) {
            for (Double rmsValue : lastRmsValues) {
                computedRmsMean += rmsValue;
            }

            computedRmsMean /= queueSize;
        } else {
            computedRmsMean = rmsMean + (currentRms - lastRmsValue) / queueSize;
        }

        rmsMean = computedRmsMean;
    }
}
