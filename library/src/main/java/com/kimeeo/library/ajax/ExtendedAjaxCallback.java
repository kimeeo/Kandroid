package com.kimeeo.library.ajax;

import com.androidquery.callback.AjaxCallback;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 9/7/15.
 */
public class ExtendedAjaxCallback<T> extends AjaxCallback<T> {

    private WeakReference<Map<String, Object>> params;

    public Map<String, Object> getParams() {
        return params.get();
    }
    public void setParams(Map<String, Object> value) {
        if(value!=null)
            params = new WeakReference(value);
    }
    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    Class clazz;

}
