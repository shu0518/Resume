package com.example.a0731;

import org.json.JSONObject;

public class MeasuredData {
    private String spec;
    private String specification;
    private String location;
    private String length;
    private String width;
    private String quantity;
    private String windowType;
    private String id;
    private boolean isDoor;
    private boolean isSelected; // 新增的選擇狀態

    public MeasuredData(String id, String specification, String spec, String location, String length, String width, String quantity, boolean isDoor) {
        this.id=id;
        this.specification= specification;
        this.spec = spec;
        this.location = location;
        this.length = length;
        this.width = width;
        this.quantity = quantity;
        this.isDoor = isDoor;
    }

    // Getter 方法
    public String getid(){return id;}
    public String getspec() {
        return spec;
    }
    public String getSpecification() { return specification; }
    // Getter 和 Setter 方法
    public String getLocation() {
        return location;
    }

    public String getLength() {
        return length;
    }

    public String getWidth() {
        return width;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWindowType() {
        return windowType;
    }

    public boolean isDoor() {
        return isDoor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
