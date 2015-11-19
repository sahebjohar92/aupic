package com.aupic.aupic.Graphics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.aupic.aupic.Constant.StringConstants;

/**
 * Created by saheb on 4/11/15.
 */
public class SquareImageView extends ImageView {

    private float FadeStrength = 1.0f;
    private int FadeLength = StringConstants.FADE_LENGTH;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return FadeStrength;
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return FadeStrength;
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return FadeStrength;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return FadeStrength;
    }
}