package com.girrafeecstud.whacamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startGame, rules;

    private TextView bestRecordTxt;

    private ImageButton exitApp;

    Dialog exitDialog, rulesDialog;

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
            case R.id.gameRulesBtn:
                showRulesDialog();
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

        checkBestRecord();

        startGame.setOnClickListener(this);
        exitApp.setOnClickListener(this);
        rules.setOnClickListener(this);
    }

    // Initialization of UI elements
    private void initUiElements(){
        startGame = findViewById(R.id.startGameBtn);
        rules = findViewById(R.id.gameRulesBtn);
        exitApp = findViewById(R.id.exitAppBtn);
        bestRecordTxt = findViewById(R.id.recordTxt);

        exitDialog = new Dialog(this);
        rulesDialog = new Dialog(this);
    }

    // Procedure starts game activity
    private void startGameActivity(){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        MainActivity.this.startActivity(intent);
    }

    // Check if we have best record now
    private void checkBestRecord(){

        String bestRecord = loadSharedPreferences();

        if (!loadSharedPreferences().equals("")) {
            bestRecordTxt.setVisibility(View.VISIBLE);
            bestRecordTxt.setText(bestRecordTxt.getText().toString() + bestRecord);
            return;
        }

    }

    // Load best game record from prefs
    private String loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(ResultActivity.SHARED_PREFS, MODE_PRIVATE);
        String bestRecord = sharedPreferences.getString(ResultActivity.BEST_GAME_RECORD, "");
        return bestRecord;
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
                exitDialog.dismiss();
                finishAffinity();
            }
        });
    }

    // Show rules dialog
    private void showRulesDialog(){
        rulesDialog.setContentView(R.layout.game_rules_dialog_layout);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton closeRules = rulesDialog.findViewById(R.id.closeRulesBtn);
        RecyclerView rulesRecView = rulesDialog.findViewById(R.id.rulesRecView);

        ArrayList<RuleItem> rulesArrayList = new ArrayList<>();

        rulesArrayList.add(new RuleItem(R.drawable.ic_mole, getResources().getString(R.string.mole_rules)));
        rulesArrayList.add(new RuleItem(R.drawable.ic_golden_mole,getResources().getString(R.string.golden_mole_rule)));
        rulesArrayList.add(new RuleItem(R.drawable.ic_tnt_mole, getResources().getString(R.string.tnt_mole_rules)));

        System.out.println("ar sz: " + rulesArrayList.size());

        RulesRecViewAdapter adapter = new RulesRecViewAdapter(rulesArrayList, MainActivity.this);
        rulesRecView.setAdapter(adapter);
        rulesRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        System.out.println("size: " + adapter.getItemCount());

        rulesDialog.show();

        closeRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rulesDialog.dismiss();
            }
        });
    }
}