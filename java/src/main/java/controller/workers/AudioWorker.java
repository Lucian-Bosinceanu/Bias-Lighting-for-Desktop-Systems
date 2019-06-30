package controller.workers;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.util.fft.FFT;
import controller.frame.FrameManager;
import controller.io.serial.SerialCommunicator;
import controller.processing_units.units.AudioProcessingUnit;
import controller.source.SourceManager;
import controller.ui.alerts.AlertManager;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import jssc.SerialPortException;
import model.FrameColorPreset;
import model.config.ConfigManager;
import model.config.enums.general.FrameMode;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class AudioWorker implements Worker {

    private boolean isRunning;
    private FrameColorPreset basePreset;
    private FrameColorPreset interpolationPreset;
    private FrameManager frameManager;

    private static final int sampleRate = 44100;
    private static final int bufferSize = 1024;
    private AudioDispatcher audioDispatcher;
    private FFT fft;
    private float[] audioData;
    private double intensity;

    @Override
    public void stop() {
        isRunning = false;
        audioDispatcher.stop();
        try {
            frameManager.turnOff();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRunning = true;
        frameManager = new FrameManager();
        frameManager.constructProcessingUnits();

        try {
            audioDispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, bufferSize, 0);
            SourceManager.getColorPresetSource().loadAllPresets();
            loadPresets();
            setIndividualLedColor();
            fft = new FFT(bufferSize);

            audioDispatcher.addAudioProcessor(new AudioProcessor() {
                @Override
                public boolean process(AudioEvent audioEvent) {
                    audioData = audioEvent.getFloatBuffer();
                    fft.forwardTransform(audioData);
                    intensity = 0;

                    for (int i = 0; i < bufferSize / 2; i++) {
                        if (audioData[i] > intensity) {
                            intensity = audioData[i];
                        }
                    }

                    //System.out.println(intensity);
                    SourceManager.getAudioSource().update(intensity);
                    try {
                        SourceManager.getTimeSource().update(System.currentTimeMillis());
                        frameManager.update();
                        SerialCommunicator.sendLedColors(frameManager.getLedArray());
                    } catch (SerialPortException e) {
                        isRunning = false;
                        ConfigManager.getGlobalConfig().setFrameMode(FrameMode.NONE);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                AlertManager.showCustomError(
                                        "Critical error!",
                                        "No device found for provided port - " + ConfigManager.getGlobalConfig().getSerialPortName(),
                                        "Make sure that the device is connected to correct port!"
                                );
                            }
                        });
                        stop();
                    }
                    return false;
                }

                @Override
                public void processingFinished() {
                }
            });

            SourceManager.getTimeSource().setStartTime();
            audioDispatcher.run();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void loadPresets() throws IOException {
        String presetFileName = ConfigManager.getAudioConfig().getFramePreset();
        FrameColorPreset currentPreset = FrameColorPreset.loadColorState(presetFileName);
        SourceManager.getColorPresetSource().update(currentPreset);
        basePreset = currentPreset;

        String interpolationPresetFileName = ConfigManager.getAudioConfig().getInterpolationPresetName();
        interpolationPreset = FrameColorPreset.loadColorState(interpolationPresetFileName);
        SourceManager.getColorPresetSource().updateNextState(interpolationPreset);
    }

    private void setIndividualLedColor() {
        for (int i = 0; i < frameManager.getLedArray().size() && i < basePreset.getIndividualColors().size(); i++) {
            frameManager.getLedArray().set(i, Color.valueOf(basePreset.getIndividualColors().get(i)));
            AudioProcessingUnit currentUnit = (AudioProcessingUnit) frameManager.getProcessingUnits().get(i);
            currentUnit.setBaseColor(Color.valueOf(basePreset.getIndividualColors().get(i)));
            currentUnit.setLedCurrentColor(Color.valueOf(basePreset.getIndividualColors().get(i)));
        }

        for (int i = 0; i < frameManager.getLedArray().size() && i < interpolationPreset.getIndividualColors().size(); i++) {
            AudioProcessingUnit currentUnit = (AudioProcessingUnit) frameManager.getProcessingUnits().get(i);
            currentUnit.setNextColor(Color.valueOf(interpolationPreset.getIndividualColors().get(i)));
        }
    }

}
