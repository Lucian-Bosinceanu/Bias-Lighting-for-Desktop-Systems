package model.config.config;

import model.config.enums.general.*;

import java.util.prefs.Preferences;

public class GeneralConfig extends MyConfig {

    private Preferences prefs;

    private static final String WIDTH = "WIDTH";
    private static final String HEIGHT = "HEIGHT";
    private static final String MODE = "MODE";
    private static final String CONSTRUCTION_TYPE = "CONSTRUCTION_TYPE";
    private static final String START_POSITION = "START_POSITION";
    private static final String DIRECTION = "DIRECTION";
    private static final String SERIAL_PORT = "SERIAL_PORT";
    private static final String STRIP_RGB_ORDER = "STRIP_RGB_ORDER";
    private static final String STATIC_FRAME_COLOR_STATE_FOLDER = "STATIC_FRAME_COLOR_STATE_FOLDER";

    public GeneralConfig(String propertyFile) {

        prefs = Preferences.userRoot().node(this.getClass().getName());
//        this.propertyFile = propertyFile;
    }

    public String getSerialPortName() {

        return prefs.get(SERIAL_PORT, "COM4");
//        return properties.getProperty(SERIAL_PORT);
    }

    public void setSerialPortName(String port) {

        prefs.put(SERIAL_PORT, port);
//        properties.setProperty(SERIAL_PORT, port);
    }

    public FrameConstructionType getConstructionType() {
        String constructionType = prefs.get(CONSTRUCTION_TYPE, FrameConstructionType.FRAME.toString());
        return FrameConstructionType.valueOf(constructionType);
    }

    public void setConstructionType(FrameConstructionType constructionType) {

        prefs.put(CONSTRUCTION_TYPE, constructionType.toString());
//        properties.setProperty(CONSTRUCTION_TYPE, constructionType.toString());
    }

    public Integer getWidth(){

        return prefs.getInt(WIDTH, 1);
//        return new Integer(properties.getProperty(WIDTH));
    }

    public void setWidth(Integer width) {

        prefs.putInt(WIDTH, width);
//        properties.setProperty(WIDTH, String.valueOf(width));
    }

    public Integer getHeight(){

        return prefs.getInt(HEIGHT, 1);
//        return new Integer(properties.getProperty(HEIGHT));
    }

    public void setHeight(Integer height) {

        prefs.putInt(HEIGHT, height);
//        properties.setProperty(HEIGHT, String.valueOf(height));
    }

    public FrameMode getFrameMode() {
//        String frameMode = properties.getProperty(MODE);
        String frameMode = prefs.get(MODE, FrameMode.NONE.toString());
        return FrameMode.valueOf(frameMode);
    }

    public void setFrameMode(FrameMode frameMode) {

        prefs.put(MODE, frameMode.toString());
//        properties.setProperty(MODE, frameMode.toString());
    }

    public FrameDirection getFrameDirection() {
//        String frameDirection = properties.getProperty(DIRECTION);
        String frameDirection = prefs.get(DIRECTION, FrameDirection.CLOCKWISE.toString());
        return FrameDirection.valueOf(frameDirection);
    }

    public void setFrameDirection(FrameDirection direction) {
        prefs.put(DIRECTION, direction.toString());
//        properties.setProperty(DIRECTION, String.valueOf(direction));
    }

    public FrameStartPosition getStartPosition() {
//        String framePosition = properties.getProperty(START_POSITION);
        String framePosition = prefs.get(START_POSITION, FrameStartPosition.LOWER_LEFT.toString());
        return FrameStartPosition.valueOf(framePosition);
    }

    public void setStartPosition(FrameStartPosition position) {
        prefs.put(START_POSITION, position.toString());
//        properties.setProperty(START_POSITION, position.toString());
    }

    public String getStaticFrameColorStateFolder() {

        return prefs.get(STATIC_FRAME_COLOR_STATE_FOLDER, "static_states");
//        return properties.getProperty(STATIC_FRAME_COLOR_STATE_FOLDER);
    }

    public void setStaticFrameColorStateFolder(String folder) {
        //properties.setProperty(STATIC_FRAME_COLOR_STATE_FOLDER, folder);
    }

    public StripRgbOrder getStripRgbOrder() {

        return StripRgbOrder.valueOf(prefs.get(STRIP_RGB_ORDER, StripRgbOrder.RGB.toString()));
//        return StripRgbOrder.valueOf(properties.getProperty(STRIP_RGB_ORDER));
    }

    public void setStripRgbOrder(StripRgbOrder rgbOrder) {
        prefs.put(STRIP_RGB_ORDER, rgbOrder.toString());
//        properties.setProperty(STRIP_RGB_ORDER, rgbOrder.toString());
    }
}
