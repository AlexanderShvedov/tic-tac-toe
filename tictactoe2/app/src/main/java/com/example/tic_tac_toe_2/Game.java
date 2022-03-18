package com.example.tic_tac_toe_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Game extends AppCompatActivity {
    ArrayList<ArrayList<Button>> buttons;
    Character current = 'X';
    TextView firstPlayerName;
    TextView firstPlayerPoints;
    TextView secondPlayerName;
    TextView secondPlayerPoints;
    Integer firstPlayerPointsInt = 0;
    Integer secondPlayerPointsInt = 0;
    ImageView imageView1;
    ImageView imageView2;
    boolean readyToClear = false;
    String names = "";
    String points = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle arguments = getIntent().getExtras();

        imageView1 = findViewById(R.id.imageView3);
        imageView1.setImageURI((Uri)arguments.get("avatar1"));

        imageView2 = findViewById(R.id.imageView4);
        imageView2.setImageURI((Uri)arguments.get("avatar2"));

        firstPlayerName = findViewById(R.id.firstPlayerName);
        firstPlayerPoints = findViewById(R.id.firstPlayerPoints);
        secondPlayerName = findViewById(R.id.secondPlayerName);
        secondPlayerPoints = findViewById(R.id.secondPlayerPoints);

        firstPlayerName.setText(arguments.getString("name1"));
        secondPlayerName.setText(arguments.getString("name2"));
        names = arguments.getString("names");
        points = arguments.getString("points");


        Button button = findViewById(R.id.exit);
        button.setOnClickListener(view -> {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("names", names + firstPlayerName.getText().toString() + "\n" + secondPlayerName.getText().toString() + "\n");
            i.putExtra("points", points + firstPlayerPoints.getText().toString() + "\n" + secondPlayerPoints.getText().toString() + "\n");
            startActivity(i);
        });

        Button restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(view -> {
            clearField();
        });

        buttons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            buttons.add(new ArrayList<>());
        }
        buttons.get(0).add(findViewById(R.id.button1));
        buttons.get(0).add(findViewById(R.id.button2));
        buttons.get(0).add(findViewById(R.id.button3));
        buttons.get(1).add(findViewById(R.id.button4));
        buttons.get(1).add(findViewById(R.id.button5));
        buttons.get(1).add(findViewById(R.id.button6));
        buttons.get(2).add(findViewById(R.id.button7));
        buttons.get(2).add(findViewById(R.id.button8));
        buttons.get(2).add(findViewById(R.id.button9));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons.get(i).get(j).setOnClickListener(view -> {
                    Button currentButton = findViewById(view.getId());
                    if (!readyToClear && currentButton.getText().toString().isEmpty()) {
                        currentButton.setText(current.toString());
                        if (current == 'X') {
                            current = '0';
                        } else  {
                            current = 'X';
                        }
                        for (int i0 = 0; i0 < 3; i0++) {
                            for (int j0 = 0; j0 < 3; j0++) {
                                if (buttons.get(i0).get(j0).equals(view)) {
                                    if (isOver(i0, j0)) {
                                        readyToClear = true;
                                        if (current == 'X') {
                                            secondPlayerPointsInt++;
                                            secondPlayerPoints.setText(secondPlayerPointsInt.toString());
                                        } else {
                                            firstPlayerPointsInt++;
                                            firstPlayerPoints.setText(firstPlayerPointsInt.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
        if (savedInstanceState != null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Integer k = (i * 3 + j + 1);
                    buttons.get(i).get(j).setText(savedInstanceState.getString(k.toString()));
                }
            }
            Uri a = Uri.parse(savedInstanceState.getString("savedavatar1"));
            imageView1.setImageURI(a);
            Uri b = Uri.parse(savedInstanceState.getString("savedavatar2"));
            imageView2.setImageURI(b);
            firstPlayerName.setText(savedInstanceState.getString("savedname1"));
            firstPlayerPoints.setText(savedInstanceState.getString("savedpoint1"));
            secondPlayerName.setText(savedInstanceState.getString("savedname2"));
            secondPlayerPoints.setText(savedInstanceState.getString("savedpoint2"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Integer k = (i * 3 + j + 1);
                outState.putString(k.toString(), buttons.get(i).get(j).getText().toString());
            }
        }
        outState.putString("savedname1", firstPlayerName.getText().toString());
        outState.putString("savedname2", firstPlayerPoints.getText().toString());
        outState.putString("savedpoint1", secondPlayerName.getText().toString());
        outState.putString("savedpoint2", secondPlayerPoints.getText().toString());
        outState.putString("savedavatar1", imageView1.toString());
        outState.putString("savedname1", imageView2.toString());
    }

    protected boolean isOver(int i, int j) {
        boolean isDiagonal = false;
        boolean isWinOnHorizontal = buttons.get(i).get(0).getText().equals(buttons.get(i).get(1).getText()) && buttons.get(i).get(0).getText().equals(buttons.get(i).get(2).getText());
        boolean isWinOnVertical = buttons.get(0).get(j).getText().equals(buttons.get(1).get(j).getText()) && buttons.get(0).get(j).getText().equals(buttons.get(2).get(j).getText());
        if (i == j) {
            isDiagonal |= buttons.get(0).get(0).getText().equals(buttons.get(1).get(1).getText()) && buttons.get(0).get(0).getText().equals(buttons.get(2).get(2).getText());
        }
        if (i == 2 + j || j == 2 + i) {
            isDiagonal |= buttons.get(0).get(2).getText().equals(buttons.get(1).get(1).getText()) && buttons.get(0).get(2).getText().equals(buttons.get(2).get(0).getText());
        }
        return isWinOnHorizontal || isWinOnVertical || isDiagonal;
    }

    protected void clearField() {
        readyToClear = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons.get(i).get(j).setText("");
            }
        }
        current = 'X';
    }
}