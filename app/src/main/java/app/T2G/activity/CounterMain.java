package app.T2G.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.T2G.R;
import app.T2G.counter.Counters;
import app.T2G.counter.DialogCounters;

public class CounterMain extends AppCompatActivity {

    /*
    Variables
     */
    private Counters counters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_main);
        counters = new Counters();
        setFragments();

        LinearLayout countersLayout = findViewById(R.id.counters);
        countersLayout.setOnClickListener(v -> {
            if(!counters.getCounterState()){
                new DialogCounters(this, counters);
            }
        });

        LinearLayout main = findViewById(R.id.mainCounter);
        TextView info = findViewById(R.id.info);
        main.setOnClickListener(v -> {
            if(counters.getCounterState()){
                counters.stopTimer();
                info.setText(R.string.infoCounter);
            }else{
                if(counters.isClear()){
                    counters.startTimer(this, () -> info.setText(R.string.infoCounter));
                    info.setText(R.string.infoCounter2);
                }
            }
        });
    }

    /*
    Settings of three counters
    */
    private void setFragments(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.daysFrame, counters.getFirstCounter());
        ft.replace(R.id.hoursFrame, counters.getSecondCounter());
        ft.replace(R.id.minutesFrame, counters.getThirdCounter());
        ft.commit();
    }
}