package com.yx.play.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.SizeUtils;
import com.yx.play.R;

public class LoadingView extends View {

    private int mSize;
    private int mPaintColor;
    private int mAnimateValue = 0;
    private ValueAnimator mAnimator;
    private Paint mPaint;
    private static final int LINE_COUNT = 12;
    private static final int DEGREE_PER_LINE = 360 / LINE_COUNT;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr, 0);
        mSize = array.getDimensionPixelSize(R.styleable.LoadingView_loading_view_size, SizeUtils.dp2px(32));
        mPaintColor = array.getInt(R.styleable.LoadingView_android_color, Color.GRAY);
        array.recycle();
        initPaint();

    }

    public LoadingView(Context context, int size, int color) {
        super(context);
        mSize = size;
        mPaintColor = color;
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mPaintColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setColor(int color) {
        mPaintColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    public void setSize(int size) {
        mSize = size;
        requestLayout();
    }

    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mAnimateValue = (int) animation.getAnimatedValue();
            invalidate();
        }
    };

    public void start() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1);
            mAnimator.addUpdateListener(mUpdateListener);
            mAnimator.setDuration(600);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.start();
        } else if (!mAnimator.isStarted()) {
            mAnimator.start();
        }
    }

    public void stop() {
        if (mAnimator != null) {
            mAnimator.removeUpdateListener(mUpdateListener);
            mAnimator.removeAllUpdateListeners();
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    private void drawLoading(Canvas canvas, int rotateDegrees) {
        int width = mSize / 12, height = mSize / 6;
        mPaint.setStrokeWidth(width);
//        canvas.rotate(rotateDegrees, mSize >> 1, mSize >> 1);
        canvas.translate(mSize >> 1, mSize >> 1);

        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.rotate(DEGREE_PER_LINE);
            int position;
            if ((i - mAnimateValue) < 0) {
                position = (i - mAnimateValue) + LINE_COUNT;
            } else {
                position = i - mAnimateValue;
            }
            mPaint.setAlpha((int) (255f * position / LINE_COUNT));
            canvas.translate(0, (-mSize >> 1) + (width >> 1));
            canvas.drawLine(0, 0, 0, height, mPaint);
            canvas.translate(0, (mSize >> 1) - (width >> 1));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        drawLoading(canvas, mAnimateValue * DEGREE_PER_LINE);
        canvas.restoreToCount(saveCount);
//        mPaint.setColor(Color.parseColor("#50ff0000"));
//        canvas.drawRect(0F, 0F, getWidth() * 1.0F, getHeight() * 1.0F, mPaint);
//        mPaint.setColor(Color.parseColor("#5000ff00"));
//        canvas.drawRect((mSize >> 1) + 1F, (mSize >> 1) + 1F, (mSize >> 1) - 1F, (mSize >> 1) - 1F, mPaint);
//        mPaint.setColor(mPaintColor);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }
}