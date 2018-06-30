package com.python.cat.studyview.bean.view;

public class Table {

    public String name;
    public double percent;// 0-100
    public int color;
    public float scale;

    public Table(String name, int percent, int color, float scale) {
        this.name = name;
        this.percent = percent;
        this.color = color;
        this.scale = scale;
    }
}
