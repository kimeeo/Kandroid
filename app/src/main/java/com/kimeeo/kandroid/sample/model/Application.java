package com.kimeeo.kandroid.sample.model;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import com.kimeeo.kandroid.sample.lists.ActionTester;
import com.kimeeo.kandroid.sample.lists.AdaptorLayoutView;
import com.kimeeo.kandroid.sample.lists.BaseView;
import com.kimeeo.kandroid.sample.lists.BaseViewListView;
import com.kimeeo.kandroid.sample.lists.BaseViewPager2;
import com.kimeeo.kandroid.sample.sheetDialogFragment.BottomSheet;
import com.kimeeo.kandroid.sample.lists.CardStackViewLikeAirTel;
import com.kimeeo.kandroid.sample.lists.CarouselView;
import com.kimeeo.kandroid.sample.lists.EasyCircleListView;
import com.kimeeo.kandroid.sample.lists.EasyHeaderVerticalGridView;
import com.kimeeo.kandroid.sample.lists.EasyHorizontalGridView;
import com.kimeeo.kandroid.sample.lists.EasyHorizontalListView;
import com.kimeeo.kandroid.sample.lists.EasyVerticalGridView;
import com.kimeeo.kandroid.sample.lists.EasyVerticalListView;
import com.kimeeo.kandroid.sample.lists.EasyVerticalListViewFastScroll;
import com.kimeeo.kandroid.sample.lists.EasyVerticalListViewFastScroll2;
import com.kimeeo.kandroid.sample.lists.LiteListView;
import com.kimeeo.kandroid.sample.lists.MapView;
import com.kimeeo.kandroid.sample.lists.MosaicListWithAdaptor;
import com.kimeeo.kandroid.sample.lists.OldVList;
import com.kimeeo.kandroid.sample.lists.ProfileBasedListView;
import com.kimeeo.kandroid.sample.lists.RSSListView;
import com.kimeeo.kandroid.sample.lists.RecycleViewBasicHorizontal;
import com.kimeeo.kandroid.sample.lists.SimpleList500pxStyleView;
import com.kimeeo.kandroid.sample.lists.SimpleListAssetsView;
import com.kimeeo.kandroid.sample.lists.SimpleListDirectoryView;
import com.kimeeo.kandroid.sample.lists.SimpleListView;
import com.kimeeo.kandroid.sample.lists.StackView;
import com.kimeeo.kandroid.sample.lists.StickyVerticalListView;
import com.kimeeo.kandroid.sample.lists.SwipeCards;
import com.kimeeo.kandroid.sample.lists.SwipeCardsDeck;
import com.kimeeo.kandroid.sample.lists.VerticalFlipViewWithDefaultAdaptor;
import com.kimeeo.kandroid.sample.viewPager.HFragmentPager;
import com.kimeeo.kandroid.sample.viewPager.HorizontalFlipableViewWithDefaltAdaptorView;
import com.kimeeo.kandroid.sample.viewPager.HorizontalPageViewWithDefaltAdaptorView;
import com.kimeeo.kandroid.sample.viewPager.VerticalPageViewWithDefaltAdaptorView;
import com.kimeeo.library.model.BaseApplication;
import com.kimeeo.library.model.IFragmentData;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.Iconics;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhavinpadhiyar on 1/11/16.
 */
public class Application extends BaseApplication {

    List<IFragmentData> menu;

    public List<IFragmentData> getMainMenu()
    {
        if(menu==null)
            menu = configMainMenu();
        return menu;
    }
    public List<IFragmentData> configMainMenu()
    {
        try {
            String packageName = getApplicationContext().getPackageName();
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(signature.toByteArray());
                String key = new String(Base64.encode(md.digest(), 0));
                key = key.trim();
                System.out.println(key);
            }
        }catch (Exception e)
        {

        }

        List<IFragmentData> data = new ArrayList<>();

        FragmentData fragmentData;

        fragmentData = new FragmentData("01", "V List", "", "", EasyVerticalListView.class, "");
        data.add(fragmentData);

        fragmentData = new FragmentData("01", "Action Tester", "", "", ActionTester.class, "");
        data.add(fragmentData);



        fragmentData = new FragmentData("02", "H Page", "", "", HorizontalPageViewWithDefaltAdaptorView.class, "");
        data.add(fragmentData);


        fragmentData=new FragmentData("02","V Page","","",VerticalPageViewWithDefaltAdaptorView.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("02","H Fragment Page","","",HFragmentPager.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("02","RecycleViewBasicHorizontal Pager","","",RecycleViewBasicHorizontal.class,"");
        data.add(fragmentData);

        fragmentData = new FragmentData("01", "500 px", "", "", SimpleList500pxStyleView.class, "");
        data.add(fragmentData);


        fragmentData=new FragmentData("02","V Grid","","",EasyVerticalGridView.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("02","Old List","","",OldVList.class,"");
        data.add(fragmentData);



        fragmentData=new FragmentData("02","H List","","",EasyHorizontalListView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("02","H Grid","","",EasyHorizontalGridView.class,"");
        data.add(fragmentData);




        fragmentData=new FragmentData("03","Profile Based Grid","","",ProfileBasedListView.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("02","V List Fast scroll","","",EasyVerticalListViewFastScroll.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","V List Fast scroll 2","","",EasyVerticalListViewFastScroll2.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("02","London Eye","","",EasyCircleListView.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("02","CarouselView","","",CarouselView.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("02","Flippable H","","",HorizontalFlipableViewWithDefaltAdaptorView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("02","Page Flip","","",VerticalFlipViewWithDefaultAdaptor.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","SwipeCards","","",SwipeCards.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("04","SwipeCardsDeck","","",SwipeCardsDeck.class,"");
        data.add(fragmentData);




        fragmentData=new FragmentData("04","StackView","","",StackView.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("04","Mosaic List","","",MosaicListWithAdaptor.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","SQL Lite List","","",LiteListView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","Simple Statis List","","",SimpleListView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","BaseView","","",BaseView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","BaseListView","","",BaseViewListView.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("04","BaseViewPager","","", com.kimeeo.kandroid.sample.lists.BaseViewPager.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("04","BaseViewPager2","","", BaseViewPager2.class,"");
        data.add(fragmentData);


        fragmentData=new FragmentData("04","StickyVerticalListView","","",StickyVerticalListView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","RSS","","",RSSListView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","Directory List","","",SimpleListDirectoryView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","Assets List","","",SimpleListAssetsView.class,"");
        data.add(fragmentData);





        fragmentData=new FragmentData("04","Header View","","",EasyHeaderVerticalGridView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","MapView","","",MapView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","Airtel View","","",CardStackViewLikeAirTel.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","AdaptorLayoutView","","",AdaptorLayoutView.class,"");
        data.add(fragmentData);

        fragmentData=new FragmentData("04","BottomSheet","","",BottomSheet.class,"");
        data.add(fragmentData);

        return data;
    }
    public void configApplication()
    {
        Iconics.registerFont(new FontAwesome());
        getMainMenu();
    }
}
