package controller.frame.assembler;

import javafx.scene.paint.Color;
import model.LedFrame;
import model.config.ConfigManager;
import model.config.enums.general.FrameConstructionType;


public class FrameBuilder {

    public LedFrame build() {
        Integer width = ConfigManager.getGlobalConfig().getWidth();
        Integer height = ConfigManager.getGlobalConfig().getHeight();
        FrameConstructionType constructionType = ConfigManager.getGlobalConfig().getConstructionType();
        LedFrame ledFrame = new LedFrame(width, height);
        buildLedArray(ledFrame, constructionType);
        return ledFrame;
    }

    private void buildLedArray(LedFrame ledFrame, FrameConstructionType constructionType) {
        int ledCount = LedCountComputer.compute(constructionType, ledFrame.getWidthLedCount(), ledFrame.getHeightLedCount());

        Color defaultColor = Color.color(0,0,0);

        for (int i = 0; i < ledCount; i++) {
            ledFrame.getLedFrame().add(defaultColor);
        }
    }
}
