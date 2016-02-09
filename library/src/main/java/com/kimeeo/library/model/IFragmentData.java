package com.kimeeo.library.model;

/**
 * Created by bhavinpadhiyar on 12/23/15.
 */
public interface IFragmentData {
    Class getView();
    String getID();
    String getName();
    Object getParam();
    void setParam(Object value);
    Object getActionValue();
    String getActionType();
}
