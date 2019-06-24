package controller.source.ambient;

import controller.io.FileManager;
import controller.source.Source;
import model.FrameColorPreset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ColorPresetSource implements Source<FrameColorPreset> {

    private FrameColorPreset currentState;
    private FrameColorPreset nextState;
    private List<FrameColorPreset> allPresets;

    @Override
    public void update(FrameColorPreset source) {
        currentState = source;
    }

    public void updateNextState(FrameColorPreset nextState) {
        this.nextState = nextState;
    }

    @Override
    public FrameColorPreset getSource() {
        return currentState;
    }

    public FrameColorPreset getCurrentState() {
        return currentState;
    }

    public FrameColorPreset getNextState() {
        return nextState;
    }

    public void loadAllPresets() {
        allPresets = new ArrayList<>();
        String path = FileManager.getPresetPath();
        File[] files = new File(path).listFiles();

        for (File f : files) {
            addPreset(f.toPath());
        }
    }

    private void addPreset(Path presetPath) {
        try {
            FrameColorPreset colorState = FrameColorPreset.loadColorState(presetPath.getFileName().toString());
            allPresets.add(colorState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FrameColorPreset> getAllPresets() {
        return allPresets;
    }
}
