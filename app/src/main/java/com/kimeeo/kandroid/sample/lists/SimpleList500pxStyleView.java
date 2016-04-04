package com.kimeeo.kandroid.sample.lists;

import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.greedoLayout.GreedoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
public class SimpleList500pxStyleView extends GreedoView {
    BitmapFactory.Options options = new BitmapFactory.Options();
    IListProvider listData = new IListProvider() {
        public List<?> getList(PageData data, Map<String, Object> param) {
            if (data.curruntPage == 1) {
                List<SampleModel> list = new ArrayList<>();
                list.add(getSample("B1", "534534", R.drawable.photo_1));
                list.add(getSample("B2", "534534", R.drawable.photo_2));
                list.add(getSample("B3", "534534", R.drawable.photo_3));
                list.add(getSample("B4", "534534", R.drawable.photo_4));
                list.add(getSample("B5", "534534", R.drawable.photo_5));
                list.add(getSample("B6", "534534", R.drawable.photo_6));
                list.add(getSample("B7", "534534", R.drawable.photo_7));
                list.add(getSample("B8", "534534", R.drawable.photo_8));
                list.add(getSample("B9", "534534", R.drawable.photo_9));
                list.add(getSample("B1", "534534", R.drawable.photo_1));
                list.add(getSample("B2", "534534", R.drawable.photo_2));
                list.add(getSample("B3", "534534", R.drawable.photo_3));
                list.add(getSample("B4", "534534", R.drawable.photo_4));
                list.add(getSample("B5", "534534", R.drawable.photo_5));
                list.add(getSample("B6", "534534", R.drawable.photo_6));
                list.add(getSample("B7", "534534", R.drawable.photo_7));
                list.add(getSample("B8", "534534", R.drawable.photo_8));
                list.add(getSample("B9", "534534", R.drawable.photo_9));
                list.add(getSample("B1", "534534", R.drawable.photo_1));
                list.add(getSample("B2", "534534", R.drawable.photo_2));
                list.add(getSample("B3", "534534", R.drawable.photo_3));
                list.add(getSample("B4", "534534", R.drawable.photo_4));
                list.add(getSample("B5", "534534", R.drawable.photo_5));
                list.add(getSample("B6", "534534", R.drawable.photo_6));
                list.add(getSample("B7", "534534", R.drawable.photo_7));
                list.add(getSample("B8", "534534", R.drawable.photo_8));
                list.add(getSample("B9", "534534", R.drawable.photo_9));
                return list;
            }
            return null;
        }
    };

    protected double getAspectRatioFor(int index, Object o) {
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getActivity().getResources(), ((SampleModel) o).resID, options);
        return options.outWidth / (double) options.outHeight;
    }

    // Data Manager
    protected DataManager createDataManager() {
        ListDataManager listData1 = new ListDataManager(getActivity(), listData);
        listData1.setRefreshEnabled(false);
        return listData1;
    }

    private SampleModel getSample(String name, String phone, @DrawableRes int resID) {
        SampleModel o = new SampleModel();
        o.name = name;
        o.details = phone;
        o.resID = resID;
        return o;
    }


    protected BaseRecyclerViewAdapter createListViewAdapter() {
        return new DefaultRecyclerIndexableViewAdapter(getDataManager(), this);
    }

    //Return View Type here
    @Override
    public int getListItemViewType(int position, Object item) {
        if (position < 4)
            return ViewTypes.VIEW_ITEM1;
        else
            return ViewTypes.VIEW_ITEM2;
    }

    // get View
    @Override
    public View getItemView(int viewType, LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout._sample_500px_column_cell, null);
    }

    protected int getSpacing() {
        return 0;
    }

    // get New BaseItemHolder
    @Override
    public BaseItemHolder getItemHolder(int viewType, View view) {
        if (viewType == ViewTypes.VIEW_ITEM1)
            return new RecyncleItemHolder1(view);
        else
            return new RecyncleItemHolder1(view);
    }

    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }

    public class RecyncleItemHolder1 extends BaseItemHolder {

        @Bind(R.id.label)
        TextView label;
        @Bind(R.id.backgroud)
        ImageView image;

        public RecyncleItemHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void updateItemView(Object item, View view, int position) {
            SampleModel listObject = (SampleModel) item;
            label.setText(position + " -> " + listObject.name);
            image.setImageResource(listObject.resID);
        }
    }
}
