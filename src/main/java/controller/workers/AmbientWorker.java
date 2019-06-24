package controller.workers;

import controller.frame.FrameManager;
import controller.io.serial.SerialCommunicator;
import controller.processing_units.units.AmbientProcessingUnit;
import controller.source.SourceManager;
import controller.ui.alerts.AlertManager;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import jssc.SerialPortException;
import model.FrameColorPreset;
import model.config.ConfigManager;
import model.config.enums.general.FrameMode;

import java.io.IOException;

public class AmbientWorker implements Worker {

    private FrameColorPreset frameColorPreset;
    private FrameColorPreset interpolationPreset;
    private FrameManager frameManager;
    private boolean isRunning;

    @Override
    public void stop() {
            isRunning = false;
            //frameManager.turnOff();

        System.out.println("Static worker stops");
    }

    @Override
    public void run() {
        System.out.println("Static worker starts");
        isRunning = true;
        frameManager = new FrameManager();
        long currentTime;

        try {

            frameManager.constructProcessingUnits();

            SourceManager.getColorPresetSource().loadAllPresets();

            loadPresets();
            setIndividualLedColor();

            currentTime = System.currentTimeMillis();

            long updateRate = ConfigManager.getVideoConfig().getUpdateRate();
            long refreshRate = 1000 / updateRate;

            SourceManager.getTimeSource().setStartTime();
            SourceManager.getTimeSource().setWaveAnimationTransitionTime(frameManager.getLedArray().size());

            while (isRunning) {
                if (System.currentTimeMillis() - currentTime > refreshRate) {
                    SourceManager.getTimeSource().update(System.currentTimeMillis());
                    currentTime = System.currentTimeMillis();
                    frameManager.update();
                    SerialCommunicator.sendLedColors(frameManager.getLedArray());
                }
            }

            frameManager.turnOff();
        } catch (SerialPortException e) {
            isRunning = false;
            ConfigManager.getGlobalConfig().setFrameMode(FrameMode.NONE);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AlertManager.showCustomError(
                            "Critical error!",
                            "No device found for provided port - " + ConfigManager.getGlobalConfig().getSerialPortName(),
                            "Make sure that Ambilight is connected to correct port!"
                    );
                }
            });
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPresets() throws IOException {
        String presetFileName = ConfigManager.getAmbientConfig().getFramePresetName();
        FrameColorPreset currentPreset = FrameColorPreset.loadColorState(presetFileName);
        SourceManager.getColorPresetSource().update(currentPreset);
        frameColorPreset = currentPreset;

        String interpolationPresetFileName = ConfigManager.getAmbientConfig().getInterpolationPresetName();
        interpolationPreset = FrameColorPreset.loadColorState(interpolationPresetFileName);
        SourceManager.getColorPresetSource().updateNextState(interpolationPreset);
    }

    private void setIndividualLedColor() {
        for (int i = 0; i < frameManager.getLedArray().size() && i < frameColorPreset.getIndividualColors().size(); i++) {
            frameManager.getLedArray().set(i, Color.valueOf(frameColorPreset.getIndividualColors().get(i)));
            AmbientProcessingUnit currentUnit = (AmbientProcessingUnit) frameManager.getProcessingUnits().get(i);
            currentUnit.setBaseColor(Color.valueOf(frameColorPreset.getIndividualColors().get(i)));
            currentUnit.setLedCurrentColor(Color.valueOf(frameColorPreset.getIndividualColors().get(i)));
        }

        for (int i = 0; i < frameManager.getLedArray().size() && i < interpolationPreset.getIndividualColors().size(); i++) {
            AmbientProcessingUnit currentUnit = (AmbientProcessingUnit) frameManager.getProcessingUnits().get(i);
            currentUnit.setNextColor(Color.valueOf(interpolationPreset.getIndividualColors().get(i)));
        }
    }

}
