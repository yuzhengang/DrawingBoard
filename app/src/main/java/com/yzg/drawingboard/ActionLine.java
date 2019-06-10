package com.yzg.drawingboard;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ActionLine extends  BaseAction{

     private  float startX;
     private  float startY;
     private  float stopX;
     private  float stopY;
     private  int size;

     ActionLine(   ){
         startX=0;
         startY=0;
         stopX=0;
         stopY=0;
     }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        canvas.drawLine(startX, startY, stopX, stopY, paint);

    }

    @Override
    public void move(float mx, float my) {
        stopX = mx;
        stopY = my;
    }

    ActionLine(float x, float y, int size, int color) {
        super(color);
        startX = x;
        startY = y;
        stopX = x;
        stopY = y;
        this.size = size;
    }

}





