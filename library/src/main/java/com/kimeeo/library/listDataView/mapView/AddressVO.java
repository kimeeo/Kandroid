package com.kimeeo.library.listDataView.mapView;

import android.location.Address;

/**
 * Created by bhavinpadhiyar on 9/1/15.
 */
public class AddressVO {

    public AddressVO(Address a)
    {
        address = a.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        city = a.getLocality();
        state = a.getAdminArea();
        country = a.getCountryName();
        postalCode = a.getPostalCode();
        knownName = a.getFeatureName();
    }
    public String address;
    public String city;
    public String state;
    public String country;
    public String postalCode;
    public String knownName;
}
