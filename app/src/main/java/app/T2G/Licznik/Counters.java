package app.T2G.Licznik;

import android.app.Activity;
import java.util.Timer;
import java.util.TimerTask;

public class Counters {

    private SingleCounter firstCounter;
    private SingleCounter secondCounter;
    private SingleCounter thirdCounter;

    private Timer timer;
    private boolean extraCounter = false;
    private int extraSeconds = 59;
    private boolean counterState;

    public Counters(){
        counterState = false;
        initCounters();
    }

    private void initCounters(){
        firstCounter = new SingleCounter("days", 1);
        secondCounter = new SingleCounter("hours", 2);
        thirdCounter = new SingleCounter("minutes", 20);
    }

    public boolean getCounterState(){
        return counterState;
    }

    public int getExtraSeconds(){
        return extraSeconds;
    }

    public SingleCounter getFirstCounter(){
        return firstCounter ;
    }

    public SingleCounter getSecondCounter(){
        return secondCounter ;
    }

    public SingleCounter getThirdCounter(){
        return thirdCounter ;
    }

    public void startTimer(Activity activity, onComplete complete){
        counterState = true;
        startCount(activity, complete);
    }

    public boolean isClear(){
        return (extraSeconds != 0 || thirdCounter.getValue() != 0
                || secondCounter.getValue() != 0 || firstCounter.getValue() !=0);
    }

    public void stopTimer(){
        timer.cancel();
        counterState = false;
    }

    private void startCount(Activity activity, onComplete complete){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    changeCountersName();
                    if(extraCounter){
                        if(extraSeconds > 0){
                            extraSeconds--;
                        }else if(extraSeconds == 0){
                            extraSeconds = 59;
                            if(thirdCounter.getValue() > 0){ //minutes
                                thirdCounter.decreaseValue();
                            }else{
                                if(secondCounter.getValue() > 0){ //hours
                                    thirdCounter.changeValue(59); //minutes
                                    secondCounter.decreaseValue();
                                }else if(firstCounter.getValue() > 0){ //days
                                    firstCounter.decreaseValue();
                                    changeCountersName();
                                }
                            }
                        }
                    }else{
                        if(thirdCounter.getValue() == 0){ //seconds
                            if(secondCounter.getValue() > 0){  //minutes
                                thirdCounter.changeValue(59); //seconds
                                secondCounter.decreaseValue();
                            }else if(firstCounter.getValue() > 0){ //hours
                                secondCounter.changeValue(59); //minutes
                                thirdCounter.changeValue(59);
                                firstCounter.decreaseValue();
                            }
                        }else{
                            thirdCounter.decreaseValue(); //seconds
                        }
                    }
                    stopCounters(complete);
                });
            }
        }, 1000, 1000);
    }

    /*
    Change counters from days, hours and minutes to hours minutes and seconds
    if days are displayed, add additional seconds which are counted in the background
     */
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

    private void stopCounters(onComplete complete) {
        if (firstCounter.getDescribe().equals("days")) {
            if (firstCounter.getValue() == 0 && secondCounter.getValue() == 0 && thirdCounter.getValue() == 0 && extraSeconds == 0) {
                stopTimer();
                complete.complete();
            }
        } else {
            if (firstCounter.getValue() == 0 && secondCounter.getValue() == 0 && thirdCounter.getValue() == 0) {
                stopTimer();
                extraSeconds = 0;
                complete.complete();
            }
        }
    }

    /*
   Setting new values on the counters
    */
    public void setNewValue(int daysValue, int hoursValue, int minutesValue, int secondsValue){
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

    public interface onComplete{
        void complete();
    }
}
