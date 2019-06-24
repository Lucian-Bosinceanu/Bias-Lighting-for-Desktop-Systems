package controller.algorithms.processing_algorithms.audio.processors.__pre;

import controller.processing_units.units.AudioProcessingUnit;
import controller.processing_units.units.ProcessingUnit;
import model.config.ConfigManager;

import java.util.LinkedList;
import java.util.Queue;

public class DynamicSensibilityDetectionPreProcessingAlgorithm implements AudioPreProcessingAlgorithm {

    private Queue<Double> frequencySample;
    private int sampleSize = 43;
    private double adjustedSensibility;
    private double sampleMean;

    public DynamicSensibilityDetectionPreProcessingAlgorithm() {
        frequencySample = new LinkedList<>();
        adjustedSensibility = ConfigManager.getAudioConfig().getSensibility();
        sampleMean = -1;
    }

    @Override
    public void preProcess(ProcessingUnit processingUnit) {
        AudioProcessingUnit updatedUnit = (AudioProcessingUnit) processingUnit;
        if (updatedUnit.getLedIndex() == 0) {
            updateSample(updatedUnit.getCurrentSignal());
            adjustSensibility();
        }

        //System.out.println(adjustedSensibility);
        updatedUnit.setSensibility(adjustedSensibility);
    }

    private void updateSample(double currentSignal) {
        if (frequencySample.size() < sampleSize) {
            frequencySample.add(currentSignal);
        } else {
            double lastFrequency = frequencySample.remove();
            frequencySample.add(currentSignal);
            updateSampleMean(currentSignal, lastFrequency);
        }
    }

    private void updateSampleMean(double currentSignal, double lastFrequency) {
        double mean = 0;
        if (sampleMean == -1) {
            for (Double value : frequencySample) {
                mean += value;
            }

            mean /= sampleSize;
        } else {
            mean = sampleMean + (currentSignal - lastFrequency) / sampleSize;
        }

        sampleMean = mean;
    }

    private void adjustSensibility() {
        if (frequencySample.size() < sampleSize) {
            return;
        }

        if (sampleMean > 2) {
            adjustedSensibility = sampleMean * 1.5;
        }
    }
}
