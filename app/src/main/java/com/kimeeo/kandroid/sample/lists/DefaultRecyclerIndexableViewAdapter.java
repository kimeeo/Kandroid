package com.kimeeo.kandroid.sample.lists;

import android.widget.SectionIndexer;

import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.listDataView.recyclerView.DefaultRecyclerView;
import com.kimeeo.library.listDataView.recyclerView.DefaultRecyclerViewAdapter;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

/**
 * Created by bhavinpadhiyar on 1/19/16.
 */
public class DefaultRecyclerIndexableViewAdapter extends DefaultRecyclerViewAdapter implements SectionIndexer, FastScrollRecyclerView.SectionedAdapter{
    public DefaultRecyclerIndexableViewAdapter(DataManager dataManager, DefaultRecyclerView abstractRecyclerView)
    {
        super(dataManager,abstractRecyclerView,abstractRecyclerView);
    }

    public Object[] getSections()
    {
        return ABCGroup.values();
    }
    public String getSectionName(int position)
    {
        return String.valueOf(position);
    }
    public int getPositionForSection(int sectionIndex)
    {
        return 0;
    }
    public int getSectionForPosition(int position)
    {
        if (position >= getDataManager().size()) {
            position = getDataManager().size() - 1;
        }
        if(getDataManager().get(position) instanceof SampleModel)
        {
            SampleModel color =(SampleModel) getDataManager().get(position);
            String c = color.name.substring(0,1).toUpperCase();
            if(c.equals("A"))
                return 0;
            else if(c.equals("B"))
                return 1;
            return 3;
        }
        return 1;
    }

    public enum ABCGroup {
        A ("A"),
        B ("B"),
        C ("C"),
        D ("D"),
        E ("E"),
        F ("F"),
        G ("G"),
        H ("H"),
        I ("I"),
        J ("J");



        private final String mName;

        ABCGroup(String groupName) {
            mName = groupName;

        }

        public String getName() {
            return mName;
        }
    }

}
