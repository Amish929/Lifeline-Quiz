package com.example.lifelinequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// QuizActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button[] answerButtons = new Button[4];
    private Button fiftyFiftyButton;
    private TextView scoreTextView;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        answerButtons[0] = findViewById(R.id.answerButton1);
        answerButtons[1] = findViewById(R.id.answerButton2);
        answerButtons[2] = findViewById(R.id.answerButton3);
        answerButtons[3] = findViewById(R.id.answerButton4);
        fiftyFiftyButton = findViewById(R.id.fiftyFiftyButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        questions = generateSampleQuestions();

        displayQuestion();

        for (Button button : answerButtons) {
            button.setOnClickListener(this::checkAnswer);
        }
    }

    private List<Question> generateSampleQuestions() {
        List<Question> sampleQuestions = new ArrayList<>();
        sampleQuestions.add(new Question("What is the capital of France?", "Paris", Arrays.asList("Berlin", "Madrid", "Rome")));
        sampleQuestions.add(new Question("What is 2 + 2?", "4", Arrays.asList("3", "5", "6")));
        sampleQuestions.add(new Question("What is the largest ocean?", "Pacific Ocean", Arrays.asList("Atlantic Ocean", "Indian Ocean", "Arctic Ocean")));
        sampleQuestions.add(new Question("Who wrote 'Hamlet'?", "William Shakespeare", Arrays.asList("Charles Dickens", "J.K. Rowling", "Mark Twain")));
        sampleQuestions.add(new Question("What is the speed of light?", "299,792,458 m/s", Arrays.asList("150,000,000 m/s", "300,000,000 m/s", "299,792 m/s")));
        sampleQuestions.add(new Question("What planet is known as the Red Planet?", "Mars", Arrays.asList("Earth", "Jupiter", "Saturn")));
        sampleQuestions.add(new Question("What is the smallest prime number?", "2", Arrays.asList("1", "3", "5")));
        sampleQuestions.add(new Question("Who painted the Mona Lisa?", "Leonardo da Vinci", Arrays.asList("Vincent van Gogh", "Pablo Picasso", "Claude Monet")));
        sampleQuestions.add(new Question("What is the chemical symbol for water?", "H2O", Arrays.asList("O2", "H2", "CO2")));
        sampleQuestions.add(new Question("What is the tallest mountain in the world?", "Mount Everest", Arrays.asList("K2", "Kangchenjunga", "Lhotse")));
        return sampleQuestions;
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionTextView.setText(question.getQuestionText());

            List<String> allAnswers = new ArrayList<>(question.getIncorrectAnswers());
            allAnswers.add(question.getCorrectAnswer());
            Collections.shuffle(allAnswers);

            for (int i = 0; i < answerButtons.length; i++) {
                answerButtons[i].setText(allAnswers.get(i));
                answerButtons[i].setVisibility(View.VISIBLE);
                answerButtons[i].setEnabled(true);
            }
        } else {
            questionTextView.setText("Quiz Finished! Your score: " + score);
            for (Button button : answerButtons) {
                button.setVisibility(View.INVISIBLE);
            }
            fiftyFiftyButton.setVisibility(View.INVISIBLE);
        }
    }

    private void checkAnswer(View view) {
        Button selectedButton = (Button) view;
        String selectedAnswer = selectedButton.getText().toString();
        Question question = questions.get(currentQuestionIndex);

        if (selectedAnswer.equals(question.getCorrectAnswer())) {
            score++;
        }

        currentQuestionIndex++;
        scoreTextView.setText("Score: " + score);
        displayQuestion();
    }

    public void useFiftyFifty(View view) {
        Question question = questions.get(currentQuestionIndex);
        List<Integer> incorrectAnswerIndices = new ArrayList<>();
        for (int i = 0; i < answerButtons.length; i++) {
            if (!answerButtons[i].getText().toString().equals(question.getCorrectAnswer())) {
                incorrectAnswerIndices.add(i);
            }
        }

        Collections.shuffle(incorrectAnswerIndices);
        for (int i = 0; i < 2; i++) {
            answerButtons[incorrectAnswerIndices.get(i)].setVisibility(View.INVISIBLE);
        }

        fiftyFiftyButton.setEnabled(false);
    }
}
