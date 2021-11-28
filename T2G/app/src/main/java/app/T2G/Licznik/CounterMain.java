package app.T2G.Licznik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import app.T2G.R;

public class CounterMain extends AppCompatActivity {

    /*
    Variables
     */
    private TextView info;
    private LinearLayout main;
    private LinearLayout countersLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_main);

        Counters counters = new Counters();

        main = findViewById(R.id.mainCounter);
        info = findViewById(R.id.info);
        countersLayout = findViewById(R.id.counters);
        countersLayout.setOnClickListener(v -> {
            if(!counters.getCounterState()){
                new Dialog(this, counters);
            }
        });

        /*
        Settings of three counters
         */
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.daysFrame, counters.getFirstCounter());
        ft.replace(R.id.hoursFrame, counters.getSecondCounter());
        ft.replace(R.id.minutesFrame, counters.getThirdCounter());

        ft.commit();


        main.setOnClickListener(v -> {
            if(counters.getCounterState()){
                counters.stopTimer();
                info.setText(R.string.infoCounter);
            }else{
                if(counters.isClear()){
                    counters.startTimer(this, new Counters.onComplete() {
                        @Override
                        public void onComplete() {
                            info.setText(R.string.infoCounter);
                        }
                    });
                    info.setText(R.string.infoCounter2);
                }
            }
        });
    }
}