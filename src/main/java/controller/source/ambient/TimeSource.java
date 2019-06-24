package controller.source.ambient;

import controller.source.Source;
import model.config.ConfigManager;

public class TimeSource implements Source<Long> {

    private static Long currentTime;
    private static Long startTime;
    private static Long spinAnimationTransitionTime;

    public void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    public void setWaveAnimationTransitionTime(int ledCount) {
        long animationSpeed = ConfigManager.getAmbientConfig().getAnimationSpeed();
        spinAnimationTransitionTime = animationSpeed / ledCount;
    }

    public Long getSpinAnimationTransitionTime() {
        return spinAnimationTransitionTime;
    }

    @Override
    public void update(Long source) {
        currentTime = System.currentTimeMillis();
    }

    public Long getElapsedTime() {
        return currentTime - startTime;
    }

    @Override
    public Long getSource() {
        if (currentTime == null) {
            currentTime = System.currentTimeMillis();
        }

        return currentTime;
    }
}
