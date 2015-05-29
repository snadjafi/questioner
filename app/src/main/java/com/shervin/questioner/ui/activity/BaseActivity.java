package com.shervin.questioner.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewParent;

import com.shervin.questioner.util.TypefaceUtil;
import com.shervin.questioner.util.Typefaces;

import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {

    protected void setTitle(String title) {
        ActionBar ab = getSupportActionBar();
        if (ab != null && !TextUtils.isEmpty(title)) {
            // toolbar.setTitle() won't work after you setSupportActionBar(toolbar)
            ab.setTitle(TypefaceUtil.getStyledText(title, Typefaces.ROBOTO_BOLD));
        } else {
            Timber.e("[ERR] make sure that you setSupportActionBar(toolbar) before calling this method");
        }
    }

    // I am using the cardview to generate the shadow for toolbar. this is for pre lollipop devices.
    // this makes a better shadow compare to adding the drawable.
    public void setSupportActionBar(@NonNull Toolbar toolbar) {
        final ViewParent parent = toolbar.getParent();
        if (parent instanceof CardView) {
            CardView toolbarCard = (CardView) parent;
            float radius = toolbarCard.getCardElevation();
            toolbarCard.setCardElevation(radius);
            toolbarCard.setMaxCardElevation(radius);
            toolbarCard.setShadowPadding(0, 0, 0, toolbarCard.getPaddingBottom());
        }
        super.setSupportActionBar(toolbar);
    }
}