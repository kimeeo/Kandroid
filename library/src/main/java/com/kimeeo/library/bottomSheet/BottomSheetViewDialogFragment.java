package com.kimeeo.library.bottomSheet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.BottomSheetDialog;
import com.gun0912.tedpermission.PermissionsBottomSheetDialogFragment;

/**
 * Created by BhavinPadhiyar on 14/04/16.
 */
public class BottomSheetViewDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
    private View view;
    public void setRootView(View rootView)
    {
        this.view = rootView;
    }
    public View getRootView()
    {
        return  view;
    }

    public void setRootView(int viewRes)
    {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.view = inflater.inflate(viewRes,null);
    }

    public void show(FragmentManager manager) {
        super.show(manager,getTag());
    }
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        if(getRootView()!=null) {
            View contentView = getRootView();

            if(contentView.getParent()!=null && contentView.getParent() instanceof ViewGroup)
                ((ViewGroup)contentView.getParent()).removeView(contentView);

            dialog.setContentView(contentView);

            if (contentView.getParent() != null && ((View) contentView.getParent()).getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                if (behavior != null && behavior instanceof BottomSheetBehavior)
                    ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            }
        }
    }

    public static BottomSheetViewDialogFragment show(FragmentActivity activity, @LayoutRes int viewRes)
    {
        if (viewRes <= 0)
            throw new IllegalArgumentException("Invalid layout");
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view= inflater.inflate(viewRes,null);
        return show(activity.getSupportFragmentManager(),view);
    }

    public static BottomSheetViewDialogFragment show(final FragmentManager fragmentManager,@LayoutRes int viewRes,Context context)
    {
        if (viewRes <= 0)
            throw new IllegalArgumentException("Invalid layout");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view= inflater.inflate(viewRes,null);
        return show(fragmentManager,view);
    }
    public static BottomSheetViewDialogFragment show(final FragmentManager fragmentManager,View view)
    {
        final BottomSheetViewDialogFragment bottomSheetDialogFragment = new BottomSheetViewDialogFragment();
        bottomSheetDialogFragment.setRootView(view);
        bottomSheetDialogFragment.show(fragmentManager, bottomSheetDialogFragment.getTag());
        return bottomSheetDialogFragment;
    }



}
