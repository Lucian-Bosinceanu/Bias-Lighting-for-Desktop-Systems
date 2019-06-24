package controller.source.video;

import controller.source.Source;

import java.awt.image.BufferedImage;

public class VideoSource implements Source<BufferedImage> {

    private static BufferedImage screenshot = null;

    public void update(BufferedImage screenshot) {
        VideoSource.screenshot = screenshot;
    }

    @Override
    public BufferedImage getSource() {
        return screenshot;
    }
}
