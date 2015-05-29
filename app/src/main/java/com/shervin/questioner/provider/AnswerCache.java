package com.shervin.questioner.provider;

import com.shervin.questioner.model.Answer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnswerCache {

    private static Set<Answer> answers = new HashSet<>();

    public static void cacheAnswer(Answer answer) {
        answers.add(answer);
    }

    public static List<Answer> getAnswers() {
        return new ArrayList<>(answers);
    }
}
