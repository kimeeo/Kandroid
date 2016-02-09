package com.kimeeo.library.listDataView.mapView;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by bhavinpadhiyar on 2/3/16.
 */
public class MapUtils {

    public static Distance getDistance(Location source,Location dest){

        if(source!=null && dest!=null) {
            Location locationA = new Location("point A");
            locationA.setLatitude(dest.getLatitude());
            locationA.setLongitude(dest.getLongitude());


            Location locationB = new Location("point B");
            locationB.setLatitude(source.getLatitude());
            locationB.setLongitude(source.getLongitude());


            Distance distance = new Distance();
            distance.distanceMeters = locationA.distanceTo(locationB);
            distance.distanceKM = Math.round(locationA.distanceTo(locationB) / 1000);
            distance.distanceMiles = (distance.distanceMeters * 0.000621371192237334);


            if(distance.distanceMeters<=500)
            {
                distance.redableDistanceKM = Math.round(distance.distanceMeters) +" Meters";
                distance.redableDistanceMiles = Math.round(distance.distanceMeters) +" Meters";
            }
            else {
                distance.redableDistanceKM = Math.round(distance.distanceKM) +" KM";
                distance.redableDistanceMiles = Math.round(distance.distanceMiles) +" Miles";
            }


            return distance;

        }
        return null;
    }
    public static Distance getDistance(IPOI source,Location dest){

        if(source!=null && dest!=null) {
            Location locationA = new Location("point A");
            locationA.setLatitude(dest.getLatitude());
            locationA.setLongitude(dest.getLongitude());


            Location locationB = new Location("point B");
            locationB.setLatitude(source.getLatitude());
            locationB.setLongitude(source.getLongitude());


            Distance distance = new Distance();
            distance.distanceMeters = locationA.distanceTo(locationB);
            distance.distanceKM = Math.round(locationA.distanceTo(locationB) / 1000);
            distance.distanceMiles = (distance.distanceMeters * 0.000621371192237334);


            if(distance.distanceMeters<=500)
            {
                distance.redableDistanceKM = Math.round(distance.distanceMeters) +" Meters";
                distance.redableDistanceMiles = Math.round(distance.distanceMeters) +" Meters";
            }
            else {
                distance.redableDistanceKM = Math.round(distance.distanceKM) +" KM";
                distance.redableDistanceMiles = Math.round(distance.distanceMiles) +" Miles";
            }


            return distance;

        }
        return null;
    }
    public static Distance getDistance(Location source,IPOI dest){

        if(source!=null && dest!=null) {
            Location locationA = new Location("point A");
            locationA.setLatitude(dest.getLatitude());
            locationA.setLongitude(dest.getLongitude());


            Location locationB = new Location("point B");
            locationB.setLatitude(source.getLatitude());
            locationB.setLongitude(source.getLongitude());


            Distance distance = new Distance();
            distance.distanceMeters = locationA.distanceTo(locationB);
            distance.distanceKM = Math.round(locationA.distanceTo(locationB) / 1000);
            distance.distanceMiles = (distance.distanceMeters * 0.000621371192237334);


            if(distance.distanceMeters<=500)
            {
                distance.redableDistanceKM = Math.round(distance.distanceMeters) +" Meters";
                distance.redableDistanceMiles = Math.round(distance.distanceMeters) +" Meters";
            }
            else {
                distance.redableDistanceKM = Math.round(distance.distanceKM) +" KM";
                distance.redableDistanceMiles = Math.round(distance.distanceMiles) +" Miles";
            }


            return distance;

        }
        return null;
    }
    public static Distance getDistance(IPOI source,IPOI dest){
        try {
            if (source != null && dest != null) {
                Location locationA = new Location("point A");
                locationA.setLatitude(dest.getLatitude());
                locationA.setLongitude(dest.getLongitude());


                Location locationB = new Location("point B");
                locationB.setLatitude(source.getLatitude());
                locationB.setLongitude(source.getLongitude());
                Distance distance = new Distance();
                distance.distanceMeters = locationA.distanceTo(locationB);
                distance.distanceKM = Math.round(locationA.distanceTo(locationB) / 1000);
                distance.distanceMiles = (distance.distanceMeters * 0.000621371192237334);

                if(distance.distanceMeters<=500)
                {
                    distance.redableDistanceKM = Math.round(distance.distanceMeters) +" Meters";
                    distance.redableDistanceMiles = Math.round(distance.distanceMeters) +" Meters";
                }
                else {
                    distance.redableDistanceKM = Math.round(distance.distanceKM) +" KM";
                    distance.redableDistanceMiles = Math.round(distance.distanceMiles) +" Miles";
                }



                return distance;

            }
        }catch(Exception e){}
        return null;
    }

    public static List<AddressVO> getAddress(Activity activity,Location loc,int size)
    {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            List<AddressVO> addresses1 = new ArrayList<>();

            geocoder = new Geocoder(activity, Locale.getDefault());
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), size);
            AddressVO addressVO;
            for (Address address:addresses) {
                addresses1.add(new AddressVO(address));
            }
            return addresses1;
        }catch(Exception e)
        {

        }
        return null;
    }

    public static List<AddressVO> getAddress(Activity activity,IPOI poi,int size)
    {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            List<AddressVO> addresses1 = new ArrayList<>();

            geocoder = new Geocoder(activity, Locale.getDefault());
            addresses = geocoder.getFromLocation(poi.getLatitude(), poi.getLongitude(), size);
            AddressVO addressVO;
            for (Address address:addresses) {
                addresses1.add(new AddressVO(address));
            }
            return addresses1;



        }catch(Exception e)
        {

        }
        return null;
    }
}
