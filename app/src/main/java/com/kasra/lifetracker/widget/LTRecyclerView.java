package com.kasra.lifetracker.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.kasra.lifetracker.R;
import com.kasra.lifetracker.utils.DividerItemDecoration;
import com.kasra.lifetracker.utils.RecyclerItemClickListener;

/**
 * Some simple extensions ontop of RecyclerView
 */
public class LTRecyclerView extends RecyclerView {
    public LTRecyclerView(Context context) {
        super(context, null, 0);
    }

    public LTRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LTRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        addItemDecoration(new DividerItemDecoration(context.getResources().getColor(R.color.divider),
                context.getResources().getDimensionPixelSize(R.dimen.divider_height)));
    }

    public void setOnItemClickListener(RecyclerItemClickListener.OnItemClickListener onItemClickListener) {
        addOnItemTouchListener(new RecyclerItemClickListener(getContext(), onItemClickListener));
    }
}
