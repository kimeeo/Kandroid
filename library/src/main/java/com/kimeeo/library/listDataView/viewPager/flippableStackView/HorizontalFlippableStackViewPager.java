package com.kimeeo.library.listDataView.viewPager.flippableStackView;

import com.bartoszlipinski.flippablestackview.StackPageTransformer;

/**
 * Created by bhavinpadhiyar on 1/22/16.
 */
abstract public class HorizontalFlippableStackViewPager extends DefaultFlippableStackViewPager
{
    public StackPageTransformer.Orientation getOrientation()
    {
        return StackPageTransformer.Orientation.HORIZONTAL;
    }
}
