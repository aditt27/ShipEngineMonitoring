package com.adibu.shipmonitoring.model;

public class WarningModel {

    String Categories;
    String title;
    String message;

    public WarningModel(String categories, String title, String message) {
        Categories = categories;
        this.title = title;
        this.message = message;
    }

    public String getCategories() {
        return Categories;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "WarningModel{" +
                "Categories='" + Categories + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
