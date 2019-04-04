package com.adibu.shipmonitoring;

public class GaugeModel {
    private String title;
    private String unit;
    private float min;
    private float max;
    private float current;


    public GaugeModel(String title, String unit, float min, float max) {
        this.title = title;
        this.unit = unit;
        this.min = min;
        this.max = max;
    }

    public GaugeModel(float current) {
        this.current = current;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
