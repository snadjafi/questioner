package com.shervin.questioner.util;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.widget.TextView;

import com.shervin.questioner.Application;

public class TypefaceUtil {

    //region Variable
    private static LruCache<String, Typeface> sTypefaceCache = new LruCache<>(2);
    //endregion

    public static void apply(TypefaceId id, TextView tv) {
        if (tv == null || tv.isInEditMode()) {
            return;
        }

        tv.setTypeface(getTypeface(id));
    }

    public static Typeface getTypeface(TypefaceId id) {
        Typeface typeface = sTypefaceCache.get(id.getFilePath());
        if (typeface == null) {
            typeface = Typeface.createFromAsset(Application.assets(), id.getFilePath());
            sTypefaceCache.put(id.getFilePath(), typeface);
        }
        return typeface;
    }

    public static SpannableString getStyledText(String title, TypefaceId id) {
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(id), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public interface TypefaceId {
        Typeface get();

        String getFilePath();
    }

    private static class TypefaceSpan extends MetricAffectingSpan {

        private Typeface mTypeface;

        public TypefaceSpan(TypefaceId id) {
            mTypeface = getTypeface(id);
        }

        @Override public void updateMeasureState(TextPaint p) {
            p.setTypeface(mTypeface);
            p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }

        @Override public void updateDrawState(TextPaint tp) {
            tp.setTypeface(mTypeface);
            tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }
}