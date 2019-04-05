package com.adibu.shipmonitoring.model;

public class GaugeModel {
    private String title;
    private String unit;
    private float min;
    private float max;
    private float current;
    private String id;


    public GaugeModel(String title, String unit, float min, float max, String id) {
        this.title = title;
        this.unit = unit;
        this.min = min;
        this.max = max;
        this.id = id;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
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

    public String getUnit() {
        return unit;
    }

    public String getId() {
        return id;
    }

}
