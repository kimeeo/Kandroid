package com.kimeeo.kandroid.sample.lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.kandroid.sample.projectCore.DefaultVerticalListView;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.volokh.danylo.layoutmanager.LondonEyeLayoutManager;
import com.volokh.danylo.layoutmanager.scroller.IScrollHandler;

import butterknife.Bind;

/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
public class EasyCircleListView extends DefaultVerticalListView
{
    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        int screenWidth = getActivity().getResources().getDisplayMetrics().widthPixels;

        // define circle radius
        int circleRadius = screenWidth;

        // define center of the circle
        int xOrigin = -200;
        int yOrigin = 0;


        // set radius and center
        //getRecyclerView().setParameters(circleRadius, xOrigin, yOrigin);

        LondonEyeLayoutManager mLondonEyeLayoutManager = new LondonEyeLayoutManager(
                circleRadius,
                xOrigin,
                yOrigin,
                getRecyclerView(),IScrollHandler.Strategy.NATURAL);

        return mLondonEyeLayoutManager;
    }

    //Return View Type here
    @Override
    public int getListItemViewType(int position,Object item)
    {
        if(position<4)
            return ViewTypes.VIEW_ITEM1;
        else
            return ViewTypes.VIEW_ITEM2;
    }
    // get View
    @Override
    public View getItemView(int viewType,LayoutInflater inflater,ViewGroup container)
    {
        return inflater.inflate(R.layout._circle_column_cell,null);
    }
    //END URL
    public String getNextDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data"+pageData.curruntPage+".txt";
    }

    public String getRefreshDataURL(PageData pageData)
    {
        return "http://www.googledrive.com/host/0B0GMnwpS0IrNRkI5WFVCZG5EUTQ/data_m1.txt";
    }

    //Data Parser
    public Class getLoadedDataParsingAwareClass()
    {
        return SampleDataParser.class;
    }





    // get New BaseItemHolder

    public BaseItemHolder getItemHolder(int viewType,View view)
    {
        if(viewType== ViewTypes.VIEW_ITEM1)
            return new VlistItemHolder1(view);
        else
            return new VlistItemHolder2(view);
    }
    // Update View Here
    public class VlistItemHolder1 extends BaseItemHolder {

        @Bind(R.id.label)TextView label;
        @Bind(R.id.backgroud)ImageView image;
        //private ViewDataBinding binding;

        public VlistItemHolder1(View itemView)
        {
            super(itemView);
            //binding= DataBindingUtil.bind(itemView);

        }
        /*
        public ViewDataBinding getBinding()
        {
            return binding;
        }
*/
        public void updateItemView(Object item,View view,int position)
        {
            SampleModel listObject = (SampleModel)item;
            label.setText(position + " -> " + listObject.name);
            //AQuery aq = new AQuery(view);
            //aq.id(R.id.backgroud).image(listObject.image, true, true, 100, 0);


            //getBinding().setVariable(BR.myBook, listObject);
           // getBinding().executePendingBindings();

        }
    }



    // Update View Here
    public class VlistItemHolder2 extends BaseItemHolder{

        @Bind(R.id.label)TextView label;
        @Bind(R.id.backgroud)ImageView image;


        //private ViewDataBinding binding;

        public VlistItemHolder2(View itemView)
        {
            super(itemView);
            //binding= DataBindingUtil.bind(itemView);
        }
        /*
        public ViewDataBinding getBinding()
        {
            return binding;
        }*/

        public void updateItemView(Object item,View view,int position)
        {
            SampleModel listObject = (SampleModel)item;

            label.setText(position + " -> " + listObject.name);
            //AQuery aq = new AQuery(view);
            //aq.id(R.id.backgroud).image(listObject.image, true, true, 100, 0);

            //getBinding().setVariable(BR.myBook, listObject);
            //getBinding().executePendingBindings();
        }
    }





}
