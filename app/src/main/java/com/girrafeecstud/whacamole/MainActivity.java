package com.girrafeecstud.whacamole;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startGame, rules;

    private ImageButton exitApp;

    Dialog exitDialog;

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startGameBtn:
                startGameActivity();
                break;
            case R.id.exitAppBtn:
                showExitDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUiElements();

        startGame.setOnClickListener(this);
        exitApp.setOnClickListener(this);
    }

    // Initialization of UI elements
    private void initUiElements(){
        startGame = findViewById(R.id.startGameBtn);
        rules = findViewById(R.id.gameRulesBtn);
        exitApp = findViewById(R.id.exitAppBtn);

        exitDialog = new Dialog(this);
    }

    // Procedure starts game activity
    private void startGameActivity(){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        MainActivity.this.startActivity(intent);
    }

    // Procedure shows exit dialog
    private void showExitDialog(){
        exitDialog.setContentView(R.layout.app_exit_dialog_layout);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button exitNo = exitDialog.findViewById(R.id.noExitBtn);
        Button exitYes = exitDialog.findViewById(R.id.yesExitBtn);

        exitDialog.show();

        exitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });

        exitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
}