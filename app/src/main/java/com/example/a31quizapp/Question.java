package com.example.a31quizapp;

public class Question {
    private String questionText;
    private String[] options;
    // answer index is 0, 1, or 2
    private int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getOption(int index) {
        if (index >= 0 && index < options.length) {
            return options[index];
        }
        return "";
    }
}

