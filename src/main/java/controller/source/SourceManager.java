package controller.source;

import controller.source.ambient.ColorPresetSource;
import controller.source.ambient.TimeSource;
import controller.source.audio.AudioSource;
import controller.source.video.VideoSource;

public class SourceManager {

    private static VideoSource videoSource = null;
    private static AudioSource audioSource = null;
    private static TimeSource timeSource = null;
    private static ColorPresetSource colorPresetSource = null;

    private SourceManager() {}

    public static VideoSource getVideoSource() {
        if (videoSource == null) {
            videoSource = new VideoSource();
        }

        return videoSource;
    }

    public static AudioSource getAudioSource() {
        if (audioSource == null) {
            audioSource = new AudioSource();
        }

        return audioSource;
    }

    public static TimeSource getTimeSource() {
        if (timeSource == null) {
            timeSource = new TimeSource();
        }

        return timeSource;
    }

    public static ColorPresetSource getColorPresetSource() {
        if (colorPresetSource == null) {
            colorPresetSource = new ColorPresetSource();
        }

        return colorPresetSource;
    }

}
