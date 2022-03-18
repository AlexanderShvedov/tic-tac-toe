package com.example.tic_tac_toe_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Uri avatar1 = null;
    Uri avatar2 = null;
    String names = "";
    String points = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle arguments = getIntent().getExtras();
            names += arguments.getString("names", "");
            points += arguments.getString("points", "");
        }
        Button button = findViewById(R.id.startGame);
        button.setOnClickListener(view -> {
            setContentView(R.layout.before_game);

            Button button1 = findViewById(R.id.startGame2);
            button1.setOnClickListener(view1 -> {
                Intent i = new Intent(this, Game.class);
                i.putExtra("avatar1", avatar1);
                i.putExtra("avatar2", avatar2);
                TextView textView = findViewById(R.id.editTextTextPersonName);
                i.putExtra("name1", textView.getText().toString());
                textView = findViewById(R.id.editTextTextPersonName2);
                i.putExtra("name2", textView.getText().toString());
                i.putExtra("names", names);
                i.putExtra("points", points);
                startActivity(i);
            });

            Button buttonAvatar1 = findViewById(R.id.fst1);
            buttonAvatar1.setOnClickListener(view1 -> {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            });

            Button buttonAvatar2 = findViewById(R.id.snd1);
            buttonAvatar2.setOnClickListener(view1 -> {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
            });
        });

        Button buttonScore = findViewById(R.id.score);
        buttonScore.setOnClickListener(view -> {
            setContentView(R.layout.activity_score);
            Intent i = new Intent(this, Score.class);
            i.putExtra("names", names);
            i.putExtra("points", points);
            startActivity(i);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            ImageView img = findViewById(R.id.imageView);
            img.setImageURI(data.getData());
            avatar1 = data.getData();
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            ImageView img = findViewById(R.id.imageView2);
            img.setImageURI(data.getData());
            avatar2 = data.getData();
        }
    }
}