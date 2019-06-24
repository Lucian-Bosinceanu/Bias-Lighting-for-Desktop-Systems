package model.config.config;

import model.config.enums.ambient.AmbientAnimationStyle;

import java.util.prefs.Preferences;

public class AmbientConfig extends MyConfig{

    private Preferences prefs;

    private static final String ANIMATION_STYLE = "ANIMATION_STYLE";
    private static final String ANIMATION_SPEED = "ANIMATION_SPEED";
    private static final String FRAME_PRESET = "FRAME_PRESET";
    private static final String INTERPOLATION_PRESET = "INTERPOLATION_PRESET";
    private static final String SHUFFLE_PRESETS = "SHUFFLE_PRESETS";


    public AmbientConfig(String propertyFile) {

        prefs = Preferences.userRoot().node(this.getClass().getName());
        //this.propertyFile = propertyFile;
    }

    public AmbientAnimationStyle getAnimationStyle() {
        return AmbientAnimationStyle.valueOf(prefs.get(ANIMATION_STYLE, AmbientAnimationStyle.STATIC.toString()));
//        return AmbientAnimationStyle.valueOf(properties.getProperty(ANIMATION_STYLE));
    }

    public void setAnimationStyle(AmbientAnimationStyle style) {
        prefs.put(ANIMATION_STYLE, style.toString());
//        properties.setProperty(ANIMATION_STYLE, String.valueOf(style));
    }

    public int getAnimationSpeed() {
        return prefs.getInt(ANIMATION_SPEED, 5000);
        //return Integer.parseInt(properties.getProperty(ANIMATION_SPEED));
    }

    public void setAnimationSpeed(int milliseconds) {
        prefs.putInt(ANIMATION_SPEED, milliseconds);
        //        properties.setProperty(ANIMATION_SPEED, String.valueOf(milliseconds));
    }

    public String getInterpolationPresetName() {

//        return properties.getProperty(INTERPOLATION_PRESET);
        return prefs.get(INTERPOLATION_PRESET, "base.json");
    }

    public void setInterpolationPreset(String presetName) {

//        properties.setProperty(INTERPOLATION_PRESET, presetName);
        prefs.put(INTERPOLATION_PRESET, presetName);
    }

    public boolean isShufflePresets() {

//        return Boolean.parseBoolean(properties.getProperty(SHUFFLE_PRESETS));
        return prefs.getBoolean(SHUFFLE_PRESETS, false);
    }

    public void setShufflePresets(boolean isShuffling) {
//        properties.setProperty(SHUFFLE_PRESETS, String.valueOf(isShuffling));
        prefs.putBoolean(SHUFFLE_PRESETS, isShuffling);
    }

    public String getFramePresetName() {

        return prefs.get(FRAME_PRESET, "base.json");
//        return properties.getProperty(FRAME_PRESET);
    }

    public void setFramePresetName(String presetName) {

//        properties.setProperty(FRAME_PRESET, presetName);
        prefs.put(FRAME_PRESET, presetName);
    }
}
