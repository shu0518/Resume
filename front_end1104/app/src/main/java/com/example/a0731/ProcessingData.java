// ProcessingData 類別 (假設已有的數據模型)
package com.example.a0731;

public class ProcessingData {
    private String frameNumber;
    private String floor;
    private String layout;
    private double screenWindowHori;  // 紗窗寬度
    private double glassWidth;        // 玻璃寬度
    private double glassLength;       // 玻璃長度
    private boolean isSelected;

    // 原有的構造函數：6 個參數
    public ProcessingData(String frameNumber, String floor, String layout,
                          double screenWindowHori, double glassWidth, double glassLength) {
        this.frameNumber = frameNumber;
        this.floor = floor;
        this.layout = layout;
        this.screenWindowHori = screenWindowHori;
        this.glassWidth = glassWidth;
        this.glassLength = glassLength;
        this.isSelected = false; // 默認值為未選中
    }

    // 新增的重載構造函數：3 個參數，使用預設值
    public ProcessingData(String frameNumber, String floor, String layout) {
        this.frameNumber = frameNumber;
        this.floor = floor;
        this.layout = layout;
        this.screenWindowHori = 0.0;  // 預設值
        this.glassWidth = 0.0;        // 預設值
        this.glassLength = 0.0;       // 預設值
        this.isSelected = false;      // 預設值為未選中
    }

    // 新增的重載構造函數：7 個參數，包含選中狀態
    public ProcessingData(String frameNumber, String floor, String layout,
                          double screenWindowHori, double glassWidth, double glassLength, boolean isSelected) {
        this.frameNumber = frameNumber;
        this.floor = floor;
        this.layout = layout;
        this.screenWindowHori = screenWindowHori;
        this.glassWidth = glassWidth;
        this.glassLength = glassLength;
        this.isSelected = isSelected; // 指定是否選中
    }

    // Getter 方法
    public String getFrameNumber() {
        return frameNumber;
    }

    public String getFloor() {
        return floor;
    }

    public String getLayout() {
        return layout;
    }

    public double getScreenWindowHori() {
        return screenWindowHori;
    }

    public double getGlassWidth() {
        return glassWidth;
    }

    public double getGlassLength() {
        return glassLength;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
