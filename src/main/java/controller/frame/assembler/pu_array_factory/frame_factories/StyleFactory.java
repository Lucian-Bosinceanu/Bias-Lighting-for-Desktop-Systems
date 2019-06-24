package controller.frame.assembler.pu_array_factory.frame_factories;

import controller.processing_units.factories.AmbientProcessingUnitFactory;
import controller.processing_units.factories.AudioProcessingUnitFactory;
import controller.processing_units.factories.ProcessingUnitFactory;
import controller.processing_units.factories.VideoProcessingUnitFactory;
import controller.processing_units.units.ProcessingUnit;
import model.config.ConfigManager;
import model.config.enums.general.FrameSegment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class StyleFactory {

    protected static List<ProcessingUnit> processingUnits;
    protected static Rectangle screenSize;
    protected static int unitHeight;
    protected static int unitWidth;
    protected static int heightLedCount;
    protected static int widthLedCount;
    protected static int ledIndex;

    protected int startingX = 0;
    protected int startingY = 0;
    protected int deltaX = 0;
    protected int deltaY = 0;
    protected int ledCount = 0;
    protected FrameSegment associatedFrameSegment;

    protected static ArrayList<FrameSegment> frameSegments;

    public StyleFactory() {
        screenSize = ConfigManager.getVideoConfig().getCurrentScreen();
        processingUnits = new ArrayList<>();

    }

    public List<ProcessingUnit> buildFrame(int startingPoint, int direction) {
        processingUnits = new ArrayList<>();
        ledIndex = 0;

        if (direction == 0) {
            for (int i = startingPoint; i < frameSegments.size(); i++) {
                if (frameSegments.get(i) != null) {
                    buildFrameSegment(frameSegments.get(i), direction);
                }
            }

            for (int i = 0; i < startingPoint; i++) {
                if (frameSegments.get(i) != null) {
                    buildFrameSegment(frameSegments.get(i), direction);
                }
            }
        } else {
            for (int i = startingPoint; i >= 0; i--) {
                if (frameSegments.get(i) != null) {
                    buildFrameSegment(frameSegments.get(i), direction);
                }
            }

            for (int i = 3; i > startingPoint; i--) {
                if (frameSegments.get(i) != null) {
                    buildFrameSegment(frameSegments.get(i), direction);
                }
            }
        }

        return processingUnits;
    }

    private void buildFrameSegment(FrameSegment frameSegment, int direction) {
        direction = direction == 0 ? 1 : -1; // 1 - clockwise
        associatedFrameSegment = FrameSegment.RIGHT;

        computeParameters(frameSegment, direction);
        buildSegment();
    }

    protected abstract void computeParameters(FrameSegment frameSegment, int direction);

    private void buildSegment() {
        int x = startingX;
        int y = startingY;
        int xPreConstructionIncrement = deltaX < 0 ? deltaX : 0;
        int yPreConstructionIncrement = deltaY < 0 ? deltaY : 0;
        int xPostConstructionIncrement = deltaX > 0 ? deltaX : 0;
        int yPostConstructionIncrement = deltaY > 0 ? deltaY : 0;
        ProcessingUnit unit;
        ProcessingUnitFactory processingUnitFactory = null;

        switch (ConfigManager.getGlobalConfig().getFrameMode()) {
            case VIDEO:
                processingUnitFactory = new VideoProcessingUnitFactory();
                break;
            case AUDIO:
                processingUnitFactory = new AudioProcessingUnitFactory();
                break;
            case AMBIENT:
                processingUnitFactory = new AmbientProcessingUnitFactory();
        }

        for (int i = 0; i < ledCount; i++) {

            x += xPreConstructionIncrement;
            y += yPreConstructionIncrement;

            unit = processingUnitFactory.getUnit(ledIndex, associatedFrameSegment, x, y, unitWidth, unitHeight);

//            switch (ConfigManager.getGlobalConfig().getFrameMode()) {
//                case VIDEO:
//                    unit = processingUnitFactory.getUnit(ledIndex, associatedFrameSegment, x, y, unitWidth, unitHeight);
//                    break;
//                case AUDIO:
//                    unit = AudioProcessingUnitFactory.getUnit(ledIndex, associatedFrameSegment);
//                    break;
//                case AMBIENT:
//                    unit = AmbientProcessingUnitFactory.getUnit(x, y, unitWidth, unitHeight, ledIndex, associatedFrameSegment);
//            }

            ledIndex++;
            processingUnits.add(unit);

            x += xPostConstructionIncrement;
            y += yPostConstructionIncrement;

        }
    }

}
