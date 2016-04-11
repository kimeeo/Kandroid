package com.kimeeo.kandroid.sample.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.activities.BaseActivity;
import com.kimeeo.kandroid.sample.activities.CoordinatorLayoutExample;
import com.kimeeo.kandroid.sample.activities.ImageUploader;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder2;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.actions.Action;
import com.kimeeo.library.actions.ImageSet;
import com.kimeeo.library.actions.SMS;
import com.kimeeo.library.actions.SelectImage;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.verticalViews.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
public class ActionTester extends ListView
{

    Action action;
    public void onItemClick(Object baseObject)
    {
        super.onItemClick(baseObject);
        if(action==null)
            action = new Action(getActivity());

        if(baseObject instanceof  SampleModel) {
            SampleModel data = (SampleModel) baseObject;
            if(data.name.equals(ACTION_DOWNLOAD_AND_OPEN))
            {
                String link="http://kmmc.in/wp-content/uploads/2014/01/lesson2.pdf";
                //String location="/aquery/abc";
                String location=null;
                String title="download Test";
                boolean aTrue=true;
                String success="Done";
                String fail="Failed";
                action.downloadAndOpen(link, location, "Open File With", true, success, fail, null);
            }
            else if(data.name.equals(ACTION_LAUNCH_ACTIVITY))
            {
                action.launchActivity(CoordinatorLayoutExample.class);
            }
            else if(data.name.equals(ACTION_LAUNCH_ACTIVITY_UPLOADER))
            {
                action.launchActivity(ImageUploader.class);
            }
            else if(data.name.equals(ACTION_OPEN_CHROME_TAB))
            {
                Action action1 =new Action(getActivity());
                action1.openChromeTab("http://www.facebook.com");
            }
            else if(data.name.equals(ACTION_IMAGE_SET))
            {
                String link = "https://www.planwallpaper.com/static/images/nature_backgrounds_perfect_version_images_7035.jpg";
                //String location="/aquery/abc";
                String location = null;
                String title = "download Test";
                boolean aTrue = true;
                String success = "Done";
                String fail = "Failed";
                //action.downloadAndOpen(link, location, "Open File With", true, success, fail, null);

                new ImageSet(getActivity()).perform(link, location, title, aTrue, success, fail, null);
                //new ImageShare(getActivity()).perform(link, location, title, "Hello Data <a hreh=http://g.com>click</a>", aTrue, success, fail, null);
            }
            else if(data.name.equals(ACTION_SMS))
            {
                new SMS(getActivity()).perform("8469492621","Hello",true);
            }
            else if(data.name.equals(ACTION_SELECT_IMAGE))
            {
                new SelectImage(getActivity(),(BaseActivity)getActivity()).perform();
            }

        }
        //Toast.makeText(getActivity(), baseObject.toString(), Toast.LENGTH_SHORT).show();
    }

    // Data Manager
    protected DataManager createDataManager()
    {
        ListDataManager listData1= new ListDataManager(getActivity(),listData);
        listData1.setRefreshEnabled(false);
        return listData1;
    }

    private static final String ACTION_DOWNLOAD_AND_OPEN="downloadAndOpen";
    private static final String ACTION_LAUNCH_ACTIVITY="launchActivity";
    private static final String ACTION_LAUNCH_ACTIVITY_UPLOADER="launchActivityUploader";
    private static final String ACTION_OPEN_CHROME_TAB="openChromeTab";
    private static final String ACTION_IMAGE_SET="ImageSet";
    private static final String ACTION_SMS="SMS";
    private static final String ACTION_SELECT_IMAGE="SelectImage";


    IListProvider listData=new IListProvider()
    {
        public List<?> getList(PageData data,Map<String, Object> param)
        {
            if(data.curruntPage==1) {
                List<SampleModel> list = new ArrayList<>();
                list.add(getSample(ACTION_DOWNLOAD_AND_OPEN,""));
                list.add(getSample(ACTION_LAUNCH_ACTIVITY, ""));
                list.add(getSample(ACTION_OPEN_CHROME_TAB, ""));
                list.add(getSample(ACTION_IMAGE_SET, ""));
                list.add(getSample(ACTION_SMS, ""));
                list.add(getSample(ACTION_SELECT_IMAGE, ""));
                list.add(getSample(ACTION_LAUNCH_ACTIVITY_UPLOADER, ""));
                return list;
            }
            return null;
        }
    };
    private SampleModel getSample(String name, String phone) {
        SampleModel o = new SampleModel();
        o.name =name;
        o.details = phone;
        return o;
    }


    protected BaseRecyclerViewAdapter createListViewAdapter()
    {
        return new DefaultRecyclerIndexableViewAdapter(getDataManager(),this);
    }

    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
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
        return inflater.inflate(R.layout._sample_column_cell,null);
    }



    // get New BaseItemHolder
    @Override
    public BaseItemHolder getItemHolder(int viewType,View view) {
        if (viewType == ViewTypes.VIEW_ITEM1)
            return new RecyncleItemHolder1(view);
        else
            return new RecyncleItemHolder2(view);
    }
}
