package com.kimeeo.kandroid.sample.retrofitDataManger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimeeo.kandroid.R;
import com.kimeeo.kandroid.sample.lists.DefaultRecyclerIndexableViewAdapter;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.simpleList.ListBackgroundDataManager;
import com.kimeeo.library.listDataView.recyclerView.BaseItemHolder;
import com.kimeeo.library.listDataView.recyclerView.BaseRecyclerViewAdapter;
import com.kimeeo.library.listDataView.recyclerView.verticalViews.ListView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by bhavinpadhiyar on 1/27/16.
 */
public class SimpleListView extends ListView {
    PostsService service;
    IListProvider listProvider = new IListProvider() {
        public List<?> getList(PageData data, Map<String, Object> param) {
            if (data.curruntPage == 1) {
                try {
                    Call<Posts> post = service.listPost();
                    return post.execute().body().posts;
                } catch (IOException e) {
                    System.out.println(e);
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
            return null;
        }
    };

    // Data Manager
    protected DataManager createDataManager() {
        ListBackgroundDataManager listData1 = new ListBackgroundDataManager(getActivity(), listProvider);
        listData1.setRefreshEnabled(false);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://kimeeo.com/restT/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(PostsService.class);
        return listData1;
    }

    protected BaseRecyclerViewAdapter createListViewAdapter() {
        return new DefaultRecyclerIndexableViewAdapter(getDataManager(), this);
    }

    // get View
    @Override
    public View getItemView(int viewType, LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout._sample_column_cell, null);
    }

    // get New BaseItemHolder
    @Override
    public BaseItemHolder getItemHolder(int viewType, View view) {
        return new RecyncleItemHolder1(view);
    }

    public interface PostsService {
        @GET("posts?X-API-KEY=KimeeoApp&transform=1")
        Call<Posts> listPost();
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
            Posts.Post listObject = (Posts.Post) item;
            label.setText(position + " -> " + listObject.getContent());
        }
    }
}
