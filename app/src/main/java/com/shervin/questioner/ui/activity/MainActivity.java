package com.shervin.questioner.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.commonsware.cwac.pager.PageDescriptor;
import com.commonsware.cwac.pager.v4.ArrayPagerAdapter;
import com.shervin.questioner.R;
import com.shervin.questioner.manager.QuestionAnswerManager;
import com.shervin.questioner.model.Answer;
import com.shervin.questioner.model.Question;
import com.shervin.questioner.ui.fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements QuestionFragment.AnswerInterface {

    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.start) Button mStart;
    @InjectView(R.id.viewpager) ViewPager mViewPager;

    private List<Question> mQuestions;
    private ArrayPagerAdapter<Fragment> mAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        setupViewPager();
    }

    @SuppressWarnings("UnusedDeclaration")
    @OnClick(R.id.start) public void onClick(View view) {
        view.setVisibility(View.GONE);
        mQuestions = QuestionAnswerManager.getQuiz();
        mViewPager.setOffscreenPageLimit(mQuestions.size());
        addQuestion(0);
    }

    @Override public void onAnswer(Answer answer, int questionIndex) {
        QuestionAnswerManager.cacheAnswer(answer);
        if (questionIndex + 1 == mQuestions.size()) {
            List<Answer> answers = QuestionAnswerManager.getAnswers();
            new AlertDialog.Builder(this)
                    .setMessage(String.format("%d out of %d right",getCorrectAnswersCount(answers),
                            answers.size()))
                    .setCancelable(false)
                    .setPositiveButton("Retake", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            restartApplication();
                        }
                    })
                    .show();
        } else {
            addQuestion(questionIndex + 1);
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onBackPressed() {
        if (mAdapter.getCount() > 0) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
            mViewPager.postDelayed(new Runnable() {
                @Override public void run() {
                    mAdapter.remove(mAdapter.getCount() - 1);
                    if (mAdapter.getCount() == 0) {
                        mStart.setVisibility(View.VISIBLE);
                    }
                }
            }, 300);

            return;
        }

        super.onBackPressed();
    }

    private void restartApplication() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(IntentCompat.makeRestartActivityTask(intent.getComponent()));
    }

    private void setupViewPager() {
        mAdapter = buildAdapter();
        mViewPager.setAdapter(mAdapter);
    }

    private void addQuestion(int position) {
        if (position != 0 && mViewPager.getCurrentItem() == position) {
            return;
        }

        if (position < mAdapter.getCount()){
            return;
        }

        QuestionPageDescriptor desc = new QuestionPageDescriptor(mQuestions.get(position), position);
        mAdapter.add(desc);
        mViewPager.setCurrentItem(mAdapter.getCount() - 1, true);
    }

    private ArrayPagerAdapter<Fragment> buildAdapter() {
        ArrayList<PageDescriptor> pages = new ArrayList<>();
        return new PagerAdapter(getSupportFragmentManager(), pages, ArrayPagerAdapter.KEEP);
    }

    private static class PagerAdapter extends ArrayPagerAdapter<Fragment> {

        public PagerAdapter(FragmentManager fragmentManager,
                            List<PageDescriptor> descriptors,
                            RetentionStrategy retentionStrategy) {
            super(fragmentManager, descriptors, retentionStrategy);
        }

        @Override protected Fragment createFragment(PageDescriptor pageDescriptor) {
            return QuestionFragment.newInstance(
                    ((QuestionPageDescriptor) pageDescriptor).getQuestion(),
                    ((QuestionPageDescriptor) pageDescriptor).getPosition());
        }
    }

    private int getCorrectAnswersCount(List<Answer> answers) {
        int count = 0;
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                count++;
            }
        }

        return count;
    }

    private static class QuestionPageDescriptor implements PageDescriptor {
        private Question question;
        private int position;

        public QuestionPageDescriptor(@NonNull Question question, int position) {
            this.question = question;
            this.position = position;
        }

        public QuestionPageDescriptor(Parcel source) {
            question = source.readParcelable(Question.class.getClassLoader());
            this.position = source.readInt();
        }

        public Question getQuestion() {
            return question;
        }

        public int getPosition() {
            return position;
        }

        @Override public String getFragmentTag() {
            return String.valueOf(position);
        }

        @Override public String getTitle() {
            return String.valueOf(position);
        }

        @Override public int describeContents() {
            return 0;
        }

        @Override public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(question, 0);
            dest.writeInt(position);
        }

        public static final Parcelable.Creator<QuestionPageDescriptor> CREATOR = new Parcelable.Creator<QuestionPageDescriptor>() {
            public QuestionPageDescriptor createFromParcel(Parcel source) {
                return new QuestionPageDescriptor(source);
            }

            public QuestionPageDescriptor[] newArray(int size) {
                return new QuestionPageDescriptor[size];
            }
        };
    }
}