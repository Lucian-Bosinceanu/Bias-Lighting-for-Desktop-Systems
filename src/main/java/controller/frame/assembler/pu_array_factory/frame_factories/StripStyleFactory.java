package controller.frame.assembler.pu_array_factory.frame_factories;

import model.config.ConfigManager;
import model.config.enums.general.FrameSegment;

import java.util.ArrayList;
import java.util.Arrays;

public class StripStyleFactory extends StyleFactory  {

    public StripStyleFactory() {
        heightLedCount = ConfigManager.getGlobalConfig().getHeight();
        widthLedCount = ConfigManager.getGlobalConfig().getWidth();

        unitHeight = screenSize.height / heightLedCount;
        unitWidth = screenSize.width / widthLedCount;

        frameSegments = new ArrayList<>(
                Arrays.asList(FrameSegment.TOP,
                        FrameSegment.RIGHT,
                        FrameSegment.BOTTOM,
                        FrameSegment.LEFT));
    }

    @Override
    protected void computeParameters(FrameSegment frameSegment, int direction) {
        switch (frameSegment) {
            case TOP:
                startingY = 0;
                startingX = direction == 1 ? 0 : screenSize.width - unitWidth;
                ledCount = widthLedCount - 1;
                deltaX = direction * unitWidth;
                deltaY = 0;
                associatedFrameSegment = FrameSegment.TOP;
                break;
            case LEFT:
                startingX = 0;
                startingY = direction == 1 ? screenSize.height : unitHeight;
                ledCount = heightLedCount - 1;
                deltaX = 0;
                deltaY = -direction * unitHeight;
                associatedFrameSegment = FrameSegment.LEFT;
                break;
            case RIGHT:
                startingX = screenSize.width - unitWidth;
                startingY = direction == 1 ? 0 : screenSize.height;
                ledCount = heightLedCount;
                deltaX = 0;
                deltaY = direction * unitHeight;
                associatedFrameSegment = FrameSegment.RIGHT;
                break;
            case BOTTOM:
                startingX = direction == 1 ? screenSize.width - unitWidth : unitWidth;
                startingY = screenSize.height - unitHeight;
                ledCount = widthLedCount - 2;
                deltaX = -direction * unitWidth;
                deltaY = 0;
                associatedFrameSegment = FrameSegment.BOTTOM;
                break;
        }
    }

}
