package com.adibu.shipmonitoring;

public class GridModel {
    private int judul;
    private String status;

    public GridModel(int judul) {
        this.judul = judul;
    }

    public GridModel(String status) {
        this.status = status;
    }

    public GridModel(int judul, String status) {
        this.judul = judul;
        this.status = status;
    }

    public int getJudul() {
        return judul;
    }

    public void setJudul(int judul) {
        this.judul = judul;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
