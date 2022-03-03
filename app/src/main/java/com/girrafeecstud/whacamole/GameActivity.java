package com.girrafeecstud.whacamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView countDownTimerTxt, scoreTxt;

    private ImageButton firstMole, secondMole, thirdMole, fourthMole, fifthMole, sixthMole, seventhMole, eigthMole, ninethMole;

    CountDownTimer mainCountDownTimer;

    private ArrayList<Integer> availiableMoleIdArrayList = new ArrayList<>();

    int[] moleIdArray = {
            R.id.firstMoleBtn,
            R.id.secondMoleBtn,
            R.id.thirdMoleBtn,
            R.id.fourthMoleBtn,
            R.id.fifthMoleBtn,
            R.id.sixthMoleBtn,
            R.id.seventhMoleBtn,
            R.id.eigthMoleBtn,
            R.id.ninethMoleBtn};

    private static boolean moleIsActive = false;

    public static final String FINAL_SCORE_VALUE_EXTRA = "FINAL_SCORE_VALUE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initUiElements();

        fillMolesArrayList(0);

        startTimer();
    }

    private void initUiElements(){
        countDownTimerTxt = findViewById(R.id.countDownTimerTxt);
        scoreTxt = findViewById(R.id.scoreTxt);

        firstMole = findViewById(R.id.firstMoleBtn);
        secondMole = findViewById(R.id.secondMoleBtn);
        thirdMole = findViewById(R.id.thirdMoleBtn);
        fourthMole = findViewById(R.id.fourthMoleBtn);
        fifthMole = findViewById(R.id.fifthMoleBtn);
        sixthMole = findViewById(R.id.sixthMoleBtn);
        seventhMole = findViewById(R.id.seventhMoleBtn);
        eigthMole = findViewById(R.id.eigthMoleBtn);
        ninethMole = findViewById(R.id.ninethMoleBtn);
    }

    // Initialization of availiable moles array list values
    private void fillMolesArrayList(int notAvailiableMoleId){

        for (int i=0; i<moleIdArray.length; i++)
            availiableMoleIdArrayList.add(moleIdArray[i]);

        // Removing last mole from list
        if (notAvailiableMoleId != 0)
            availiableMoleIdArrayList.remove(new Integer(notAvailiableMoleId));

    }

    private void startTimer(){
        // Interval - 1 second
        // Timer - 30 seconds
        mainCountDownTimer = new CountDownTimer(10000, 1) { // TODO поменять на 30000 в конце работы
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long seconds = (millisUntilFinished / 1000) % 60;
                long millies = millisUntilFinished % 1000;
                countDownTimerTxt.setText(f.format(seconds) + ":" + f.format(millies));
                if(!moleIsActive)
                    showMole();
            }
            // When the game is over it will be 00:00
            public void onFinish() {
                countDownTimerTxt.setText("00:00");
                startResultActivity();
            }
        }.start();
    }

    // Start game
    private void showMole(){

        // Special moles
        boolean tntMole = false;
        boolean goldenMole = false;

        // Calculate the chance of special moles
        Random randomMole = new Random();
        int randomMoleValue = randomMole.nextInt(20);
        // 10% chance to see mole with tnt
        if (randomMoleValue == 0 || randomMoleValue == 1)
            tntMole  = true;

        // 5% chance to see golden mole
        if (randomMoleValue == 2)
            goldenMole = true;

        // Random mole crawals out
        ImageButton activeMole
                = findViewById(availiableMoleIdArrayList.get(new Random().nextInt(availiableMoleIdArrayList.size())));
        activeMole.setEnabled(true);
        activeMole.setImageResource(R.drawable.ic_mole);
      // if (tntMole)
           // activeMole.setImageResource(R.drawable.tnt_mole);
       // if (goldenMole)
           // activeMole.setImageResource(R.drawable.golden_mole);
        activeMole.setEnabled(true);
        moleIsActive = true;

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run(){
                // Clear moles Array List and then fill it again without last mole
                availiableMoleIdArrayList.clear();
                fillMolesArrayList(activeMole.getId());
                // Hide mole
                activeMole.setImageResource(R.drawable.ic_mole_hole);
                activeMole.setEnabled(false);
                moleIsActive = false;
            }
        };
        // Mole hides after 0.5 second
        handler.postDelayed(runnable, 600);

        // Listeners for all buttons with moles
        for (int i=0; i< moleIdArray.length; i++){

            boolean innerTntMoleStatus = tntMole;
            boolean innerGoldenMoleStatus = goldenMole;

            ImageButton imageButton = findViewById(moleIdArray[i]);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!imageButton.isEnabled())
                        return;

                        imageButton.setImageResource(R.drawable.ic_mole_hole);

                        // Finish game if we click on mole with tnt
                        if (innerTntMoleStatus == true){
                            mainCountDownTimer.cancel();
                            mainCountDownTimer.onFinish();
                            return;
                        }

                        // Add 3 points if clicked at golden mole
                        if (innerGoldenMoleStatus == true){
                            scoreTxt.setText(String.valueOf(Integer.parseInt(scoreTxt.getText().toString()) + 3));
                            return;
                        }

                        scoreTxt.setText(String.valueOf(Integer.parseInt(scoreTxt.getText().toString()) + 1));
                        Toast.makeText(GameActivity.this, String.valueOf(imageButton.getId()), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    // Start Result Activity when game is finished
    private void startResultActivity(){
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putExtra(GameActivity.FINAL_SCORE_VALUE_EXTRA, scoreTxt.getText().toString());
        GameActivity.this.startActivity(intent);
    }
}