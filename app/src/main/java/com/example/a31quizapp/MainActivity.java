package com.example.a31quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.name_label), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.name_input);
        startButton = findViewById(R.id.start_button);

        // Restore username from SharedPreferences if available
        SharedPreferences prefs = getSharedPreferences("QuizAppPrefs", MODE_PRIVATE);
        String savedName = prefs.getString("last_user_name", "");
        if (!savedName.isEmpty()) {
            nameInput.setText(savedName);
        }

        // Check if name was passed via intent (i.e. from results screen)
        String nameFromIntent = getIntent().getStringExtra("user_name");
        if (nameFromIntent != null && !nameFromIntent.isEmpty()) {
            nameInput.setText(nameFromIntent);
        }

        startButton.setOnClickListener(v -> startQuiz());
    }

    private void startQuiz() {
        String name = nameInput.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("user_name", name);
        startActivity(intent);
    }
}