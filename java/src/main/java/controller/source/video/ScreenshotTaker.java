package controller.source.video;

import model.config.ConfigManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenshotTaker implements Runnable {

    private Rectangle targetScreen;
    private Robot robot;

    public ScreenshotTaker() throws AWTException {
        int displayId = ConfigManager.getVideoConfig().getDisplayId();
        targetScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[displayId].getConfigurations()[0].getBounds();
        robot = new Robot();
    }

    public BufferedImage take(){
        return robot.createScreenCapture(targetScreen);
    }

    @Override
    public void run() {
        BufferedImage image = take();
    }
}
