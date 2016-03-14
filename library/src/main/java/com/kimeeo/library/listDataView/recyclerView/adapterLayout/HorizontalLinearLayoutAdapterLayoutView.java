package com.kimeeo.library.listDataView.recyclerView.adapterLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.R;

/**
 * Created by bhavinpadhiyar on 2/16/16.
 */
abstract public class HorizontalLinearLayoutAdapterLayoutView extends BaseLinearLayoutAdapterLayoutView {
    protected View createRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getDataManager().getRefreshEnabled())
            return inflater.inflate(R.layout._fragment_horizontal_linear_layout_adapter_view_with_swipe_refresh_layout, container, false);
        else
            return inflater.inflate(R.layout._fragment_horizontal_linear_layout_adapter_view, container, false);
    }
}
