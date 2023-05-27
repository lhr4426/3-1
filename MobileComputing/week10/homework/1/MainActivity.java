package com.example.mc_week10_homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static int LINE = 1, CIRCLE = 2, RECT=3;
    static int curShape = LINE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        int id = item.getItemId();
        switch (id) {
            case R.id.item_line:
                curShape = LINE; // 선
                return true;
            case R.id.item_circle:
                curShape = CIRCLE; // 원
                return true;
            case R.id.item_rect:
                curShape = RECT;
                return true;
            case R.id.item_clear:
                setContentView(new MyGraphicView(this));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static class MyGraphicView extends View {
        int startX = -1, startY = -1, stopX = -1, stopY = -1;

        ArrayList<FigureData> figureList = new ArrayList<>();
        FigureData newFigure = null;

        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    newFigure = new FigureData();
                    // 처음에 newFigure를 만들 당시의 curShape를 저장
                    // 저장 안하면 나중에 모든 객체가 선/원/사각형 중 하나만 됨
                    newFigure.shape = curShape;
                    newFigure.startX = (int) event.getX();
                    newFigure.startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    newFigure.stopX = (int) event.getX();
                    newFigure.stopY = (int) event.getY();
                    this.invalidate();
                    // 화면에서 손 떼기 전까지는 newFigure에 데이터 저장한채로 그림
                    break;
                case MotionEvent.ACTION_UP:
                    newFigure.stopX = (int) event.getX();
                    newFigure.stopY = (int) event.getY();
                    this.invalidate();
                    // 화면에서 손 떼면 figureList에 현재의 newFigure를 저장함
                    figureList.add(newFigure);
                    // 그리고 newFigure 초기화
                    newFigure = null;
                    break;
            }
            return true;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);

            for(FigureData figure : figureList) {
                myDrawing(figure, canvas, paint);
            } // 저장된 도형들 그리기
            if (newFigure != null) {
                myDrawing(newFigure, canvas, paint);
            } // 손 안뗀 상태에서도 그려야 되니까 넣은 부분
        }

        public class FigureData {
            // 여러 도형들을 저장하기 위한 클래스
            int startX, startY, stopX, stopY, shape;
        }

        public void myDrawing(FigureData figure, Canvas canvas, Paint paint) {
            // 받아온 FigureData 인스턴스 내의 shape에 따라 그리는 결과가 다르도록 함
            switch (figure.shape) {
                case LINE:
                    canvas.drawLine(figure.startX, figure.startY, figure.stopX, figure.stopY, paint);
                    break;
                case CIRCLE:
                    int radius = (int) Math.sqrt(Math.pow(figure.stopX - figure.startX, 2)
                            + Math.pow(figure.stopY - figure.startY, 2));
                    canvas.drawCircle(figure.startX, figure.startY, radius, paint);
                    break;
                case RECT:
                    Rect rect = new Rect(figure.startX, figure.startY, figure.stopX, figure.stopY);
                    canvas.drawRect(rect,paint);
                    break;
            }
        }
    }
}