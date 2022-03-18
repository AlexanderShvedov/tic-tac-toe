package com.example.tic_tac_toe_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;


public class Score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Button buttonExit = findViewById(R.id.button13);
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle arguments = getIntent().getExtras();
            textView.setText(arguments.getString("names"));
            textView2.setText(arguments.getString("points"));
        }
        buttonExit.setOnClickListener(view -> {
            setContentView(R.layout.activity_main);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("names", textView.getText().toString());
            i.putExtra("points", textView2.getText().toString());
            startActivity(i);
        });
    }
}