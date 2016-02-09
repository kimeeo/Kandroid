package com.kimeeo.library.listDataView.mapView;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by bhavinpadhiyar on 8/18/15.
 */
public interface IPOI {
    Double getLatitude();
    Double getLongitude();
    Double getAltitude();
    String getTitle();
    String getSnippet();
    void setSnippet(String val);
    String getId();

    Marker getMarker();
    void setMarker(Marker marker);
}
