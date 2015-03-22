package com.kasra.lifetracker.utils;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Why the hell doesn't RecyclerView support dividers natively?
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int mHeight;

    public DividerItemDecoration(int color, int height) {
        mDivider = new ColorDrawable(color);
        mHeight = height;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mHeight;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
