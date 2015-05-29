package com.shervin.questioner.util;

import android.graphics.Typeface;

public enum Typefaces implements TypefaceUtil.TypefaceId {
    ROBOTO_BOLD("Roboto-Bold.ttf"),
    ROBOTO_REGULAR("Roboto-Regular.ttf");

    public static final String BASE_PATH = "fonts/";

    private String filename;

    Typefaces(String filename) {
        this.filename = filename;
    }

    public static Typefaces from(int index) {
        switch (index) {
            case 0:
                return ROBOTO_BOLD;
            case 1:
            default:
                return ROBOTO_REGULAR;
        }
    }

    @Override public Typeface get() {
        return TypefaceUtil.getTypeface(this);
    }

    @Override public String getFilePath() {
        return BASE_PATH + filename;
    }
}