package app.T2G.Licznik;

import android.content.Context;
import android.widget.NumberPicker;
import android.widget.TextView;

import app.T2G.R;

public class Dialog {

    public Dialog(Context context, Counters counters){
        createDialog(context, counters);
    }

    private void createDialog(Context context, Counters counters){
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.setContentView(R.layout.dialog_counter);
        NumberPicker daysPicker = dialog.findViewById(R.id.daysPicker);
        NumberPicker hoursPicker = dialog.findViewById(R.id.hoursPicker);
        NumberPicker minutesPicker = dialog.findViewById(R.id.minutesPicker);
        NumberPicker secondsPicker = dialog.findViewById(R.id.secondsPicker);


        daysPicker.setMaxValue(30);
        daysPicker.setMinValue(0);

        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(23);
        /*
        day shift causes the hours shift etc
         */
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

        /*
        Set the time on the counters
        */

        if(counters.getFirstCounter().getDescribe().equals("days")){
            daysPicker.setValue(counters.getFirstCounter().getValue());
        }else{
            hoursPicker.setValue(counters.getFirstCounter().getValue());
        }

        if(counters.getSecondCounter().getDescribe().equals("hours")){
            hoursPicker.setValue(counters.getSecondCounter().getValue());
        }else{
            minutesPicker.setValue(counters.getSecondCounter().getValue());
        }

        if(counters.getThirdCounter().getDescribe().equals("minutes")){
            minutesPicker.setValue(counters.getThirdCounter().getValue());
            secondsPicker.setValue(counters.getExtraSeconds());
        }else{
            secondsPicker.setValue(counters.getThirdCounter().getValue());
        }


        TextView accept = dialog.findViewById(R.id.accept);
        accept.setOnClickListener(v -> {
            counters.setNewValue(daysPicker.getValue(), hoursPicker.getValue(), minutesPicker.getValue(), secondsPicker.getValue());
            dialog.dismiss();
        });

        TextView discard = dialog.findViewById(R.id.discard);
        discard.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
