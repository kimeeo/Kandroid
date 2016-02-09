package com.kimeeo.library.listDataView.mapView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kimeeo.library.R;
import com.kimeeo.library.listDataView.BaseListDataView;
import com.kimeeo.library.listDataView.dataManagers.DataChangeWatcher;
import com.kimeeo.library.listDataView.dataManagers.DataManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 2/3/16.
 */
abstract public class BaseMapView extends BaseListDataView implements DataChangeWatcher
{
    public Object remove(int position) {
        return getDataManager().remove(position);
    }
    public boolean add(Object value)
    {
        return getDataManager().add(value);
    }
    public void  add(int position,Object value) {
        getDataManager().add(position,value);
    }
    public boolean addAll(Collection value) {
        return getDataManager().addAll(value);
    }
    public boolean addAll(int index, Collection collection) {
        return getDataManager().addAll(index, collection);
    }
    public void addAll(int index,Object[] values) {
        getDataManager().addAll(index, Arrays.asList(values));
    }

    public boolean removeAll(Collection value) {
        return getDataManager().removeAll(value);
    }

    protected SupportMapFragment mapFragment;
    protected GoogleMap googleMap;
    private Location myLocation;
    private View mProgressBar;

    public HashMap<String, Object> getMarkers() {
        return markerInfo;
    }

    public List<Marker> getMarkersList() {

        List<Marker> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry:markerInfo.entrySet()) {
            if(entry.getValue() instanceof IPOI)
            list.add(((IPOI) entry.getValue()).getMarker());
        }
        return list;
    }

    public void updateLatLng(Marker marker,LatLng latLng) {
        marker.setPosition(latLng);
        marker.notifyAll();
    }
    public void updateLatLng(Marker marker,Double latitude,Double longitude) {
        LatLng latLng = new LatLng(latitude,longitude);
        marker.setPosition(latLng);
        marker.notifyAll();
    }

    protected HashMap<String, Object> markerInfo = new HashMap<String, Object>();


    public static int VIEW_TYPE = GoogleMap.MAP_TYPE_NORMAL;
    private List<?> updatePending;

    protected void configDataManager(DataManager dataManager) {
        dataManager.setOnCallService(this);
        dataManager.setDataChangeWatcher(this);
    }

    protected void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        if(googleMap!=null)
        {
            googleMap.setOnMarkerClickListener(null);
            googleMap.setOnInfoWindowClickListener(null);
            googleMap.setOnMyLocationChangeListener(null);
            googleMap.setOnMarkerDragListener(null);

        }
        clearOnGlobalLayoutListener();
        mapFragment= null;
        googleMap = null;
        myLocation =null;
        updatePending=null;
        markerInfo=null;
    }


    public void drawLine(IPOI src,IPOI dest,int color)
    {
        LatLng latLng1 = new LatLng(src.getLatitude(),src.getLongitude());
        LatLng latLng2 = new LatLng(dest.getLatitude(),dest.getLongitude());
        getGoogleMap().addPolyline((new PolylineOptions()).add(latLng1).add(latLng2).width(2).color(color).geodesic(true));
    }
    public void drawLine(LatLng src,LatLng dest,int color)
    {
        getGoogleMap().addPolyline((new PolylineOptions()).add(src).add(dest).width(2).color(color).geodesic(true));
    }
    public void drawLine(Location src,Location dest,int color)
    {
        LatLng latLng1 = new LatLng(src.getLatitude(),src.getLongitude());
        LatLng latLng2 = new LatLng(dest.getLatitude(),dest.getLongitude());
        getGoogleMap().addPolyline((new PolylineOptions()).add(latLng1).add(latLng2).width(2).color(color).geodesic(true));
    }
    public void drawLine(IPOI src,Location dest,int color)
    {
        LatLng latLng1 = new LatLng(src.getLatitude(),src.getLongitude());
        LatLng latLng2 = new LatLng(dest.getLatitude(),dest.getLongitude());
        getGoogleMap().addPolyline((new PolylineOptions()).add(latLng1).add(latLng2).width(2).color(color).geodesic(true));
    }
    public void drawLine(Location src,IPOI dest,int color)
    {
        LatLng latLng1 = new LatLng(src.getLatitude(),src.getLongitude());
        LatLng latLng2 = new LatLng(dest.getLatitude(),dest.getLongitude());
        getGoogleMap().addPolyline((new PolylineOptions()).add(latLng1).add(latLng2).width(2).color(color).geodesic(true));
    }


    public GoogleMap getGoogleMap()
    {
        return googleMap;
    }
    public boolean showMenu()
    {
        return true;
    };

    protected View createRootView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout._fregment_map_fragment, container, false);
        return rootView;
    }

    protected SupportMapFragment getSupportMapFragment(View rootView,FragmentManager fragmentManager)
    {
        return (SupportMapFragment)fragmentManager.findFragmentById(R.id.mapFragment);
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        configViewParam();
        View rootView = createRootView(inflater, container, savedInstanceState);
        FragmentManager fragmentManager = getChildFragmentManager();
        mapFragment = getSupportMapFragment(rootView,fragmentManager);
        googleMap = mapFragment.getMap();
        try {
            MapsInitializer.initialize(getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        setHasOptionsMenu(showMenu());
        viewCreated(rootView);

        if(rootView.findViewById(R.id.progressBar)!=null)
            mProgressBar= rootView.findViewById(R.id.progressBar);

        configMapView(googleMap,mapFragment,getDataManager());
        loadNext();
        return rootView;
    }

    protected Drawable getEmptyViewDrawable()
    {
        Drawable drawable =getResources().getDrawable(R.drawable._empty_box);
        drawable.setColorFilter(getResources().getColor(R.color._emptyViewMessageColor), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }
    protected String getEmptyViewMessage()
    {
        return getResources().getString(R.string._emptyViewMessage);
    }
    protected void configMapView(GoogleMap googleMap,SupportMapFragment mapFragment,DataManager dataManager)
    {

    }
    protected void viewCreated(View rootView)
    {

    }

    public void navigateTo(IPOI poi)
    {
        String locationURL = "http://maps.google.com/maps?saddr="+myLocation.getLatitude()+","+myLocation.getLongitude()+"&daddr="+poi.getLatitude()+","+poi.getLongitude()+"";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationURL));
        startActivity(intent);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu_options, menu);
        setMenuIcons(menu, inflater);


        MenuItem item=null;
        if (VIEW_TYPE == GoogleMap.MAP_TYPE_NORMAL)
            item=menu.findItem(R.id.normal);
        else if (VIEW_TYPE == GoogleMap.MAP_TYPE_SATELLITE)
            item=menu.findItem(R.id.satellite);
        else if (VIEW_TYPE == GoogleMap.MAP_TYPE_TERRAIN)
            item=menu.findItem(R.id.terrain);
        else if (VIEW_TYPE == GoogleMap.MAP_TYPE_HYBRID)
            item=menu.findItem(R.id.hybrid);
        if(item!=null)
            item.setChecked(true);

        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);

        if (item.getItemId() == R.id.normal) {
            VIEW_TYPE =GoogleMap.MAP_TYPE_NORMAL;
            updateMapView(VIEW_TYPE);
            return true;
        }
        else if (item.getItemId() == R.id.satellite) {
            VIEW_TYPE =GoogleMap.MAP_TYPE_SATELLITE;
            updateMapView(VIEW_TYPE);
            return true;
        }
        else if (item.getItemId() == R.id.terrain) {
            VIEW_TYPE =GoogleMap.MAP_TYPE_TERRAIN;
            updateMapView(VIEW_TYPE);

            return true;
        }
        else if (item.getItemId() == R.id.hybrid) {
            VIEW_TYPE =GoogleMap.MAP_TYPE_HYBRID;
            updateMapView(VIEW_TYPE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setMenuIcons(Menu menu, MenuInflater inflater) {

    }
    public void updateMapView(int type) {
        if(googleMap!=null)
            googleMap.setMapType(type);
    }
    @Override
    public void onResume() {
        super.onResume();

        if (googleMap != null)
            setUpMap(googleMap);
    }
    protected void setUpMap(GoogleMap googleMap)
    {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);

        googleMap.setOnMarkerClickListener(onMarkerClickListener);
        googleMap.setOnInfoWindowClickListener(onInfoWindowClickListener);
        googleMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
        googleMap.setOnMarkerDragListener(onMarkerDragListener);

        updateMapView(VIEW_TYPE);

        if(updatePending!=null)
        {
            itemsAdded(updatePending);
            updatePending=null;
        }

    }
    GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener()
    {
        @Override
        public boolean onMarkerClick(final Marker marker) {
            return onMarkerTouch(markerInfo.get(marker.getId()));
        }
    };
    GoogleMap.OnInfoWindowClickListener onInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener()
    {
        @Override
        public void onInfoWindowClick(Marker marker)
        {
            Object data = markerInfo.get(marker.getId());
            onInfoWindowTouch(data);
        }
    };
    GoogleMap.OnMarkerDragListener onMarkerDragListener = new GoogleMap.OnMarkerDragListener()
    {
        @Override
        public void onMarkerDragStart(Marker marker)
        {

        }

        @Override
        public void onMarkerDragEnd(Marker marker) {

        }

        @Override
        public void onMarkerDrag(Marker marker)
        {

        }
    };
    GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener = new GoogleMap.OnMyLocationChangeListener()
    {
        @Override
        public void onMyLocationChange(Location location)
        {
            if(googleMap!=null) {
                myLocation = googleMap.getMyLocation();
                myLocationChange(myLocation);
                List<AddressVO> list=MapUtils.getAddress(getActivity(),myLocation,2);
                System.out.println(list);
            }
        }
    };
    public void myLocationChange(Location location)
    {

    }

    public Distance getDistanceFromMyLocation(Location loc){
        return MapUtils.getDistance(myLocation,loc);
    }
    public Distance getDistanceFromMyLocation(IPOI loc){
        return MapUtils.getDistance(myLocation,loc);
    }

    public void moveCameraToLocation( IPOI newPOI) {
        if(newPOI!=null && googleMap!=null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(newPOI.getLatitude(), newPOI.getLongitude())).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
    public void moveCameraToLocation( Location location) {
        if(location !=null && googleMap!=null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
    public void moveCameraToMyLocation() {
        Location location = myLocation;
        if(location !=null && googleMap!=null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    public void onInfoWindowTouch(Object data) {

    }
    public boolean onMarkerTouch(Object data)
    {
        return false;
    };

    public void fitMapToPins() {
        try
        {
            if (mapFragment.getView().getViewTreeObserver().isAlive()) {
                mapFragment.getView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
            }
        }catch(Exception e){}

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    protected void removePOIMarker(IPOI poi)
    {
        if(googleMap!=null) {
            poi.getMarker().remove();
        }
    }

    protected void addPOIMarker(IPOI poi)
    {
        try
        {
            if(googleMap!=null) {
                MarkerOptions markerOptions = getMarkerOptions(poi);
                if (markerOptions != null) {
                    Bitmap iconBitmap = getPOIIcon(poi);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(iconBitmap);
                    markerOptions.icon(bitmapDescriptor);
                    markerOptions.snippet(poi.getSnippet());
                    markerOptions.title(poi.getTitle());
                    Marker marker = googleMap.addMarker(markerOptions);
                    marker.setSnippet(poi.getSnippet());
                    poi.setMarker(marker);

                    markerInfo.put(marker.getId(), poi);
                }
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }


    }
    protected Bitmap getPOIIcon(IPOI poi)
    {
        Drawable layerDrawable=getActivity().getResources().getDrawable(R.drawable._pin);
        Bitmap icon=drawableToBitmap(layerDrawable);
        return icon;
    }


    public Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    protected IPOI getPOIForObject(Object data)
    {
        return null;
    }
    protected MarkerOptions getMarkerOptions(IPOI poi)
    {
        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(poi.getLatitude(), poi.getLongitude())).title(poi.getTitle()).snippet(poi.getSnippet());
        return markerOptions;
    }

    public void onDataReceived(String url,Object value,Object status)
    {

    }
    public void onDataLoadError(String url, Object status)
    {

    }
    private boolean firstItemIn = false;
    public void onCallStart()
    {
        if(mProgressBar!=null && firstItemIn==false)
            mProgressBar.setVisibility(View.VISIBLE);
    }
    public void onCallEnd(List<?> dataList,boolean isRefreshPage)
    {

    }
    public void onLastCallEnd()
    {

    }
    public void onFirstCallEnd()
    {

    }






    public void clearOnGlobalLayoutListener() {
        if(mapFragment!=null && mapFragment.getView()!=null)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                mapFragment.getView().getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
            else
                mapFragment.getView().getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }
    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @SuppressWarnings("deprecation")
        @SuppressLint("NewApi")
        @Override
        public void onGlobalLayout() {
            try {
                LatLngBounds.Builder bc = new LatLngBounds.Builder();
                List<Object> dataList = getDataManager();
                IPOI poi;
                for (Object item : dataList) {
                    if (item instanceof IPOI) {
                        poi = (IPOI) item;
                        bc.include(poi.getMarker().getPosition());
                    }
                    else
                    {
                        poi= getPOIForObject(item);
                        if(poi!=null)
                            bc.include(poi.getMarker().getPosition());
                    }
                }
                if (dataList.size() > 0) {
                    LatLngBounds bounds = bc.build();
                    clearOnGlobalLayoutListener();
                    if(googleMap!=null)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                }
            }catch(Exception e){}
        }
    };

    public void itemsAdded(List dataList)
    {
        if(googleMap==null)
        {
            updatePending=dataList;
        }
        else
        {
            for (Object item : dataList)
            {
                if(item instanceof IPOI)
                    addPOIMarker((IPOI)item);
                else
                {
                    IPOI poi =getPOIForObject(item);
                    if(poi!=null)
                        addPOIMarker(poi);
                }
            }
            fitMapToPins();
        }

        if(mProgressBar!=null)
            mProgressBar.setVisibility(View.GONE);

        firstItemIn = true;
    }
    public void removedItem(Object item)
    {

    }
    public void itemsRemoved(List dataList)
    {
        if(googleMap!=null) {
            for (Object item : dataList)
            {
                if(item instanceof IPOI)
                    removePOIMarker((IPOI) item);
                else
                    removedItem(item);
            }
            fitMapToPins();
        }
        if(mProgressBar!=null)
            mProgressBar.setVisibility(View.GONE);
    }
}
