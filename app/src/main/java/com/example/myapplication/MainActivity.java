package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //The informations on rectangle properties
    private TextView sizeView, lengthView, circumferenceView, areaView;
    //This View represente a rectangle
    private View rectangleView;
    //The background of my screen
    private int backgroundColor;
    //The touchable points
    private PointF touchPoint1, touchPoint2;
    //To verify if the rectangle is already draw
    private boolean isRectangleDrawn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.widgetsInit();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerCount = event.getPointerCount();
            if (pointerCount == 2) {
                isRectangleDrawn = false;
                touchPoint1 = new PointF(event.getX(0), event.getY(0));
                touchPoint2 = new PointF(event.getX(1), event.getY(1));
                drawRectangle();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void drawRectangle() {
        if (touchPoint1 != null && touchPoint2 != null) {
            //Determine the four corners of the rectangle
            float top = Math.min(touchPoint1.y, touchPoint2.y);
            float right = Math.max(touchPoint1.x, touchPoint2.x);
            float bottom = Math.max(touchPoint1.y, touchPoint2.y);
            float left = Math.min(touchPoint1.x, touchPoint2.x);

            //The width and height of rectangle
            float width = right - left;
            float height = bottom - top;
            //Calculation of size
            float size = width * height;
            //calculation of the percentage of the current rectangle compared to the old rectangle
            float screenArea = rectangleView.getWidth() * rectangleView.getHeight();
            float percentSize = size / screenArea * 100;

            //Draw a rectangle
            rectangleView.setVisibility(View.VISIBLE);
            rectangleView.setX(left);
            rectangleView.setY(top);
            rectangleView.getLayoutParams().width = (int) width;
            rectangleView.getLayoutParams().height = (int) height;
            rectangleView.requestLayout();

            this.sizeView.setText(String.format(Locale.getDefault(), "Size: %.2f%%", percentSize));
            lengthView.setText(String.format(Locale.getDefault(), "Length: %.2f cm", pxToCm(width)));
           // tvLength.setText(String.format(Locale.getDefault(), "Length: %.2f px, %.2f cm", width, pxToCm(width)));
            circumferenceView.setText(String.format(Locale.getDefault(), "Circumference: %.2f cm", pxToCm(2 * (width + height))));
            //tvCircumference.setText(String.format(Locale.getDefault(), "Circumference: %.2f px, %.2f cm", 2 * (width + height), pxToCm(2 * (width + height))));
            areaView.setText(String.format(Locale.getDefault(), "Area: %.2f cm²", pxToCm2(size)));
            //tvArea.setText(String.format(Locale.getDefault(), "Area: %.2f px², %.2f cm²", size, pxToCm2(size)));
        }

    }

    private float pxToCm(float px) {
        float dpi = getResources().getDisplayMetrics().densityDpi;
        return px * 2.54f / dpi;
    }

    private float pxToCm2(float px) {
        float dpi = getResources().getDisplayMetrics().densityDpi;
        return px * (2.54f / dpi) * (2.54f / dpi);
    }

    private void widgetsInit(){
        sizeView = findViewById(R.id.size_id);
        lengthView = findViewById(R.id.length_id);
        circumferenceView = findViewById(R.id.circumference_id);
        areaView = findViewById(R.id.area_id);
        rectangleView = findViewById(R.id.rectangleView);

        //Change background color
        backgroundColor = getApplicationContext().getColor(R.color.seagreen);
        findViewById(android.R.id.content).setBackgroundColor(backgroundColor);
    }
}