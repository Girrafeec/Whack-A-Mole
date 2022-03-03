package com.girrafeecstud.whacamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView currentScore, recordScore, newRecord;

    private Button playAgain, returnMainMenu;

    private int currentScoreInt = 0;

    public static final String SHARED_PREFS = "SHARED_PREFS";
    public static final String BEST_GAME_RECORD = "BEST_GAME_RECORD";

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.playAgainBtn:
                ResultActivity.this.startActivity(new Intent(ResultActivity.this, GameActivity.class));
                break;
            case R.id.goToMenuBtn:
                ResultActivity.this.startActivity(new Intent(ResultActivity.this, MainActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        ResultActivity.this.startActivity(new Intent(ResultActivity.this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initUiValues();

        getValuesFromGameActivity();

        checkScore();
        setBestRecordValue();

        playAgain.setOnClickListener(this);
        returnMainMenu.setOnClickListener(this);
    }

    // INitialization of UI elements
    private void initUiValues(){
        currentScore = findViewById(R.id.currentGameRecordTxt);
        recordScore = findViewById(R.id.bestGameRecordTxt);
        newRecord = findViewById(R.id.newRecordTxt);
        playAgain = findViewById(R.id.playAgainBtn);
        returnMainMenu = findViewById(R.id.goToMenuBtn);
    }

    // Get game points from Game Activity
    private void getValuesFromGameActivity(){
        Intent intent = getIntent();
        currentScoreInt = Integer.parseInt(intent.getStringExtra(GameActivity.FINAL_SCORE_VALUE_EXTRA));
        currentScore.setText(currentScore.getText().toString() + String.valueOf(currentScoreInt));
    }

    // Check if current score is the best record
    private void checkScore(){

        System.out.println("curren: " + currentScoreInt);
        System.out.println("prefs: " + loadSharedPreferences());

        if (loadSharedPreferences().equals("")) {
            newRecord.setVisibility(View.VISIBLE);
            saveSharedPreferences(String.valueOf(currentScoreInt));
            return;
        }

        if (currentScoreInt <= Integer.parseInt(loadSharedPreferences()))
            return;

        newRecord.setVisibility(View.VISIBLE);
        recordScore.setVisibility(View.VISIBLE);
        recordScore.setText(recordScore.getText().toString() +  String.valueOf(currentScoreInt));
        saveSharedPreferences(String.valueOf(currentScoreInt));

    }

    // Set best record value
    private void setBestRecordValue(){
        String bestRecord = loadSharedPreferences();

        if (loadSharedPreferences().equals("")) {
            recordScore.setVisibility(View.VISIBLE);
            recordScore.setText(recordScore.getText().toString() + "0");
            return;
        }
        recordScore.setVisibility(View.VISIBLE);
        recordScore.setText("Best: " + bestRecord);
    }

    // Save best record
    private void saveSharedPreferences(String bestRecord){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(BEST_GAME_RECORD, bestRecord);

        editor.apply();
    }

    // Load best game record from prefs
    private String loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String bestRecord = sharedPreferences.getString(BEST_GAME_RECORD, "");
        return bestRecord;
    }

}