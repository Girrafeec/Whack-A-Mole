package com.girrafeecstud.whacamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gusakov.library.PulseCountDown;
import com.gusakov.library.java.interfaces.OnCountdownCompleted;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView countDownTimerTxt, scoreTxt;

    private ImageButton resPauseBtn;
    private ImageButton firstMole, secondMole,thirdMole,fourthMole,fifthMole,sixthMole,seventhMole,eighthMole,ninethMole;

    private CountDownTimer mainCountDownTimer;

    private PulseCountDown startGameCountDownTimer;

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

    private static boolean moleIsActive = false, countDownWorks = false;

    private long curSeconds = 0, curMillies = 0;

    private static final int GAME_TIME = 30000;

    public static final String FINAL_SCORE_VALUE_EXTRA = "FINAL_SCORE_VALUE_EXTRA";

    @Override
    public void onBackPressed() {
        pauseGame();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pauseGameBtn:
                rePauseTimer();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initUiElements();

        disableHoles();

        fillMolesArrayList(0);

        resPauseBtn.setOnClickListener(this);

        startPulse(GAME_TIME);
    }

    private void initUiElements(){

        firstMole = findViewById(R.id.firstMoleBtn);
        secondMole = findViewById(R.id.secondMoleBtn);
        thirdMole = findViewById(R.id.thirdMoleBtn);
        fourthMole = findViewById(R.id.fourthMoleBtn);
        fifthMole = findViewById(R.id.fifthMoleBtn);
        sixthMole = findViewById(R.id.sixthMoleBtn);
        seventhMole = findViewById(R.id.seventhMoleBtn);
        eighthMole = findViewById(R.id.eigthMoleBtn);
        ninethMole = findViewById(R.id.ninethMoleBtn);

        countDownTimerTxt = findViewById(R.id.countDownTimerTxt);
        scoreTxt = findViewById(R.id.scoreTxt);

        startGameCountDownTimer =  findViewById(R.id.pulseCountDown);

        resPauseBtn = findViewById(R.id.pauseGameBtn);
    }

    // Disable all holes at the start of the game
    private void disableHoles(){
        firstMole.setEnabled(false);
        secondMole.setEnabled(false);
        thirdMole.setEnabled(false);
        fourthMole.setEnabled(false);
        fifthMole.setEnabled(false);
        sixthMole.setEnabled(false);
        seventhMole.setEnabled(false);
        eighthMole.setEnabled(false);
        ninethMole.setEnabled(false);
    }

    // Initialization of availiable moles array list values
    private void fillMolesArrayList(int notAvailiableMoleId){

        for (int i=0; i<moleIdArray.length; i++)
            availiableMoleIdArrayList.add(moleIdArray[i]);

        // Removing last mole from list
        if (notAvailiableMoleId != 0)
            availiableMoleIdArrayList.remove(new Integer(notAvailiableMoleId));

    }

    // Procedure proccess pause and resume functions
    private void rePauseTimer(){

        if (countDownWorks == true){
            pauseGame();
            return;
        }
        resumeGame();

    }

    // Pause game
    private void pauseGame(){
        countDownWorks = false;
        mainCountDownTimer.cancel();
        resPauseBtn.setImageResource(R.drawable.play_selector);
    }

    // Resume game
    private void resumeGame(){
        int time = (int) curMillies + (int) curSeconds*1000;
        startPulse(time);
        resPauseBtn.setImageResource(R.drawable.pause_selector);
    }

    // Start pulse timer before game
    private void startPulse(int time){
        startGameCountDownTimer.start(new OnCountdownCompleted() {
            @Override
            public void completed() {
                startTimer(time);
            }
        });
    }

    // Start main game timer
    private void startTimer(int time){

        // Make resume pause Button visible
        resPauseBtn.setVisibility(View.VISIBLE);

        // Interval - 1 second
        // Timer - 30 seconds
        mainCountDownTimer = new CountDownTimer(time, 1) { // TODO поменять на 30000 в конце работы
            public void onTick(long millisUntilFinished) {
                countDownWorks = true;
                long seconds = (millisUntilFinished / 1000) % 60;
                // Save current seconds remaining
                curSeconds = seconds;
                long millies = millisUntilFinished % 1000;
                // Save current millies remaining
                curMillies = millies;

                String stringSeconds = "", stringMillies = "";

                // Format seconds
                if (String.valueOf(seconds).length() < 2)
                    stringSeconds += " " + seconds;
                else if (String.valueOf(seconds).length() == 2)
                    stringSeconds += seconds;

                // Format millies
                if (String.valueOf(millies).length() < 2)
                    stringMillies += "00" + millies;
                else if (String.valueOf(millies).length() < 3)
                    stringMillies += "0" + millies;
                else if (String.valueOf(millies).length() == 3)
                    stringMillies += millies;

                countDownTimerTxt.setText(stringSeconds + "." + stringMillies);
                if(!moleIsActive)
                    showMole();
            }
            // When the game is over it will be 0.000
            public void onFinish() {
                countDownTimerTxt.setText("0.000");
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
        // Set mole image
        activeMole.setImageResource(R.drawable.ic_mole);
       if (tntMole)
            activeMole.setImageResource(R.drawable.ic_tnt_mole);
        if (goldenMole)
            activeMole.setImageResource(R.drawable.ic_golden_mole);
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
        handler.postDelayed(runnable, 500);

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