package model.config.config;

import model.config.enums.video.VideoFrameUpdateStyle;
import model.config.enums.video.VideoImageFilter;
import model.config.enums.video.VideoProcessingDepth;
import model.config.enums.video.VideoProcessingType;

import java.awt.*;
import java.util.prefs.Preferences;

public class VideoConfig extends MyConfig{

    private Preferences prefs;

    private static final String DISPLAY = "DISPLAY";
    private static final String UPDATE_RATE = "UPDATE_RATE";
    private static final String PROCESSING_TYPE = "PROCESSING_TYPE";
    private static final String USE_BRIGHTNESS_REGULATOR = "USE_BRIGHTNESS_REGULATOR";
    private static final String BRIGHTNESS = "BRIGHTNESS";
    private static final String IMAGE_FILTER = "IMAGE_FILTER";
    private static final String UPDATE_STYLE = "UPDATE_STYLE";
    private static final String PROCESS_DEPTH = "PROCESS_DEPTH";
    private static final String SMOOTH_FACTOR = "SMOOTH_FACTOR";
    private static final String LAZY_COMFORT_RANGE = "LAZY_COMFORT_RANGE";


    public VideoConfig(String videoFile) {

        prefs = Preferences.userRoot().node(this.getClass().getName());
//        this.propertyFile = videoFile;
    }

    public Rectangle getCurrentScreen() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[getDisplayId()].getConfigurations()[0].getBounds();
    }

    public Integer getUpdateRate() {

        return prefs.getInt(UPDATE_RATE, 30);
//        return Integer.valueOf(properties.getProperty(UPDATE_RATE));
    }

    public void setUpdateRate(Integer updateRate) {

        prefs.putInt(UPDATE_RATE, updateRate);
//        properties.setProperty(UPDATE_RATE, String.valueOf(refreshRate));
    }

    public VideoProcessingType getProcessingType() {
//        String type = properties.getProperty(PROCESSING_TYPE);
        String type = prefs.get(PROCESSING_TYPE, VideoProcessingType.UNIFORM.toString());
        return VideoProcessingType.valueOf(type);
    }

    public void setProcessingType(VideoProcessingType type) {
//        properties.setProperty(PROCESSING_TYPE, String.valueOf(type));
        prefs.put(PROCESSING_TYPE, String.valueOf(type));
    }

    public int getDisplayId() {
        return prefs.getInt(DISPLAY, 0);
//        return Integer.parseInt(properties.getProperty(DISPLAY));
    }

    public void setDisplayId(int id) {

        prefs.putInt(DISPLAY, id);
//        properties.setProperty(DISPLAY, String.valueOf(id));
    }

    public Float getBrightness() {

        return prefs.getFloat(BRIGHTNESS, 0.5f);
//        return Float.valueOf(properties.getProperty(BRIGHTNESS));
    }

    public void setBrightness(Float brightness) {

        prefs.putFloat(BRIGHTNESS, brightness);
//        properties.setProperty(BRIGHTNESS, String.valueOf(brightness));
    }

    public Boolean isBrightnessRegulated() {

        return prefs.getBoolean(USE_BRIGHTNESS_REGULATOR, false);
//        return Boolean.valueOf(properties.getProperty(USE_BRIGHTNESS_REGULATOR));
    }

    public void setBrightnessRegulated(Boolean state) {
        prefs.putBoolean(USE_BRIGHTNESS_REGULATOR, state);
//        properties.setProperty(USE_BRIGHTNESS_REGULATOR, String.valueOf(state));
    }

    public VideoFrameUpdateStyle getFrameToFrameUpdateStyle() {
//        String style = properties.getProperty(UPDATE_STYLE);
        String style = prefs.get(UPDATE_STYLE, VideoFrameUpdateStyle.INSTANT.toString());
        return VideoFrameUpdateStyle.valueOf(style);
    }

    public void setFrameToFrameUpdateStyle(VideoFrameUpdateStyle style) {
        prefs.put(UPDATE_STYLE, style.toString());
//        properties.setProperty(UPDATE_STYLE, String.valueOf(style));
    }

    public Integer getLazyUpdateStyleComfortRange(){
        return prefs.getInt(LAZY_COMFORT_RANGE, 64);
//        return Integer.valueOf(properties.getProperty(LAZY_COMFORT_RANGE));
    }

    public void setLazyUpdateStyleComfortRange(int range){
        prefs.putInt(LAZY_COMFORT_RANGE, range);
//        properties.setProperty(LAZY_COMFORT_RANGE, String.valueOf(range));
    }

    public VideoImageFilter getImageFilter() {
//        String filter = properties.getProperty(IMAGE_FILTER);
        String filter = prefs.get(IMAGE_FILTER, VideoImageFilter.NONE.toString());
        return VideoImageFilter.valueOf(filter);
    }

    public void setImageFilter(VideoImageFilter filter) {
        prefs.put(IMAGE_FILTER, filter.toString());
//        properties.setProperty(IMAGE_FILTER, String.valueOf(filter));
    }

    public VideoProcessingDepth getProcessingDepth() {
//        String depth = properties.getProperty(PROCESS_DEPTH);
        String depth = prefs.get(PROCESS_DEPTH, VideoProcessingDepth.MARGIN.toString());
        return VideoProcessingDepth.valueOf(depth);
    }

    public void setProcessingDepth(VideoProcessingDepth depth) {
        prefs.put(PROCESS_DEPTH, depth.toString());
//        properties.setProperty(PROCESS_DEPTH, String.valueOf(depth));
    }

    public Double getSmoothFactor() {

        return prefs.getDouble(SMOOTH_FACTOR, 0.5);
//        return Double.valueOf(properties.getProperty(SMOOTH_FACTOR));
    }

    public void setSmoothFactor(Double smoothFactor) {
        prefs.putDouble(SMOOTH_FACTOR, smoothFactor);
//        properties.setProperty(SMOOTH_FACTOR, String.valueOf(smoothFactor));
    }
}
