package com.kimeeo.kAndroid.rssDataManager;

import android.content.Context;
import android.os.AsyncTask;

import com.kimeeo.library.listDataView.dataManagers.BaseDataParser;
import com.kimeeo.library.listDataView.dataManagers.DataManager;
import com.kimeeo.library.listDataView.dataManagers.PageData;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;

import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/30/16.
 */
abstract public class RSSDataManager extends DataManager {

    private static final String LOG_TAG= "RSSDataManager";
    private  LongOperation longOperation;
    public void garbageCollectorCall()
    {
        super.garbageCollectorCall();
        longOperation=null;
    }
    public RSSDataManager(Context context)
    {
        super(context);
        longOperation =new LongOperation(new RSSReader());
    }

    protected void callService(String url)
    {
        longOperation.execute(url);
    }
    private class LongOperation  extends AsyncTask<String, Void, Void> {

        public LongOperation(RSSReader reader)
        {
            super();
            this.reader=reader;
        }
        protected void onPreExecute() {

        }
        RSSReader reader;
        RSSFeed feed;
        String url;
        protected Void doInBackground(String... urls) {
            url =urls[0];
            try
            {
                feed= reader.load(url);
            }catch (Exception e)
            {
                feed=null;
            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            if (feed != null) {
                feedLoaded(feed);
                List<RSSItem> data=feed.getItems();
                dataHandler(url,data,"DONE");
            } else {
                dataHandler(url, null, "ERROR");
            }
        }
    }

    protected void feedLoaded(RSSFeed feed) {

    }

    protected void parseData(String url, Object value, Object status)
    {
        boolean isRefreshPage = isRefreshPage(getPageData(),url);
        if(isRefreshPage==false)
            updatePagingData();
        dataLoadingDone((List<?>)value,isRefreshPage);
    }
    protected void updatePagingData() {
        try
        {
            getPageData().curruntPage +=1;
            getPageData().totalPage +=1;
        }catch (Exception e)
        {
            getPageData().curruntPage=getPageData().totalPage=1;
        }
    }

    protected boolean isRefreshPage(PageData pageData,String url)
    {
        if(getRefreshDataURL(pageData)!=null)
            return getRefreshDataURL(pageData).equals(url);
        return false;
    }


    final public Class<BaseDataParser> getLoadedDataParsingAwareClass()
    {
        return null;
    }
}
