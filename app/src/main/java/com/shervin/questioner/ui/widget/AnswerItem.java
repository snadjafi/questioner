package com.shervin.questioner.ui.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shervin.questioner.R;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AnswerItem extends RelativeLayout implements Checkable {

    @InjectView(R.id.image) ImageView mImage;

    private OnSelectedListener mListener;
    private String mImageUrl;
    private boolean mIsChecked;

    public AnswerItem(Context context) {
        super(context);
        init();
    }

    public AnswerItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnswerItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void bind(String imageUrl) {
        mImageUrl = imageUrl;
        Picasso.with(getContext())
                .load(imageUrl)
                .into(mImage);
    }

    @Override public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    @Override public boolean isChecked() {
        return mIsChecked;

    }

    @Override public void toggle() {
        setChecked(!mIsChecked);
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                toggle();
                if (mIsChecked) {
                    touchDown();
                    if (mListener != null) {
                        mListener.onSelected(this);
                    }
                } else {
                    touchUp();
                    if (mListener != null) {
                        mListener.onSelected(this);
                    }
                }
                break;
            case (MotionEvent.ACTION_UP):
                if (mIsChecked) {
                    if (mListener != null) {
                        mListener.onSelected(this);
                    }
                }
                break;
            case (MotionEvent.ACTION_CANCEL):
                unSelect();
                break;
            case (MotionEvent.ACTION_OUTSIDE):
                unSelect();
                break;
        }
        return super.onTouchEvent(event);
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void unSelect() {
        if (mIsChecked) {
            setChecked(false);
            touchUp();
        }
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.mListener = listener;
    }

    private void init() {
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(getContext()).inflate(R.layout.view_answer_item, this, true);
        ButterKnife.inject(this);
    }

    private void touchDown() {
        mImage.setAlpha(0.5F);
        animate().scaleX(0.8F).scaleY(0.8F).setDuration(75);
    }

    private void touchUp() {
        mImage.setAlpha(1F);
        animate().scaleX(1F).scaleY(1F).setDuration(75);
    }

    public interface OnSelectedListener {
        void onSelected(AnswerItem view);
    }
}