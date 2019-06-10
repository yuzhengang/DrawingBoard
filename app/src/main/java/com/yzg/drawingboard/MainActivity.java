package com.yzg.drawingboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawingBoardView   drawBoardView;
    private AlertDialog mColorDialog;
    private AlertDialog mPaintDialog;
    private AlertDialog mShapeDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawBoardView=findViewById(R.id.drawBoardView);
        drawBoardView.setSize(dip2px(5));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return drawBoardView.onTouchEvent(event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.main_color:
                showColorDialog();
                break;
            case R.id.main_size:
                showSizeDialog();
                break;
            case R.id.main_action:
                showShapeDialog();
                break;
            case R.id.main_revoke:
                drawBoardView.back();
                break;
            case R.id.main_reset:
                drawBoardView.reset();
                break;
            case R.id.main_save:
                String path = drawBoardView.saveBitmap(drawBoardView);
                Toast.makeText(this, "保存图片的路径为：" + path,  Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void showColorDialog() {
        if(mColorDialog == null){
            mColorDialog = new AlertDialog.Builder(this)
                    .setTitle("选择颜色")
                    .setSingleChoiceItems(new String[]{"蓝色", "红色", "黑色"}, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0:
                                            drawBoardView.setColor("#0000ff");
                                            break;
                                        case 1:
                                            drawBoardView.setColor("#ff0000");
                                            break;
                                        case 2:
                                            drawBoardView.setColor("#272822");
                                            break;
                                        default:break;
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
        }
        mColorDialog.show();
    }

    /**
     * 显示选择画笔粗细的对话框
     */
    private void showSizeDialog(){
        if(mPaintDialog == null){
            mPaintDialog = new AlertDialog.Builder(this)
                    .setTitle("选择画笔粗细")
                    .setSingleChoiceItems(new String[]{"细", "中", "粗"}, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0:
                                            drawBoardView.setSize(dip2px(5));
                                            break;
                                        case 1:
                                            drawBoardView.setSize(dip2px(10));
                                            break;
                                        case 2:
                                            drawBoardView.setSize(dip2px(15));
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
        }
        mPaintDialog.show();
    }

    /**
     * 显示选择画笔形状的对话框
     */
    private void showShapeDialog(){
        if(mShapeDialog == null){
            mShapeDialog = new AlertDialog.Builder(this)
                    .setTitle("选择形状")
                    .setSingleChoiceItems(new String[]{"路径", "直线", "矩形", "圆形"}, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0:
                                            drawBoardView.setType(DrawingBoardView.ActionType.Path);
                                            break;
                                        case 1:
                                            drawBoardView.setType(DrawingBoardView.ActionType.Line);
                                            break;
                                        case 2:
                                            drawBoardView.setType(DrawingBoardView.ActionType.Rect);
                                            break;
                                        case 3:
                                            drawBoardView.setType(DrawingBoardView.ActionType.Circle);
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            }).create();
        }
        mShapeDialog.show();
    }
    private int dip2px(float dpValue){
        final float scale = getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
}
