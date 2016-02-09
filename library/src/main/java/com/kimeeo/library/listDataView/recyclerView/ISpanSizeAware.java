/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kimeeo.library.listDataView.recyclerView;

/**
 * Created by bhavinpadhiyar on 8/4/15.
 */
public interface ISpanSizeAware {
    public static int FULL = 9999;
    public static int HALF = 9998;
    public static int QUARTER = 9997;
    public static int REGULAR = 1;
    int getSpanSize();
}
