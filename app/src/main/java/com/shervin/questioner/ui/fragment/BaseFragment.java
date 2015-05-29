package com.shervin.questioner.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewParent;

import com.shervin.questioner.Application;
import com.shervin.questioner.util.TypefaceUtil;
import com.shervin.questioner.util.Typefaces;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

public class BaseFragment extends Fragment {

    @Override public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = Application.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar ab = activity.getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(showHomeAsUp);
            } else {
                Timber.e("[ERR] make sure that you setSupportActionBar(toolbar) before calling this method");
            }
        }
    }

    protected void setSupportActionBar(@NonNull Toolbar toolbar) {
        final ViewParent parent = toolbar.getParent();
        if (parent instanceof CardView) {
            CardView toolbarCard = (CardView) parent;
            float radius = toolbarCard.getCardElevation();
            toolbarCard.setCardElevation(radius);
            toolbarCard.setMaxCardElevation(radius);
            toolbarCard.setShadowPadding(0, 0, 0, toolbarCard.getPaddingBottom());
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    protected void setTitle(String title) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            ActionBar ab = activity.getSupportActionBar();
            if (ab != null && !TextUtils.isEmpty(title)) {
                // toolbar.setTitle() won't work after you setSupportActionBar(toolbar)
                ab.setTitle(TypefaceUtil.getStyledText(title, Typefaces.ROBOTO_BOLD));
            } else {
                Timber.e("[ERR] make sure that you setSupportActionBar(toolbar) before calling this method");
            }
        }
    }
}