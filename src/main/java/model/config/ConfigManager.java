package model.config;

import model.config.config.AmbientConfig;
import model.config.config.AudioConfig;
import model.config.config.GeneralConfig;
import model.config.config.VideoConfig;

import java.io.IOException;

public class ConfigManager {

    private static String audioConfigFile = "properties/audio.config.properties";
    private static String videoConfigFile = "properties/video.config.properties";
    private static String globalConfigFile = "properties/global.config.properties";
    private static String staticConfigFile = "properties/ambient.config.properties";

    private static AudioConfig audioConfig;
    private static VideoConfig videoConfig;
    private static GeneralConfig globalConfig;
    private static AmbientConfig ambientConfig;

    private ConfigManager() { }

    public static void init() {
        audioConfig = new AudioConfig(audioConfigFile);
        videoConfig = new VideoConfig(videoConfigFile);
        globalConfig = new GeneralConfig(globalConfigFile);
        ambientConfig = new AmbientConfig(staticConfigFile);
        //loadProperties();
    }

    private static void loadProperties() {
        try {
            videoConfig.loadProperties();
            audioConfig.loadProperties();
            globalConfig.loadProperties();
            ambientConfig.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveProperties() {
        try {
            audioConfig.saveProperties();
            videoConfig.saveProperties();
            globalConfig.saveProperties();
            ambientConfig.saveProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static AudioConfig getAudioConfig() {
        return audioConfig;
    }

    public static VideoConfig getVideoConfig() {
        return videoConfig;
    }

    public static GeneralConfig getGlobalConfig() {
        return globalConfig;
    }

    public static AmbientConfig getAmbientConfig() {return ambientConfig;}
}
