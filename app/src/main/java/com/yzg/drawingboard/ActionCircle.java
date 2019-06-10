package com.yzg.drawingboard;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ActionCircle  extends  BaseAction {
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private float radius;
    private int size;

    ActionCircle(  ){
        startX = 0;
        startY = 0;
        stopX = 0;
        stopY = 0;
        radius = 0;
    }
    ActionCircle(float x, float y, int size, int color) {
        super(color);
        this.startX = x;
        this.startY = y;
        this.stopX = x;
        this.stopY = y;
        this.radius = 0;
        this.size = size;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(size);
        canvas.drawCircle((startX + stopX) / 2, (startY + stopY) / 2, radius, paint);
    }

    @Override
    public void move(float mx, float my) {
        stopX = mx;
        stopY = my;
        radius = (float) ((Math.sqrt((mx - startX) * (mx - startX) + (my - startY) * (my - startY))) / 2);
    }
}
