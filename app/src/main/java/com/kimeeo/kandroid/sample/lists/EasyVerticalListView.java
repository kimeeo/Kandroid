package com.kimeeo.kandroid.sample.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.activities.BaseActivity;
import com.kimeeo.kandroid.sample.activities.CoordinatorLayoutExample;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder1;
import com.kimeeo.kandroid.sample.lists.holder.RecyncleItemHolder2;
import com.kimeeo.kandroid.sample.projectCore.DefaultVerticalListView;
import com.kimeeo.library.actions.Action;
import com.kimeeo.library.actions.ImageSet;
import com.kimeeo.library.actions.ImageShare;
import com.kimeeo.library.actions.SMS;
import com.kimeeo.library.actions.SelectImage;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;

/**
 * Created by bhavinpadhiyar on 12/26/15.
 */
public class EasyVerticalListView extends DefaultVerticalListView
{
    Action action;

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        super.onItemClick(parent,view,position,id);

        if(action==null)
            action = new Action(getActivity());

        if(position==0)
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
        else if(position==1)
        {
            action.launchActivity(CoordinatorLayoutExample.class);
        }
        else if(position==2)
        {
            Action action1 =new Action(getActivity());
            action1.openChromeTab("http://www.facebook.com");
        } else if (position == 3) {
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
        else if (position == 4) {
            new SMS(getActivity()).perform("8469492621","Hello",true);
        }
        else if (position == 5) {
            new SelectImage(getActivity(),(BaseActivity)getActivity()).perform();
        }

    }

    public void onItemClick(Object baseObject)
    {
        super.onItemClick(baseObject);
        //Toast.makeText(getActivity(), baseObject.toString(), Toast.LENGTH_SHORT).show();
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
    @Override
    public BaseItemHolder getItemHolder(int viewType,View view)
    {
        if(viewType== ViewTypes.VIEW_ITEM1)
            return new RecyncleItemHolder1(view);
        else
            return new RecyncleItemHolder2(view);
    }

    public static class ViewTypes {
        public static final int VIEW_ITEM1 = 5;
        public static final int VIEW_ITEM2 = 10;
    }




}
