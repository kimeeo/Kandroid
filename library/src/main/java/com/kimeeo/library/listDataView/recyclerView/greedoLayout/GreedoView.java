package com.kimeeo.library.listDataView.recyclerView.greedoLayout;

import android.support.v7.widget.RecyclerView;

import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.DefaultRecyclerView;

/**
 * Created by bhavinpadhiyar on 7/17/15.
 */
abstract public class GreedoView extends DefaultRecyclerView {
    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        GreedoLayoutManager layoutManager = new GreedoLayoutManager(createSizeCalculatorDelegate(getAdapter()));
        int rowHeight = getMaxRowHeight();
        rowHeight = MeasUtils.dpToPx(rowHeight, getActivity());
        layoutManager.setMaxRowHeight(rowHeight);
        return layoutManager;
    }

    protected RecyclerView.ItemDecoration createItemDecoration() {
        int spacing = getSpacing();
        spacing = MeasUtils.dpToPx(spacing, getActivity());
        return new GreedoSpacingItemDecoration(spacing);
    }

    protected int getSpacing() {
        return 4;
    }

    protected int getMaxRowHeight() {
        return 130;
    }

    abstract protected GreedoLayoutSizeCalculator.SizeCalculatorDelegate createSizeCalculatorDelegate(BaseRecyclerViewAdapter adapter);

    /*
    private void calculateImageAspectRatios() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        for (int i = 0; i < mImageResIds.length; i++) {
            BitmapFactory.decodeResource(getActivity().getResources(), mImageResIds[i], options);
            mImageAspectRatios[i] = options.outWidth / (double) options.outHeight;
        }
    }
    */
}
