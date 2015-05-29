package com.shervin.questioner.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.shervin.questioner.R;
import com.shervin.questioner.util.TypefaceUtil;
import com.shervin.questioner.util.Typefaces;

public class TextView extends AppCompatTextView {

    public TextView(Context context) {
        super(context);
        init(null);
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.textview_typeface, 0, 0);
            try {
                Integer position = a.getInteger(R.styleable.textview_typeface_textFont, 0);
                setTypeface(TypefaceUtil.getTypeface(Typefaces.from(position)));
            } finally {
                a.recycle();
            }
        } else {
            setTypeface(TypefaceUtil.getTypeface(Typefaces.ROBOTO_REGULAR));
        }
    }
}
