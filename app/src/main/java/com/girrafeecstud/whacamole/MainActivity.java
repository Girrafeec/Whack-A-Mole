package com.girrafeecstud.whacamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startGame;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startGameBtn:
                startGameActivity();
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
    }

    // Initialization of UI elements
    private void initUiElements(){
        startGame = findViewById(R.id.startGameBtn);
    }

    // Procedure starts game activity
    private void startGameActivity(){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        MainActivity.this.startActivity(intent);
    }
}