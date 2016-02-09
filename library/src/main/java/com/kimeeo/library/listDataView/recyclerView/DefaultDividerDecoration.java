package com.kimeeo.library.listDataView.recyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
public class DefaultDividerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = { android.R.attr.listDivider };
    private int mInsets;

    public DefaultDividerDecoration(Context context) {
        mInsets = 0;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
    }

    public void drawVertical(Canvas c, RecyclerView parent)
    {
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}
