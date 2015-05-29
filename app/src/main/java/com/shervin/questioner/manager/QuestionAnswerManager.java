package com.shervin.questioner.manager;

import com.shervin.questioner.model.Answer;
import com.shervin.questioner.model.Question;
import com.shervin.questioner.provider.AnswerCache;
import com.shervin.questioner.provider.QuestionProvider;

import java.util.List;

public class QuestionAnswerManager {

    public static List<Question> getQuiz() {
        return QuestionProvider.getQuiz();
    }

    public static void cacheAnswer(Answer answer) {
        AnswerCache.cacheAnswer(answer);
    }

    public static List<Answer> getAnswers() {
        return AnswerCache.getAnswers();
    }
}
