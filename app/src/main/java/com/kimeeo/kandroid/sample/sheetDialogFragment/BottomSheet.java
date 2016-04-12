package com.kimeeo.kandroid.sample.sheetDialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.kandroid.R;
import com.kimeeo.library.fragments.BaseFragment;

/**
 * Created by BhavinPadhiyar on 12/04/16.
 */
public class BottomSheet extends BaseFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.coordinator_layout,null);
    }
    @Override
    protected void garbageCollectorCall() {

    }
}
