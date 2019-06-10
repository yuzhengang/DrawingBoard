package com.yzg.drawingboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawingBoardView extends SurfaceView implements SurfaceHolder.Callback{

    private  SurfaceHolder mSurfaceHolder=null;

    private  BaseAction currentAction;

    private  int  currentColor= Color.BLACK;

    private  int  currentSize=5;

    private Paint  mPaint;

    private List<BaseAction>    mBaseActions;

    private Bitmap  mBitmap;

    private ActionType mActionType = ActionType.Path;


    public DrawingBoardView(Context context) {
        super(context);
        init();
    }

    public DrawingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void  init( ){
        mSurfaceHolder=this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
        mPaint=new Paint( );
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(currentSize);
    }
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Canvas canvas=mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        mBaseActions=new ArrayList<>( );
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        if(action==MotionEvent.ACTION_CANCEL){
            return  false;
        }
        float  touchX=event.getX();
        float  touchY=event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
               setCurAction(touchX,touchY);
            break;

            case MotionEvent.ACTION_MOVE:
                Canvas  canvas=mSurfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                for (BaseAction  baseAction:mBaseActions){
                    baseAction.draw(canvas);
                }
                currentAction.move(touchX,touchY);
                currentAction.draw(canvas);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                break;
            case MotionEvent.ACTION_UP:
                mBaseActions.add(currentAction);
                currentAction=null;
                break;

               default:

                   break;
        }
        return   true;
    }

    private void setCurAction(float touchX, float touchY) {
        switch (mActionType){
            case Point:
              currentAction=new ActionPoint(touchX,touchY,currentColor);
             break;
            case Path:
              currentAction=new ActionPath(touchX,touchY,currentSize,currentColor);
             break;
            case Line:
              currentAction = new ActionLine(touchX, touchY, currentSize, currentColor);
              break;
            case Rect:
                currentAction = new ActionRect(touchX, touchY, currentSize, currentColor);
                break;
            case Circle:
                currentAction = new ActionCircle(touchX, touchY, currentSize, currentColor);
                break;
        }
    }

    public  String  saveBitmap(DrawingBoardView  boardView){
        String  path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/drawboard/"+System.currentTimeMillis()+".png";
        if(!new File(path).exists()){
            new File(path).getParentFile().mkdir();
        }
        savePicByPNG(boardView.getBitmap(),path);
        return path;
    }



    /**
     * 设置画笔的颜色
     *
     * @param color 颜色
     */
    public void setColor(String color) {
        this.currentColor = Color.parseColor(color);
    }

    /**
     * 设置画笔的粗细
     *
     * @param size 画笔的粗细
     */
    public void setSize(int size) {
        this.currentSize = size;
    }

    /**
     * 设置画笔的形状
     *
     * @param type 画笔的形状
     */
    public void setType(ActionType type) {
        this.mActionType = type;
    }




    /**
     *   将Canvas 转化为 一个 Bitmap
     */
    public  Bitmap  getBitmap(   ){
        mBitmap=Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
        Canvas   canvas=new Canvas(mBitmap);
        doDraw(canvas);
        return  mBitmap;
    }
    /**
     *   将一个Bitmap 保存在 一个指定的路径中
     *   @param  bitmap
     *   @param  filePath
     */
    public  static void  savePicByPNG(Bitmap bitmap,String  filePath){
        FileOutputStream  fileOutputStream;
        try {
            fileOutputStream=new FileOutputStream(filePath);
            if(null!=fileOutputStream){
                bitmap.compress(Bitmap.CompressFormat.PNG,90,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private  void  doDraw(Canvas   canvas){
        canvas.drawColor(Color.TRANSPARENT);
        for (BaseAction  action:mBaseActions){
            action.draw(canvas);
        }
        canvas.drawBitmap(mBitmap,0,0,mPaint);
    }


    public  boolean   back(  ){
       if(mBaseActions!=null&&mBaseActions.size()>0){
           mBaseActions.remove(mBaseActions.size()-1);
           Canvas canvas=mSurfaceHolder.lockCanvas();
           canvas.drawColor(Color.WHITE);
           for (BaseAction action:mBaseActions){
               action.draw(canvas);
           }
           mSurfaceHolder.unlockCanvasAndPost(canvas);
           return true;
       }
       return  false;
    }

    /**
     * 重置签名
     */
    public void reset(){
        if(mBaseActions != null && mBaseActions.size() > 0){
            mBaseActions.clear();
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            for (BaseAction action : mBaseActions) {
                action.draw(canvas);
            }
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    enum  ActionType{
        Point,Path,Line,Rect,Circle
    }
}
















