package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.io.FileManager;
import controller.io.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FrameColorPreset {

    private String name;
    private String baseColor;
    private String leftSegmentColor;
    private String rightSegmentColor;
    private String topSegmentColor;
    private String bottomSegmentColor;
    private Boolean isEditable;
    private List<String> individualColors;

    public FrameColorPreset() {
        baseColor = "0x00000000";
        leftSegmentColor = "0x00000000";
        rightSegmentColor = "0x00000000";
        topSegmentColor = "0x00000000";
        bottomSegmentColor = "0x00000000";
        isEditable = true;
        individualColors = new ArrayList<>();
    }

    public String getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(String baseColor) {
        this.baseColor = baseColor;
    }

    public String getLeftSegmentColor() {
        return leftSegmentColor;
    }

    public void setLeftSegmentColor(String leftSegmentColor) {
        this.leftSegmentColor = leftSegmentColor;
    }

    public String getRightSegmentColor() {
        return rightSegmentColor;
    }

    public void setRightSegmentColor(String rightSegmentColor) {
        this.rightSegmentColor = rightSegmentColor;
    }

    public String getTopSegmentColor() {
        return topSegmentColor;
    }

    public void setTopSegmentColor(String topSegmentColor) {
        this.topSegmentColor = topSegmentColor;
    }

    public String getBottomSegmentColor() {
        return bottomSegmentColor;
    }

    public void setBottomSegmentColor(String bottomSegmentColor) {
        this.bottomSegmentColor = bottomSegmentColor;
    }

    public Boolean getEditable() {
        return isEditable;
    }

    public void setEditable(Boolean editable) {
        isEditable = editable;
    }

    public List<String> getIndividualColors() {
        return individualColors;
    }

    public void setIndividualColors(List<String> individualColors) {
        this.individualColors = individualColors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static FrameColorPreset loadColorState(String jsonFile) throws IOException {
        String jsonContent = FileManager.getTextFromFile(jsonFile);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        return gson.fromJson(jsonContent, FrameColorPreset.class);
    }


    public void saveColorState() throws IOException {
        String json = JsonParser.getJsonFromObject(this);
        String fileName = name + ".json";

        FileManager.writeTextToFile(fileName, json);
    }

    public boolean deleteColorState() throws IOException {
        String fileName = name + ".json";

        return FileManager.deleteFile(fileName);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        if (!isEditable) {
            stringBuilder.append("*");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrameColorPreset that = (FrameColorPreset) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(baseColor, that.baseColor) &&
                Objects.equals(leftSegmentColor, that.leftSegmentColor) &&
                Objects.equals(rightSegmentColor, that.rightSegmentColor) &&
                Objects.equals(topSegmentColor, that.topSegmentColor) &&
                Objects.equals(bottomSegmentColor, that.bottomSegmentColor) &&
                Objects.equals(isEditable, that.isEditable) &&
                Objects.equals(individualColors, that.individualColors);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, baseColor, leftSegmentColor, rightSegmentColor, topSegmentColor, bottomSegmentColor, isEditable, individualColors);
    }
}
