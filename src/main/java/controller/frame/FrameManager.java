package controller.frame;

import controller.frame.assembler.FrameBuilder;
import controller.frame.assembler.pu_array_factory.ProcessingUnitsArrayFactory;
import controller.io.serial.SerialCommunicator;
import controller.processing_units.units.ProcessingUnit;
import javafx.scene.paint.Color;
import jssc.SerialPortException;
import model.LedFrame;

import java.util.List;

public class FrameManager {

    private LedFrame ledFrame;
    private List<ProcessingUnit> processingUnits;

    public FrameManager() {
        System.out.println("New FrameManager is created");
        this.ledFrame = new FrameBuilder().build();
        this.processingUnits = null;
    }

    public void constructProcessingUnits(){
        System.out.println("Processing Units construction begins");
        this.processingUnits = new ProcessingUnitsArrayFactory().build();
    }

    public void update(){
        int ledIndex;
        for (ProcessingUnit processingUnit : processingUnits) {
            Color newColor = processingUnit.process();
            ledIndex = processingUnit.getLedIndex();
            ledFrame.getLedFrame().set(ledIndex, newColor);
        }

    }

    public List<Color> getLedArray() {
        return ledFrame.getLedFrame();
    }

    public List<ProcessingUnit> getProcessingUnits() {
        return processingUnits;
    }

    public int getFrameHeightLedCount() {
        return ledFrame.getHeightLedCount();
    }

    public int getFrameWidthLedCount() {
        return ledFrame.getWidthLedCount();
    }

    public void turnOff() throws SerialPortException {
        SerialCommunicator.sendTurnOffCommand();
    }
}
