package com.aupic.aupic.Graphics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by saheb on 4/11/15.
 */
public class SquareImageWithoutFade extends ImageView {

    public SquareImageWithoutFade(Context context) {
        super(context);
    }

    public SquareImageWithoutFade(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageWithoutFade(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
