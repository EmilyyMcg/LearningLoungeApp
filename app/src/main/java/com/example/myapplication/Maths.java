package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Maths extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = ""; // Holds the selected answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths);
        ImageView backButton = findViewById(R.id.maths_img); // Use ImageView if it's an ImageView
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open = new Intent(Maths.this, GameCentre.class);
                startActivity(open);
            }
        });
        totalQuestionsTextView = findViewById(R.id.total_questions);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions : " + totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        int purpleMain = ContextCompat.getColor(this, R.color.purpleMain);
        ansA.setBackgroundColor(purpleMain);
        ansB.setBackgroundColor(purpleMain);
        ansC.setBackgroundColor(purpleMain);
        ansD.setBackgroundColor(purpleMain);

        // Handle the submit button logic
        if (clickedButton.getId() == R.id.submit_btn) {
            // If the selected answer is correct, increment the score
            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();
        } else {
            // Handle answer button clicks (toggle the selection)
            toggleAnswer(clickedButton);
        }
    }

    void toggleAnswer(Button clickedButton) {
        // If the button is already selected, deselect it
        if (clickedButton.getBackground().equals(Color.MAGENTA)) {
            clickedButton.setBackgroundColor(Color.WHITE);
            selectedAnswer = ""; // Reset selected answer when deselecting
        } else {
            // Otherwise, select the button


            clickedButton.setBackgroundColor(Color.MAGENTA);  // Set selected button color
            selectedAnswer = clickedButton.getText().toString();  // Set selected answer text
        }
    }

    void loadNewQuestion() {
        // If there are no more questions, finish the quiz
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        // Load the next question and its choices
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        // Reset button backgrounds

        // Reset selected answer
        selectedAnswer = "";
    }

    void finishQuiz() {
        String passStatus = score > totalQuestion * 0.60 ? "Passed" : "Failed";

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}

