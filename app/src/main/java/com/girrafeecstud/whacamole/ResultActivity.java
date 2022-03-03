package com.girrafeecstud.whacamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView currentScore, recordScore;

    private Button playAgain, returnMainMenu;

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
        super.onBackPressed();
        ResultActivity.this.startActivity(new Intent(ResultActivity.this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initUiValues();

        getValuesFromGameActivity();

        playAgain.setOnClickListener(this);
        returnMainMenu.setOnClickListener(this);
    }

    private void initUiValues(){
        currentScore = findViewById(R.id.currentGameRecordTxt);
        playAgain = findViewById(R.id.playAgainBtn);
        returnMainMenu = findViewById(R.id.goToMenuBtn);
    }

    private void getValuesFromGameActivity(){
        Intent intent = getIntent();
        currentScore.setText(intent.getStringExtra(GameActivity.FINAL_SCORE_VALUE_EXTRA));
    }
}