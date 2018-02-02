package jtli.com.dogbreeds.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.WindowManager;

public class ColorFilterToolBar extends Toolbar {

    public static final int STRAT_BLUE= 0xFF673bb7;
    public static final int END_BLUE= 0xFFa987e7;//  906ad4

    private Paint mPaint;
    private float windowWidth;
    private int height;

    public ColorFilterToolBar(Context context) {
        this(context, null);
    }

    public ColorFilterToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ColorFilterToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();//obtain width of the screen
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 1; i <= windowWidth; i++) {
            // set painting color
            mPaint.setColor((Integer) evaluateColor(Math.pow(i/ windowWidth,2),STRAT_BLUE,END_BLUE));
            canvas.drawRect(i-1, 0, i, height,mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    /**
     * color transition
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Object evaluateColor(double fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24 |
                (startR + (int) (fraction * (endR - startR))) << 16 |
                (startG + (int) (fraction * (endG - startG))) << 8 |
                (startB + (int) (fraction * (endB - startB)));
    }
}
