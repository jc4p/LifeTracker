package com.kasra.lifetracker.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.kasra.lifetracker.utils.RecyclerItemClickListener;

/**
 * Some simple extensions ontop of Recyclerview
 */
public class LTRecyclerView extends RecyclerView {
    public LTRecyclerView(Context context) {
        super(context);
    }

    public LTRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LTRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnItemClickListener(RecyclerItemClickListener.OnItemClickListener onItemClickListener) {
        addOnItemTouchListener(new RecyclerItemClickListener(getContext(), onItemClickListener));
    }
}
