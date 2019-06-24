package controller.processing_units.factories;

import controller.processing_units.units.ProcessingUnit;
import model.config.enums.general.FrameSegment;

public interface ProcessingUnitFactory {

    ProcessingUnit getUnit(int ledIndex, FrameSegment associatedFrameSegment, Integer... params);
    void setPreProcessingAlgorithms(ProcessingUnit unit);
    void setProcessingAlgorithms(ProcessingUnit unit);
    void setPostProcessingAlgorithms(ProcessingUnit unit);
}
