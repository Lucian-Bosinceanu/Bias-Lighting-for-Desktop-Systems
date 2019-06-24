package controller.algorithms.algorithm_manager;

import controller.algorithms.algorithm_manager.ambient.AmbientAlgorithmManager;
import controller.algorithms.algorithm_manager.audio.AudioAlgorithmManager;
import controller.algorithms.algorithm_manager.video.VideoAlgorithmManager;

public class AlgorithmManager {

    private static VideoAlgorithmManager videoAlgorithmManager = null;
    private static AudioAlgorithmManager audioAlgorithmManager = null;
    private static AmbientAlgorithmManager ambientAlgorithmManager = null;

    private AlgorithmManager() {

    }

    public static VideoAlgorithmManager getVideoAlgorithms() {

        if (videoAlgorithmManager == null) {
            videoAlgorithmManager = new VideoAlgorithmManager();
        }

        return videoAlgorithmManager;
    }

    public static AudioAlgorithmManager getAudioAlgorithms() {

        if (audioAlgorithmManager == null) {
            audioAlgorithmManager = new AudioAlgorithmManager();
        }

        return audioAlgorithmManager;
    }

    public static AmbientAlgorithmManager getAmbientAlgorithms() {
        if (ambientAlgorithmManager == null) {
            ambientAlgorithmManager = new AmbientAlgorithmManager();
        }

        return ambientAlgorithmManager;
    }
}
