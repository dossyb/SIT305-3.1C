package com.example.a31quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultsActivity extends AppCompatActivity {

    private String userName;
    private int score;
    private int totalQuestions;

    private TextView congratulationsMessage;
    private TextView scoreValue;
    private Button newQuizButton;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.congratulations_message), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userName = getIntent().getStringExtra("user_name");
        score = getIntent().getIntExtra("score", 0);
        totalQuestions = getIntent().getIntExtra("total_questions", 5);

        congratulationsMessage = findViewById(R.id.congratulations_message);
        scoreValue = findViewById(R.id.score_value);
        newQuizButton = findViewById(R.id.new_quiz_button);
        finishButton = findViewById(R.id.finish_button);

        congratulationsMessage.setText("Congratulations " + userName + "!");

        scoreValue.setText(score + "/" + totalQuestions);

        // Save username for next quiz
        SharedPreferences prefs = getSharedPreferences("QuizAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("last_user_name", userName);
        editor.apply();

        newQuizButton.setOnClickListener(v -> takeNewQuiz());
        finishButton.setOnClickListener(v -> finishApp());
    }

    private void takeNewQuiz() {
        Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
        intent.putExtra("user_name", userName);
        startActivity(intent);
        finish();
    }

    private void finishApp() {
        finishAffinity();
    }
}

