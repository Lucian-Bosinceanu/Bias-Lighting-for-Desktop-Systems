package model.config.config;

import model.config.enums.audio.AudioProcessingType;

import java.util.prefs.Preferences;

public class AudioConfig extends MyConfig {

    private Preferences prefs;

    private static final String PROCESSING_TYPE = "PROCESSING_TYPE";
    private static final String FRAME_PRESET = "FRAME_PRESET";
    private static final String DYNAMIC_SENSIBILITY = "DYNAMIC_SENSIBILITY";
    private static final String SENSIBILITY = "SENSIBILITY";

    private static final String RELAXED = "RELAXED";
    private static final String UPBEAT_SMOOTH_FACTOR = "UPBEAT_SMOOTH_FACTOR";
    private static final String DOWNBEAT_SMOOTH_FACTOR = "DOWNBEAT_SMOOTH_FACTOR";

    private static final String INTERPOLATION = "RHYTHMIC_INTERPOLATION";
    private static final String FRAME_INTERPOLATION_PRESET = "FRAME_INTERPOLATION_PRESET";
    private static final String BASE_ANIMATION_SPEED = "BASE_ANIMATION_SPEED";
    private static final String SHUFFLE_PRESETS = "SHUFFLE_PRESETS";

    private static final String ANIMATION_SPEED_FACTOR = "ANIMATION_SPEED_FACTOR";

    public AudioConfig(String propertyFile) {

        prefs = Preferences.userRoot().node(this.getClass().getName());
        //this.propertyFile = propertyFile;
    }

    public AudioProcessingType getProcessingType() {
//        return AudioProcessingType.valueOf(properties.getProperty(PROCESSING_TYPE));
        return AudioProcessingType.valueOf(prefs.get(PROCESSING_TYPE, AudioProcessingType.PULSE.toString()));
    }


    public Double getSensibility() {

//        return Double.valueOf(properties.getProperty(SENSIBILITY));
        return prefs.getDouble(SENSIBILITY, 50);
    }

    public void setProcessingType(AudioProcessingType type) {
//        properties.setProperty(PROCESSING_TYPE, type.toString());
        prefs.put(PROCESSING_TYPE, type.toString());
    }

    public void setSensibility(Double sensibility) {
//        properties.setProperty(SENSIBILITY, String.valueOf(sensibility));
        prefs.putDouble(SENSIBILITY, sensibility);
    }

    public boolean isRelaxedTransition() {
//        return Boolean.parseBoolean(properties.getProperty(RELAXED));
        return prefs.getBoolean(RELAXED, true);
    }

    public void setRelaxedTransition(Boolean isRelaxed) {
//        properties.setProperty(RELAXED, String.valueOf(isRelaxed));
        prefs.putBoolean(RELAXED, isRelaxed);
    }

    public String getFramePreset() {
//        return properties.getProperty(FRAME_PRESET);
        return prefs.get(FRAME_PRESET, "base.json");
    }

    public void setBasePreset(String presetName) {
//        properties.setProperty(FRAME_PRESET, presetName);
        prefs.put(FRAME_PRESET, presetName);
    }


    public long getBaseAnimationSpeed() {
//        return Long.parseLong(properties.getProperty(BASE_ANIMATION_SPEED));
        return prefs.getLong(BASE_ANIMATION_SPEED, 5000);
    }

    public void setBaseAnimationSpeed(long speed) {
//        properties.setProperty(BASE_ANIMATION_SPEED, String.valueOf(speed));
        prefs.putLong(BASE_ANIMATION_SPEED, speed);
    }

    public boolean isRhythmicInterpolation() {
//        return Boolean.parseBoolean(properties.getProperty(INTERPOLATION));
        return prefs.getBoolean(INTERPOLATION, false);
    }

    public void setRhythmicInterpolation(boolean isRhythmicInterpolated) {
//        properties.setProperty(INTERPOLATION, String.valueOf(isRhythmicInterpolated));
        prefs.putBoolean(INTERPOLATION, isRhythmicInterpolated);
    }

    public boolean isShufflingPresets() {
//        return Boolean.parseBoolean(properties.getProperty(SHUFFLE_PRESETS));
        return prefs.getBoolean(SHUFFLE_PRESETS, false);
    }

    public void setShufflePresets(boolean isShufflingPresets) {
//        properties.setProperty(SHUFFLE_PRESETS, String.valueOf(isShufflingPresets));
        prefs.putBoolean(SHUFFLE_PRESETS, isShufflingPresets);
    }

    public double getAnimationSpeedFactor() {
//        return Double.parseDouble(properties.getProperty(ANIMATION_SPEED_FACTOR));
        return prefs.getDouble(ANIMATION_SPEED_FACTOR, 2);
    }

    public void setAnimationSpeedFactor(double speedFactor) {
        properties.setProperty(ANIMATION_SPEED_FACTOR, String.valueOf(speedFactor));
    }

    public String getInterpolationPresetName() {
//        return properties.getProperty(FRAME_INTERPOLATION_PRESET);
        return prefs.get(FRAME_INTERPOLATION_PRESET, "base.json");
    }

    public void setInterpolationPresetName(String presetName) {
//        properties.setProperty(FRAME_INTERPOLATION_PRESET, presetName);
        prefs.put(FRAME_INTERPOLATION_PRESET, presetName);
    }

    public double getUpbeatSmoothFactor() {
//        return Double.parseDouble(properties.getProperty(UPBEAT_SMOOTH_FACTOR));
        return prefs.getDouble(UPBEAT_SMOOTH_FACTOR, 0.5);
    }

    public void setUpbeatSmoothFactor(double smoothFactor) {
//        properties.setProperty(UPBEAT_SMOOTH_FACTOR, String.valueOf(smoothFactor));
        prefs.putDouble(UPBEAT_SMOOTH_FACTOR, smoothFactor);
    }

    public double getDownBeatSmoothFactor() {

        return prefs.getDouble(DOWNBEAT_SMOOTH_FACTOR, 0.25);
//        return Double.parseDouble(properties.getProperty(DOWNBEAT_SMOOTH_FACTOR));
    }

    public void setDownBeatSmoothFactor(double smoothFactor) {
        prefs.putDouble(DOWNBEAT_SMOOTH_FACTOR, smoothFactor);
//        properties.setProperty(DOWNBEAT_SMOOTH_FACTOR, String.valueOf(smoothFactor));
    }

    public boolean isDynamicSensibility() {

        return prefs.getBoolean(DYNAMIC_SENSIBILITY, false);
//        return Boolean.parseBoolean(properties.getProperty(DYNAMIC_SENSIBILITY));
    }

    public void setDynamicSensibility(boolean isDynamicSensibility) {
        prefs.putBoolean(DYNAMIC_SENSIBILITY, isDynamicSensibility);
//        properties.setProperty(DYNAMIC_SENSIBILITY, String.valueOf(isDynamicSensibility));
    }
}
