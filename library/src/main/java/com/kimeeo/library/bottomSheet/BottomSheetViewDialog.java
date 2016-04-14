package com.kimeeo.library.bottomSheet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimeeo.library.R;

/**
 * Created by BhavinPadhiyar on 14/04/16.
 */
public class BottomSheetViewDialog {

    public static BottomSheetDialog show(Activity activity, @LayoutRes int viewRes)
    {
        if (viewRes <= 0)
            throw new IllegalArgumentException("Invalid layout");
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view= inflater.inflate(viewRes,null);
        return show(activity,view);
    }

    public static BottomSheetDialog show(Activity activity,View view)
    {
        if(view.getParent()!=null && view.getParent() instanceof ViewGroup)
            ((ViewGroup)view.getParent()).removeView(view);

        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        return mBottomSheetDialog;
    }



    public static BottomSheetDialog show(FragmentActivity activity,Fragment fragment)
    {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view= inflater.inflate(R.layout.bottom_sheet_dialog_fragment_holder,null);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();

        activity.getSupportFragmentManager().beginTransaction().replace(R.id.bottom_sheet_fragment_container,fragment);
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        return mBottomSheetDialog;
    }
}
