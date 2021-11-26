package app.T2G.Licznik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import app.T2G.R;

public class CounterMain extends AppCompatActivity {

    private SingleCounter firstCounter;
    private SingleCounter secondCounter;
    private SingleCounter thirdCounter;

    boolean extraCounter = false;
    private int extraSeconds = 59;
    private TextView info;
    private LinearLayout main;
    private LinearLayout counters;
    private boolean counterStart;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_main);

        counterStart = false;

        main = findViewById(R.id.mainCounter);
        info = findViewById(R.id.info);
        counters = findViewById(R.id.counters);
        counters.setOnClickListener(v -> {
            if(!counterStart){
                createDialog();
            }
        });

        firstCounter = new SingleCounter("days", 1);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.daysFrame, firstCounter);

        secondCounter = new SingleCounter("hours", 2);
        ft.replace(R.id.hoursFrame, secondCounter);

        thirdCounter = new SingleCounter("minutes", 20);
        ft.replace(R.id.minutesFrame, thirdCounter);

        ft.commit();


        main.setOnClickListener(v -> {
            if(counterStart){
                timer.cancel();
                counterStart = false;
                info.setText(R.string.infoCounter);
            }else{
                counterStart = true;
                startCount();
                info.setText(R.string.infoCounter2);
            }
        });
    }

    private void createDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Set new value");
        dialog.setContentView(R.layout.dialog_counter);
        NumberPicker daysPicker = dialog.findViewById(R.id.daysPicker);
        NumberPicker hoursPicker = dialog.findViewById(R.id.hoursPicker);
        NumberPicker minutesPicker = dialog.findViewById(R.id.minutesPicker);
        NumberPicker secondsPicker = dialog.findViewById(R.id.secondsPicker);


        daysPicker.setMaxValue(30);
        daysPicker.setMinValue(0);

        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(23);
        hoursPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if(i1 == 0 && i == 23){
                    daysPicker.setValue(daysPicker.getValue() + 1);
                }else if(i == 0 && i1 == 23){
                    if(daysPicker.getValue() >= 1){
                        daysPicker.setValue(daysPicker.getValue() - 1);
                    }
                }
            }
        });

        minutesPicker.setMaxValue(59);
        minutesPicker.setMinValue(0);
        minutesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if(i1 == 0 && i == 59){
                    if(hoursPicker.getValue() == 23){
                        daysPicker.setValue(daysPicker.getValue() + 1);
                    }
                    hoursPicker.setValue(hoursPicker.getValue() + 1);
                }else if(i == 0 && i1 == 59 ){
                    if(hoursPicker.getValue() == 0){
                        if(daysPicker.getValue() >= 1){
                            daysPicker.setValue(daysPicker.getValue() - 1);
                        }
                    }
                    hoursPicker.setValue(hoursPicker.getValue()-1);
                }
            }
        });

        secondsPicker.setMaxValue(59);
        secondsPicker.setMinValue(0);
        secondsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if(i1 == 0 && i == 59){
                    if(minutesPicker.getValue() == 59){
                        if(hoursPicker.getValue() == 23){
                            daysPicker.setValue(daysPicker.getValue() + 1);
                        }
                        hoursPicker.setValue(hoursPicker.getValue() + 1);
                    }
                    minutesPicker.setValue(minutesPicker.getValue() + 1);
                }else if(i == 0 && i1 == 59){
                    if(minutesPicker.getValue() == 0) {
                        if (hoursPicker.getValue() == 0) {
                            if (daysPicker.getValue() >= 1) {
                                daysPicker.setValue(daysPicker.getValue() - 1);
                            }
                        }
                        hoursPicker.setValue(hoursPicker.getValue()-1);
                    }
                    minutesPicker.setValue(minutesPicker.getValue() - 1);
                }
            }
        });

        if(firstCounter.getDescribe().equals("days")){
            daysPicker.setValue(firstCounter.getValue());
        }else{
            hoursPicker.setValue(firstCounter.getValue());
        }

        if(secondCounter.getDescribe().equals("hours")){
            hoursPicker.setValue(secondCounter.getValue());
        }else{
            minutesPicker.setValue(secondCounter.getValue());
        }

        if(thirdCounter.getDescribe().equals("minutes")){
            minutesPicker.setValue(thirdCounter.getValue());
            secondsPicker.setValue(extraSeconds);
        }else{
            secondsPicker.setValue(thirdCounter.getValue());
        }


        TextView accept = dialog.findViewById(R.id.accept);
        accept.setOnClickListener(v -> {
            setNewValue(daysPicker.getValue(), hoursPicker.getValue(), minutesPicker.getValue(), secondsPicker.getValue());
            dialog.dismiss();
        });

        TextView discard = dialog.findViewById(R.id.discard);
        discard.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setNewValue(int daysValue, int hoursValue, int minutesValue, int secondsValue){
        if(daysValue > 0){
            firstCounter.changeValue(daysValue);
            firstCounter.changeDescribe("days");

            secondCounter.changeValue(hoursValue);
            secondCounter.changeDescribe("hours");

            thirdCounter.changeValue(minutesValue);
            thirdCounter.changeDescribe("minutes");
            extraSeconds = secondsValue;
        }else{
            firstCounter.changeValue(hoursValue);
            firstCounter.changeDescribe("hours");

            secondCounter.changeValue(minutesValue);
            secondCounter.changeDescribe("minutes");

            thirdCounter.changeValue(secondsValue);
            thirdCounter.changeDescribe("seconds");
        }
    }

    private void startCount(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeCountersName();
                        if(extraCounter){
                            if(extraSeconds > 0){
                                extraSeconds--;
                            }
                            if(extraSeconds == 0){
                                extraSeconds = 59;
                                if(thirdCounter.getValue() > 0){ //minuta
                                    thirdCounter.decreaseValue();
                                }else{
                                    if(secondCounter.getValue() > 0){ //godzina
                                        thirdCounter.changeValue(59);
                                        secondCounter.decreaseValue();
                                    }else{
                                        if(firstCounter.getValue() > 0){
                                            secondCounter.changeValue(23);
                                            firstCounter.decreaseValue();
                                        }
                                    }
                                }
                            }
                        }else{
                            thirdCounter.decreaseValue(); //sekunda
                            if(thirdCounter.getValue() == 0){
                                if(secondCounter.getValue() > 0){
                                    thirdCounter.changeValue(59);
                                    secondCounter.decreaseValue(); //minuta
                                }else{
                                    if(firstCounter.getValue() > 0){
                                        secondCounter.changeValue(59); //minuta
                                        firstCounter.decreaseValue(); //godzina
                                    }
                                }
                            }
                        }
                        stopCounters();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void changeCountersName(){
        if(firstCounter.getDescribe().equals("days")){
            if(firstCounter.getValue() == 0){
                firstCounter.changeValue(23);
                firstCounter.changeDescribe("hours");

                secondCounter.changeValue(59);
                secondCounter.changeDescribe("minutes");

                thirdCounter.changeValue(59);
                thirdCounter.changeDescribe("seconds");
            }else{
                extraCounter = true;
            }
        }else{
            extraCounter = false;
        }
    }

    private void stopCounters(){
        if(firstCounter.getDescribe().equals("days")){
            if(firstCounter.getValue() == 0 && secondCounter.getValue() == 0 && thirdCounter.getValue() ==0 && extraSeconds == 0){
                timer.cancel();
                counterStart = false;
                info.setText(R.string.infoCounter);
            }
        }else{
            if(firstCounter.getValue() == 0 && secondCounter.getValue() == 0 && thirdCounter.getValue() ==0){
                timer.cancel();
                counterStart = false;
                info.setText(R.string.infoCounter);
            }
        }

    }
}