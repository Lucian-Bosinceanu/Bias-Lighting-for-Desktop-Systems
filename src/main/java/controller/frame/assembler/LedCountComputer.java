package controller.frame.assembler;

import model.config.enums.general.FrameConstructionType;

public class LedCountComputer {

    private LedCountComputer(){}

    public static int compute(FrameConstructionType constructionType, int widthLedCount, int heightLedCount) {
        int result = 0;

        switch (constructionType){
            case FRAME:
                result = 2 * (widthLedCount + heightLedCount);
                break;
            case LED_STRIP:
                result = 2 * (widthLedCount + heightLedCount) - 4;
                break;
            case SIDE_ONLY:
                result  = 2 * heightLedCount;
                break;
            case LAPTOP_FRAME:
                result = 2 * heightLedCount + widthLedCount;
                break;
            case LAPTOP_LED_STRIP:
                result = 2 * heightLedCount + widthLedCount - 2;
        }

        return result;
    }
}
