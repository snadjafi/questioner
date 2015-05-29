package com.shervin.questioner.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Question implements Parcelable {
    private String title;
    private List<String> imageUrls = new ArrayList<>();

    public Question(Map.Entry<String, JsonElement> entry, JsonArray jsonArray) {
        title = entry.getKey();
        imageUrls = new ArrayList<>(4);

        for (JsonElement jsonElement : jsonArray) {
            imageUrls.add(jsonElement.getAsString());
        }
    }

    public Question(Parcel source) {
        title = source.readString();
        source.readStringList(imageUrls);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImageUrl() {
        return imageUrls;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringList(imageUrls);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        return getTitle().equals(question.getTitle());

    }

    @Override public int hashCode() {
        return getTitle().hashCode();
    }
}