package com.yzg.drawingboard;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ActionPoint   extends  BaseAction{
    private  float  x;
    private  float  y;
    ActionPoint(float px,float  py,int color){
        super(color);
        this.x=px;
        this.y=py;
    }
    @Override
    public void draw(Canvas canvas) {
        Paint   paint=new Paint( );
        paint.setColor(color);
        canvas.drawPoint(x,y,paint);
    }
    @Override
    public void move(float mx, float my) {

    }
}












