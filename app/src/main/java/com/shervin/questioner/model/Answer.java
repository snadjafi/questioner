package com.shervin.questioner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    private Question question;
    private boolean isCorrect;

    public Answer(Question question, boolean isCorrect) {
        this.question = question;
        this.isCorrect = isCorrect;
    }

    public Answer(Parcel source) {
        question = source.readParcelable(Question.class.getClassLoader());
        isCorrect = source.readInt() == 1;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;
        return question.equals(answer.question);
    }

    @Override public int hashCode() {
        return question.hashCode();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(question, 0);
        dest.writeInt(isCorrect ? 1 : 0);
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
