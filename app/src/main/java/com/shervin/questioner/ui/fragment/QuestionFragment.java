package com.shervin.questioner.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shervin.questioner.R;
import com.shervin.questioner.model.Answer;
import com.shervin.questioner.model.Question;
import com.shervin.questioner.ui.widget.AnswerItem;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class QuestionFragment extends BaseFragment implements AnswerItem.OnSelectedListener {

    private static final String QUESTION = "question";
    private static final String INDEX = "index";
    private static final Random RANDOM = new Random();

    @InjectView(R.id.title) TextView mTitle;
    @InjectView(R.id.submit) Button mSubmit;
    @InjectViews({R.id.first, R.id.second, R.id.third, R.id.fourth}) List<AnswerItem> mAnswerItems;

    private Question mQuestion;
    private AnswerInterface mCoordinator;
    private int mCorrectAnswer;

    public static QuestionFragment newInstance(Question question, int index) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(QUESTION, question);
        args.putInt(INDEX, index);
        fragment.setArguments(args);
        fragment.setRetainInstance(true);

        return fragment;
    }

    public QuestionFragment() {
    }

    @Override public void onAttach(Activity activity) {
        try {
            mCoordinator = (AnswerInterface) activity;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Parent does not implement " + getClass().getSimpleName() + "'s contract interface.", e);
        }

        super.onAttach(activity);
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = getArguments().getParcelable(QUESTION);
        mCorrectAnswer = RANDOM.nextInt(4);
        setDisplayHomeAsUpEnabled(true);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        mTitle.setText(mQuestion.getTitle());
        mSubmit.setEnabled(false);
        setupImages();
    }

    @Override public void onDetach() {
        super.onDetach();
        mCoordinator = null;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override public void onSelected(AnswerItem view) {
        mSubmit.setEnabled(true);
        unCheckAllAnswers(view);
    }

    private void unCheckAllAnswers(AnswerItem view) {
        for (AnswerItem item : mAnswerItems) {
            if (item.getId() != view.getId()) {
                item.unSelect();
            }
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    @OnClick(R.id.submit) public void onClick(View view) {
        Answer answer = new Answer(mQuestion, mCorrectAnswer == getSelectedAnswerIndex());
        if (hasAnswer()) {
            mCoordinator.onAnswer(answer, getArguments().getInt(INDEX));
        } else {
            Toast.makeText(getActivity(), "You need to select an answer.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasAnswer() {
        for (AnswerItem item : mAnswerItems) {
            if (item.isChecked()) {
                return true;
            }
        }

        return false;
    }

    private int getSelectedAnswerIndex() {
        for (int i = 0; i < mAnswerItems.size(); i++) {
            AnswerItem item = mAnswerItems.get(i);
            if (item.isChecked()) {
                return i;
            }
        }

        return 0;
    }

    private void setupImages() {
        List<String> imageUrls = mQuestion.getImageUrl();
        for (int i = 0; i < 4; i++) {
            AnswerItem item = mAnswerItems.get(i);
            item.bind(imageUrls.get(i));
            item.setOnSelectedListener(this);
        }
    }

    public interface AnswerInterface {
        void onAnswer(Answer answer, int index);
    }
}