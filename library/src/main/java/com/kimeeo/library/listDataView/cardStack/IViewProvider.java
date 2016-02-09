package com.kimeeo.library.listDataView.cardStack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bhavinpadhiyar on 1/21/16.
 */
public interface IViewProvider {
    View getItemView(int index,Object data, LayoutInflater inflater, ViewGroup container);

}

