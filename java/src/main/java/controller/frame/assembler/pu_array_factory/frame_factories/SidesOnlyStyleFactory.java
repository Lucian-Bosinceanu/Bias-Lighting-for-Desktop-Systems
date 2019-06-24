package controller.frame.assembler.pu_array_factory.frame_factories;

import model.config.ConfigManager;
import model.config.enums.general.FrameSegment;

import java.util.ArrayList;
import java.util.Arrays;

public class SidesOnlyStyleFactory extends StyleFactory {


    public SidesOnlyStyleFactory() {

        heightLedCount = ConfigManager.getGlobalConfig().getHeight();

        unitHeight = screenSize.height / heightLedCount;
        unitWidth = unitHeight;

        frameSegments = new ArrayList<>(
                Arrays.asList(null,
                        FrameSegment.RIGHT,
                        null,
                        FrameSegment.LEFT));
    }

    @Override
    protected void computeParameters(FrameSegment frameSegment, int direction) {
        switch (frameSegment) {
            case LEFT:
                startingX = 0;
                startingY = direction == 1 ? screenSize.height : unitHeight;
                ledCount = heightLedCount;
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
        }
    }
}
