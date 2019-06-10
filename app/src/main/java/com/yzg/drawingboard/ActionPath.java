package com.yzg.drawingboard;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.widget.TextView;

public class ActionPath extends BaseAction {
    private Path  path;
    private int size;
    ActionPath( ){
       path=new Path( );
       size=1;
    }

    ActionPath(float x, float y, int size, int color ){
        super(color);
        path = new Path();
        this.size = size;
        path.moveTo(x, y);
        path.lineTo(x, y);
    }
    @Override
    public void draw(Canvas canvas) {
        Paint  paint=new Paint( );
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStrokeWidth(size); // 设置画笔粗细
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,paint);
    }

    @Override
    public void move(float mx, float my) {
        path.lineTo(mx, my);
    }
}
