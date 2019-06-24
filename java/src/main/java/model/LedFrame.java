package model;


import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class LedFrame {

    private int widthLedCount;
    private int heightLedCount;

    private List<Color> ledFrame;

    public LedFrame(int widthLedCount, int heightLedCount) {
        this.widthLedCount = widthLedCount;
        this.heightLedCount = heightLedCount;
        ledFrame = new ArrayList<>();
    }

    public int getWidthLedCount() {
        return widthLedCount;
    }

    public void setWidthLedCount(int widthLedCount) {
        this.widthLedCount = widthLedCount;
    }

    public int getHeightLedCount() {
        return heightLedCount;
    }

    public void setHeightLedCount(int heightLedCount) {
        this.heightLedCount = heightLedCount;
    }

    public List<Color> getLedFrame() {
        return ledFrame;
    }

}
