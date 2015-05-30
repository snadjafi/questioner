package com.shervin.questioner.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shervin.questioner.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionProvider {

        public static Gson gson = new GsonBuilder().create();

        public static List<Question> getQuiz() {
                List<Question> questions = new ArrayList<>();
                JsonElement element = gson.fromJson(QUESTIONS, JsonElement.class);
                JsonObject jsonObj = element.getAsJsonObject();

                for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
                        questions.add(new Question(entry, jsonObj.getAsJsonArray(entry.getKey())));
                }

                return questions;
        }

        //region Question json
        private static final String QUESTIONS = "{ \n" +
                "  \"1. Pick a photo\": [ \n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"2. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"3. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"4. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"5. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"6. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"7. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"8. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"9. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ],\n" +
                "  \"10. Pick a photo\": [\n" +
                "    \"http://lorempixel.com/200/200/sports/1/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/2/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/3/\",\n" +
                "    \"http://lorempixel.com/200/200/sports/4/\" \n" +
                "  ]\n" +
                "}";
        //endregion
}