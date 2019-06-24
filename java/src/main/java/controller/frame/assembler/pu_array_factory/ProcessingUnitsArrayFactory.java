package controller.frame.assembler.pu_array_factory;

import controller.frame.assembler.pu_array_factory.frame_factories.*;
import controller.processing_units.units.ProcessingUnit;
import model.config.ConfigManager;
import model.config.enums.general.FrameDirection;

import java.util.ArrayList;
import java.util.List;

public class ProcessingUnitsArrayFactory {

    private int direction;

    public List<ProcessingUnit> build() {

        List<ProcessingUnit> processingUnits = new ArrayList<>();
        direction = ConfigManager.getGlobalConfig().getFrameDirection() == FrameDirection.CLOCKWISE ? 0 : -1;

        int startingPoint = computeStartingPoint();

        switch (ConfigManager.getGlobalConfig().getConstructionType()) {
            case FRAME:
                processingUnits = new FrameStyleFactory().buildFrame(startingPoint, direction);
                break;
            case LED_STRIP:
                processingUnits = new StripStyleFactory().buildFrame(startingPoint, direction);
                break;
            case SIDE_ONLY:
                processingUnits = new SidesOnlyStyleFactory().buildFrame(startingPoint, direction);
                break;
            case LAPTOP_LED_STRIP:
                processingUnits = new LaptopStripStyleFactory().buildFrame(startingPoint, direction);
                break;
            case LAPTOP_FRAME:
                processingUnits= new LaptopFrameStyleFactory().buildFrame(startingPoint, direction);
                break;
        }

        return processingUnits;
    }

    private int computeStartingPoint() {
        int startingPoint = -1;

        switch (ConfigManager.getGlobalConfig().getStartPosition()) {
            case UPPER_LEFT:
                startingPoint = 0;
                break;
            case UPPER_RIGHT:
                startingPoint = 1;
                break;
            case LOWER_RIGHT:
                startingPoint = 2;
                break;
            case LOWER_LEFT:
                startingPoint = 3;
                break;
        }

        startingPoint += direction;

        if (startingPoint == -1) {
            startingPoint = 3;
        }

        return startingPoint;
    }

}
