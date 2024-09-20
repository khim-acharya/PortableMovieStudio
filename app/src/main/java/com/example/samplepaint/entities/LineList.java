package com.example.samplepaint.entities;

import android.graphics.Paint;

import java.util.ArrayList;

public class LineList {
    public ArrayList<Line> lineArrayList;
    public Paint paint;

    public LineList( Paint paint) {
        this.lineArrayList = new ArrayList<>();
        this.paint = paint;
    }


}
