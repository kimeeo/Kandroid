package com.kimeeo.kandroid.sample.lists.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.listDataView.listViews.BaseItemHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bpa001 on 3/11/16.
 */
public class ListItemHolder1 extends BaseItemHolder {

    @Bind(R.id.label)TextView label;
    @Bind(R.id.backgroud)ImageView image;

    public ListItemHolder1(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
     }
    public void updateItemView(Object item,View view,int position)
    {
        SampleModel listObject = (SampleModel)item;
        label.setText(position + " -> " + listObject.name);
    }
}
