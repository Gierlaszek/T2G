package app.T2G.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.T2G.R;

/**
 * Support for the single counter
 */

public class SingleCounter extends Fragment {

    /*
    Variables
     */
    private TextView describeCounter;
    private TextView counter;
    private String describeCounterString = "";
    private int maxValue = 0;
    private int currentValue = 0;

    public SingleCounter(){

    }

    public SingleCounter(String describeCounter, int startValue) {
        this.describeCounterString = describeCounter;
        this.maxValue = startValue;
        currentValue = startValue;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_single_counter, container, false);

        describeCounter = v.findViewById(R.id.tv);
        describeCounter.setText(describeCounterString);
        counter = v.findViewById(R.id.counter);
        counter.setText(String.valueOf(maxValue));

        return v;
    }

    public void changeValue(int newValue){
        currentValue = newValue;
        counter.setText(String.valueOf(newValue));
    }

    public boolean isStopped(){
        return getValue() == 0;
    }

    public int getValue() { return currentValue; }

    public void decreaseValue(){
        currentValue--;
        counter.setText(String.valueOf(currentValue));
    }

    public String getDescribe(){ return describeCounterString; }

    public void changeDescribe(String newDescribe){
        describeCounterString = newDescribe;
        describeCounter.setText(newDescribe);
    }
}