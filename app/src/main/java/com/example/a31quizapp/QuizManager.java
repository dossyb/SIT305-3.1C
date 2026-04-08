package com.example.a31quizapp;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {
    private static List<Question> questions;

    public static List<Question> getQuestions() {
        if (questions == null) {
            questions = new ArrayList<>();
            initializeQuestions();
        }
        return questions;
    }

    private static void initializeQuestions() {
        questions.add(new Question(
                "In what year was the PlayStation 2 video game 'Haven: Call of the King' released?",
                new String[]{"2002", "2004", "2006"},
                0
        ));

        questions.add(new Question(
                "What is the primary resource Haven needs to replenish his antidote meter in 'Haven: Call of the King' called?",
                new String[]{"Essence", "Catana", "Cortana"},
                1
        ));

        questions.add(new Question(
                "What is the name of Haven's love interest in 'Haven: Call of the King'?",
                new String[]{"Chess", "Jess", "Tess"},
                0
        ));

        questions.add(new Question(
                "Who developed 'Haven: Call of the King'?",
                new String[]{"Insomniac Games", "Traveller's Tales", "Santa Monica Studios"},
                1
        ));

        questions.add(new Question(
                "What is the name of the mechanical bird Haven creates in 'Haven: Call of the King'?",
                new String[]{"Skarmory", "Clockwerk", "Talon"},
                2
        ));
    }

    public static int getTotalQuestions() {
        return getQuestions().size();
    }
}

