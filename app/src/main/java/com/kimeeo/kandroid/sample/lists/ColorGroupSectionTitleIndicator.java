package com.kimeeo.kandroid.sample.lists;

import android.content.Context;
import android.util.AttributeSet;

import com.kimeeo.kandroid.sample.model.SampleModel;

import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;

/**
 * Created by bhavinpadhiyar on 1/19/16.
 */
public class ColorGroupSectionTitleIndicator extends SectionTitleIndicator<DefaultRecyclerIndexableViewAdapter.ABCGroup> {

    public ColorGroupSectionTitleIndicator(Context context) {
        super(context);
    }

    public ColorGroupSectionTitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorGroupSectionTitleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSection(DefaultRecyclerIndexableViewAdapter.ABCGroup colorGroup) {
        setTitleText( "A");
    }
}
