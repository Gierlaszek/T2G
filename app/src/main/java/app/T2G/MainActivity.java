package app.T2G;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import app.T2G.activity.CounterMain;
import app.T2G.activity.LCDDisplay;
import app.T2G.activity.CodeBreaker;

public class MainActivity extends AppCompatActivity {

    Button counter;
    Button display;
    Button code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter = findViewById(R.id.counter);
        counter.setOnClickListener(v -> {
            Intent intent = new Intent(this, CounterMain.class);
            startActivity(intent);
        });

        code = findViewById(R.id.code);
        code.setOnClickListener(v -> {
            Intent intent = new Intent(this, CodeBreaker.class);
            startActivity(intent);
        });


        display = findViewById(R.id.LCD);
        display.setOnClickListener(v -> {
            Intent intent = new Intent(this, LCDDisplay.class);
            startActivity(intent);
        });


    }
}