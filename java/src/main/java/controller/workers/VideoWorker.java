package controller.workers;

import controller.frame.FrameManager;
import controller.io.serial.SerialCommunicator;
import controller.source.SourceManager;
import controller.source.video.ScreenshotTaker;
import controller.ui.alerts.AlertManager;
import javafx.application.Platform;
import jssc.SerialPortException;
import model.config.ConfigManager;
import model.config.enums.general.FrameMode;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoWorker implements Worker {

    private boolean isRunning;
    private BufferedImage screenshot;
    private Long lastScreenshotTime;
    private ScreenshotTaker screenshotTaker;
    private FrameManager frameManager;

    @Override
    public void run() {
        isRunning = true;

        System.out.println("Video Worker Starts");
        try {

            int updateRate = ConfigManager.getVideoConfig().getUpdateRate();
            int refreshRate = 1000 / updateRate;
            screenshotTaker = new ScreenshotTaker();

            SourceManager.getVideoSource().update(screenshotTaker.take());

            frameManager = new FrameManager();
            frameManager.constructProcessingUnits();

            lastScreenshotTime = System.currentTimeMillis();

            while (isRunning) {
                if (System.currentTimeMillis() - lastScreenshotTime > refreshRate) {
                    takeScreenshot();
                    lastScreenshotTime = System.currentTimeMillis();
                    SourceManager.getVideoSource().update(screenshot);
                    frameManager.update();
                    SerialCommunicator.sendLedColors(frameManager.getLedArray());
                }
            }

            frameManager.turnOff();
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        catch (SerialPortException e) {
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

    }

    private void takeScreenshot(){
        screenshot = screenshotTaker.take();
//        lastScreenshotTime = System.currentTimeMillis();
    }

    public void stop() {
        System.out.println("Video worker stops");
        isRunning = false;
    }
}
