package com.example.samplepaint.ui.custome_ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.samplepaint.R;
import com.example.samplepaint.entities.Line;
import com.example.samplepaint.entities.LineList;
import com.example.samplepaint.entities.MyPoint;
import com.example.samplepaint.view_models.PaintScreenViewModel;
import com.skydoves.colorpickerview.ActionMode;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.ArrayList;

public class DrawView extends View {

    //private Path drawPath;
    //private Paint drawPaint;
    MyPoint lastPoint;
    ArrayList<LineList> lineList;
    LineList tempLineObj;
    private Paint paintState;

    public DrawView(Context context, PaintScreenViewModel paintScreenViewModel) {
        super(context);
        this.lineList = paintScreenViewModel.lineList;
        this.paintState = paintScreenViewModel.paintState;
    }

    public void setPaint(PaintScreenViewModel paintScreenViewModel) {
        // todo: Pathの使い方と良い点を調査後削除
        //drawPath = new Path();
        paintState = paintScreenViewModel.paintState;
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
            for(LineList list: lineList){
                for(Line l: list.lineArrayList){
                    canvas.drawLine(l.start.x,l.start.y,l.end.x,l.end.y,list.paint);
                }
                //drawCanvas.drawPath(p,drawPaint);
            }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x1 = (int) event.getX();
                int y1 = (int) event.getY();
                lastPoint = new MyPoint(x1,y1);
                tempLineObj = new LineList(paintState);
                lineList.add(tempLineObj);

                //drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                int x2 = (int) event.getX();
                int y2 = (int) event.getY();
                MyPoint newPoint = new MyPoint(x2,y2);
                tempLineObj.lineArrayList.add(new Line(lastPoint,newPoint));
                lastPoint = newPoint;

                //drawPath.lineTo(touchX, touchY);
                //pathList.add(drawPath);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
    public void undoEvent() {
        if(!lineList.isEmpty()){
            lineList.remove(lineList.size()-1);
            invalidate();
        }
    }
    public void clearFrame() {
        if(!lineList.isEmpty()){
            lineList.clear();
            invalidate();
        }
    }

    public static class Dialogs {
        Context context;
        ColorPickerDialog.Builder colorPickerDialog;

        public Dialogs(Context context) {
            this.context = context;
            this.colorPickerDialog = getColorPickerDialog(context);
        }
        private ColorPickerDialog.Builder getColorPickerDialog(Context context) {
            return new ColorPickerDialog.Builder(context)
                    .setPreferenceName("MyColorPickerDialog")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("Select", new ColorEnvelopeListener() {
                        @Override
                        public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                            int currentBrushColor = envelope.getColor();
                            //DrawView drawingView = new DrawView(context, paintScreenViewModel);
                            //drawingView.setupDrawing(currentBrushColor, 10);
                        }
                    })
                    .setTitle("Select Color");
        }

        public ColorPickerView getColorPickerView() {
            return new ColorPickerView.Builder(context)
                    .setPreferenceName("MyColorPicker")
                    .setActionMode(ActionMode.LAST)
                    .setPaletteDrawable(ContextCompat.getDrawable(context, R.drawable.ic_launcher_background))
                    .build();
        }
    }
}
