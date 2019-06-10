package com.yzg.drawingboard;

import android.graphics.Canvas;
import android.graphics.Color;

public  abstract  class BaseAction {
      public  int  color;

      BaseAction(int color){
        this.color=color;
      }
      BaseAction( ){
          color= Color.BLACK;
      }
      public  abstract  void  draw(Canvas  canvas);

      public abstract void move(float mx, float my);
}




