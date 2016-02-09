package com.kimeeo.kandroid.sample.lists;

import android.os.Handler;

import com.kimeeo.kandroid.sample.model.SamplePOIModel;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.mapView.BaseMapView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 2/3/16.
 */
public class MapView extends BaseMapView {

    // Data Manager
    protected DataManager createDataManager()
    {
        final ListDataManager listData1= new ListDataManager(getActivity(),listData);
        listData1.setRefreshEnabled(false);

        final Handler handler = new Handler();
        final Runnable runnablelocal = new Runnable() {
            @Override
            public void run() {
                listData1.remove(0);
            }
        };
        handler.postDelayed(runnablelocal, 15000);
        return listData1;
    }

    IListProvider listData=new IListProvider()
    {
        public List<?> getList(PageData data,Map<String, Object> param)
        {
            if(data.curruntPage==1) {
                List<SamplePOIModel> list = new ArrayList<>();
                list.add(getSample("B1", "534534",23.012916023,72.524510072));
                list.add(getSample("B1", "534534", 24.012916023, 72.524510072));

                //list.add(getSample("B1", "534534",24.012916023,72.524510072));
                //list.add(getSample("B1", "534534",25.012916023,74.524510072));
                //list.add(getSample("B1", "534534",22.012916023,73.524300072));
                //list.add(getSample("B1", "534534",25.012916023,72.524400072));
                //list.add(getSample("B1", "534534",26.012916023,72.524300072));
                //list.add(getSample("B1", "534534",13.012916023,72.543510072));
                //list.add(getSample("B1", "534534",23.012916023,42.524510072));
                return list;
            }


            return null;
        }
    };
    private SamplePOIModel getSample(String name, String phone,Double latitude,Double longitude) {
        SamplePOIModel o = new SamplePOIModel();
        o.name =name;
        o.details = phone;
        o.latitude=latitude;
        o.longitude=longitude;
        return o;
    }
}
