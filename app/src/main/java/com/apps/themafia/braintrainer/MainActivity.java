package com.apps.themafia.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    GridLayout mGridLayout;
    double percent;
    ConstraintLayout mConstraintLayout;
    Button mGo;
    Button mButton1 , mButton2 , mButton3 , mButton4 , replay , start;
    TextView mTextView1 , mTextView2 , mTextView3 , mresult;
    CountDownTimer mCountDownTimer;
    int answer;
    int score;
    int numberOfQuestions;
    int location;
    int newlocation;
    TextView mDiagnosis1 , mDiagnosis2 , mDiagnosis3;
    ArrayList<Integer> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridLayout = findViewById(R.id.gridLayout);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        replay = findViewById(R.id.replay);
        mTextView1 = findViewById(R.id.question);
        mTextView2 = findViewById(R.id.score);
        mTextView3 = findViewById(R.id.timer);
        mresult = findViewById(R.id.resultview);
        mDiagnosis1 = findViewById(R.id.diagnosis1);
        mDiagnosis2 = findViewById(R.id.diagnosis2);
        mDiagnosis3 = findViewById(R.id.diagnosis3);

        mConstraintLayout = findViewById(R.id.gameLayout);
        mConstraintLayout.setVisibility(View.INVISIBLE);
        mGo = findViewById(R.id.goButton);
        mGo.setVisibility(View.VISIBLE);

    }

    public void Go(View view){
        mGo.setVisibility(View.INVISIBLE);
        mConstraintLayout.setVisibility(View.VISIBLE);
        Start(view);
    }
    public void updateView(){

        Random rand = new Random();

        int q1 = rand.nextInt(70);
        int q2 = rand.nextInt(30);
        answer = q1 + q2;
        mTextView1.setText("" + q1 + " + " + q2);

        results.clear();
        location = rand.nextInt(4);
        for (int i=0; i < 4; i++){

            if (i == location){
                results.add(answer);
            }else{
                int wrong = rand.nextInt(70);
                while (wrong == answer){
                    wrong = rand.nextInt(70);
                }
                results.add(wrong);
            }
        }

        mButton1.setText(Integer.toString(results.get(0)));
        mButton2.setText(Integer.toString(results.get(1)));
        mButton3.setText(Integer.toString(results.get(2)));
        mButton4.setText(Integer.toString(results.get(3)));

        Log.i("Usertlocation" , "" + location);
        Log.i("Userques1" , "" + q1);
        Log.i("Userques2" , "" + q2);
        Log.i("Useranswerorignal" , "" + answer);
        Log.i("UseranswerArray" , "" + results);


    }

    public void checkAnswer(View view){
        if (Integer.toString(location).equals(view.getTag().toString())) {
            mresult.setText("Correct!");
            score++;
        } else {
            mresult.setText("Wrong :(");
        }
        numberOfQuestions++;
        mTextView2.setText("" + score + "/" + numberOfQuestions);
        updateView();
    }

    public void Start(View v){
        updateView();
        mCountDownTimer = new CountDownTimer(30100 , 1000) {
            @Override
            public void onTick(long l) {
                mTextView3.setText((l/1000) + "s");
                replay.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFinish() {
                mGridLayout.setVisibility(View.INVISIBLE);
                mTextView1.setText(R.string.timeup);
                mTextView3.setText("0s");
                mresult.setText("Done!!");
                replay.setVisibility(View.VISIBLE);
                diagnosis();
            }
        }.start();
    }


    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        mDiagnosis1.setVisibility(View.INVISIBLE);
        mDiagnosis2.setVisibility(View.INVISIBLE);
        mDiagnosis3.setVisibility(View.INVISIBLE);

        mTextView3.setText("30s");
        mTextView2.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        updateView();
        replay.setVisibility(View.INVISIBLE);
        mresult.setText("");
        mGridLayout.setVisibility(View.VISIBLE);

        new CountDownTimer(30100,1000) {

            @Override
            public void onTick(long l) {
                mTextView3.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                mresult.setText("Done!");
                replay.setVisibility(View.VISIBLE);
                mGridLayout.setVisibility(View.INVISIBLE);
                mTextView1.setText(R.string.timeup);
                mTextView3.setText("0s");
                diagnosis();
            }
        }.start();

    }

    public void diagnosis(){

        mDiagnosis1.setVisibility(View.VISIBLE);
        mDiagnosis2.setVisibility(View.VISIBLE);
        mDiagnosis3.setVisibility(View.VISIBLE);

        percent = (double) score/30;
        Log.i("percent" , "" + percent);
        double percentage = (percent * 100);
        String result = String.format("%.2f" , percentage);
        if (percentage < 50.00){
            mresult.setText(R.string.failure);
        }
        mDiagnosis1.setText("Total Answered = " + numberOfQuestions);
        mDiagnosis2.setText("Total Correct = " + score);
        mDiagnosis3.setText("Percentage = " + result + "%");
    }



}
