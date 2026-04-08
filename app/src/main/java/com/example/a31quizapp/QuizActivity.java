package com.example.a31quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private String userName;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int selectedAnswerIndex = -1;
    private boolean isAnswerSubmitted = false;
    private boolean hasSubmittedAnyAnswer = false;

    private TextView welcomeBanner;
    private TextView questionTitle;
    private TextView questionDetails;
    private Button[] answerButtons;
    private Button submitButton;
    private ProgressBar progressBar;
    private TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        final android.view.View quizRoot = findViewById(R.id.quiz_root);
        final int initialLeftPadding = quizRoot.getPaddingLeft();
        final int initialTopPadding = quizRoot.getPaddingTop();
        final int initialRightPadding = quizRoot.getPaddingRight();
        final int initialBottomPadding = quizRoot.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(quizRoot, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    initialLeftPadding + systemBars.left,
                    initialTopPadding + systemBars.top,
                    initialRightPadding + systemBars.right,
                    initialBottomPadding + systemBars.bottom
            );
            return insets;
        });

        userName = getIntent().getStringExtra("user_name");

        welcomeBanner = findViewById(R.id.welcome_banner);
        questionTitle = findViewById(R.id.question_title);
        questionDetails = findViewById(R.id.question_details);
        submitButton = findViewById(R.id.submit_button);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);

        answerButtons = new Button[3];
        answerButtons[0] = findViewById(R.id.answer_button_1);
        answerButtons[1] = findViewById(R.id.answer_button_2);
        answerButtons[2] = findViewById(R.id.answer_button_3);

        questions = QuizManager.getQuestions();

        welcomeBanner.setText("Welcome " + userName + "!");

        loadQuestion();

        for (int i = 0; i < answerButtons.length; i++) {
            final int buttonIndex = i;
            answerButtons[i].setOnClickListener(v -> selectAnswer(buttonIndex));
        }

        submitButton.setOnClickListener(v -> submitAnswer());
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            goToResults();
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        isAnswerSubmitted = false;
        selectedAnswerIndex = -1;

        welcomeBanner.setVisibility(hasSubmittedAnyAnswer ? View.GONE : View.VISIBLE);

        progressBar.setMax(questions.size());
        progressBar.setProgress(currentQuestionIndex + 1);
        progressText.setText((currentQuestionIndex + 1) + "/" + questions.size());

        questionTitle.setText("Question " + (currentQuestionIndex + 1) + ":");
        questionDetails.setText(currentQuestion.getQuestionText());

        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(options[i]);
            answerButtons[i].setBackgroundTintList(null);
            answerButtons[i].setBackgroundColor(getResources().getColor(R.color.button_blue, null));
            answerButtons[i].setTextColor(getResources().getColor(R.color.white, null));
            answerButtons[i].setEnabled(true);
            answerButtons[i].setClickable(true);
            answerButtons[i].setFocusable(true);
            answerButtons[i].setAlpha(1.0f);
        }

        submitButton.setText("SUBMIT");
        submitButton.setBackgroundColor(getResources().getColor(R.color.button_grey, null));
    }

    private void selectAnswer(int buttonIndex) {
        // Prevent changing selected answer after submitting
        if (!isAnswerSubmitted) {
            selectedAnswerIndex = buttonIndex;
            for (int i = 0; i < answerButtons.length; i++) {
                if (i == buttonIndex) {
                    answerButtons[i].setAlpha(0.7f);
                } else {
                    answerButtons[i].setAlpha(1.0f);
                }
            }
        }
    }

    private void submitAnswer() {
        if (isAnswerSubmitted) {
            currentQuestionIndex++;
            loadQuestion();
        } else {
            if (selectedAnswerIndex == -1) {
                return;
            }

            isAnswerSubmitted = true;
            hasSubmittedAnyAnswer = true;
            welcomeBanner.setVisibility(View.GONE);

            Question currentQuestion = questions.get(currentQuestionIndex);
            int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();

            // Apply button colours based on correct/incorrect answer
            if (selectedAnswerIndex == correctAnswerIndex) {
                answerButtons[selectedAnswerIndex].setBackgroundColor(
                        getResources().getColor(R.color.correct_green, null));
                score++;
            } else {
                answerButtons[selectedAnswerIndex].setBackgroundColor(
                        getResources().getColor(R.color.incorrect_red, null));
                answerButtons[correctAnswerIndex].setBackgroundColor(
                        getResources().getColor(R.color.correct_green, null));
            }

            for (Button button : answerButtons) {
                button.setClickable(false);
                button.setFocusable(false);
                button.setAlpha(1.0f);
            }

            submitButton.setText("NEXT");
        }
    }

    private void goToResults() {
        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("score", score);
        intent.putExtra("total_questions", questions.size());
        startActivity(intent);
        finish();
    }
}

