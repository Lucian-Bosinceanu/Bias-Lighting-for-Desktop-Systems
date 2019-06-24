package controller.workers;

import model.config.ConfigManager;
import model.config.enums.general.FrameMode;

public class MainWorker {

    private MainWorker() {}

    private static Worker activeWorker;

    public static void start() {

        if (activeWorker != null) {
            return;
        }

        System.out.println("Main worker starts");

        FrameMode mode = ConfigManager.getGlobalConfig().getFrameMode();

        switch (mode) {
            case VIDEO:
                activeWorker = new VideoWorker();
                break;
            case AUDIO:
                activeWorker = new AudioWorker();
                break;
            case AMBIENT:
                activeWorker = new AmbientWorker();
        }

        new Thread(activeWorker).start();
    }

    public static void restart() {
        System.out.println("Main worker restarts");
        stop();
        start();
    }

    public static void stop() {
        if (activeWorker != null) {
            activeWorker.stop();
            activeWorker = null;
            System.out.println("Main worker stops");
        }
    }
}
