package com.kimeeo.library.listDataView.recyclerView.viewPager;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

/**
 * Created by bpa001 on 3/11/16.
 */
public class TabLayout extends android.support.design.widget.TabLayout {
    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setScrollPosition(int position,   float positionOffset,  boolean updateSelectedText) {
       try {
            if(position!=-1)
                super.setScrollPosition(position, positionOffset, updateSelectedText);
       }catch (Exception e){



       }
    }
}
