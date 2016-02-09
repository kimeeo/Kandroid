package com.kimeeo.kandroid.sample.lists;


import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModelFrgament;
import com.kimeeo.library.fragments.BaseFragment;
import com.kimeeo.library.listDataView.cardStack.BaseCardStackAdapter;
import com.kimeeo.library.listDataView.cardStack.BaseCardStackFragmentAdapter;
import com.kimeeo.library.listDataView.cardStack.BaseCardstack;
import com.kimeeo.library.model.IFragmentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
public class CardStackViewLikeAirTel extends BaseCardstack
{
    protected int getViewRes(int index,Object data)
    {
        return R.layout._sample_column_cell_fragment;
    }
    private void update(View view, int index, Object data) {
        view.setBackgroundColor(getColorFor(index));
        TextView label=(TextView)view.findViewById(R.id.label);

        SampleModelFrgament listObject = (SampleModelFrgament)data;
        label.setText(index + " -> " + listObject.name);

    }

    private int getColorFor(int index) {
        if(index==0)
            return Color.parseColor("#F44336");
        else if(index==1)
            return Color.parseColor("#E91E63");
        else if(index==2)
            return Color.parseColor("#AB47BC");
        else if(index==3)
            return Color.parseColor("#03A9f4");
        else if(index==4)
            return Color.parseColor("#44A9f4");
        else if(index==5)
            return Color.parseColor("#EF49f4");
        else
            return Color.parseColor("#009688");
    }

    public List<?> getData()
    {
        List<SampleModelFrgament> list = new ArrayList<>();
        list.add(getSample("B1", "534534"));
        list.add(getSample("B2", "534534"));
        list.add(getSample("B3", "534534"));
        list.add(getSample("B4", "534534"));
        list.add(getSample("B3", "534534"));
        list.add(getSample("B4", "534534"));
        return list;
    }

    private SampleModelFrgament getSample(String name, String phone) {
        SampleModelFrgament o = new SampleModelFrgament();
        o.name =name;
        o.details = phone;
        return o;
    }

    protected BaseCardStackAdapter createListViewAdapter()
    {
        return new Adaptor(getActivity(),getActivity().getSupportFragmentManager());

        //return new Adaptor1(getActivity());
    }


    public class Adaptor1 extends BaseCardStackAdapter
    {
        public Adaptor1(FragmentActivity activity) {
            super(activity);
        }
        protected  List createList()
        {
            return getData();
        }
        protected void updateView(View view,int index,Object data)
        {
            update(view,index,data);
        }
        protected int getViewResID(int index,Object data)
        {
            return getViewRes(index, data);
        }
    }

    public class Adaptor extends BaseCardStackFragmentAdapter
    {
        public Adaptor(FragmentActivity activity,FragmentManager fragmentManager) {
            super(activity,fragmentManager);
        }
        protected  List createList()
        {
            return getData();
        }
        protected void updateView(View view,int index,Object data)
        {
            update(view,index,data);
        }

        protected int getViewResID(int index,Object data)
        {
            return getViewRes(index, data);
        }

        protected void loadItemFragment(FragmentManager fragmentManager,View root,int position,Object data)
        {
            if(data instanceof IFragmentData)
            {
                BaseFragment activePage = BaseFragment.newInstance((IFragmentData)data);
                if(activePage!=null)
                {
                    FrameLayout frameLayout =(FrameLayout)root.findViewById(R.id.holder);
                    int id =getId();
                    frameLayout.setId(id);
                    fragmentManager.beginTransaction().replace(id, activePage).commit();
                }
            }
        }
    }
}
