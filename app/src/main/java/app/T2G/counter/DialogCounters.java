package app.T2G.counter;

import android.app.Dialog;
import android.content.Context;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.T2G.R;

public class DialogCounters {

    private NumberPicker daysPicker;
    private NumberPicker hoursPicker;
    private NumberPicker minutesPicker;
    private NumberPicker secondsPicker;
    private final List<NumberPicker> pickerList = new ArrayList<>();

    public DialogCounters(Context context, Counters counters){
        createDialog(context, counters);
    }

    private void init(Dialog dialog){
        daysPicker = dialog.findViewById(R.id.daysPicker);
        hoursPicker = dialog.findViewById(R.id.hoursPicker);
        minutesPicker = dialog.findViewById(R.id.minutesPicker);
        secondsPicker = dialog.findViewById(R.id.secondsPicker);

        daysPicker.setMaxValue(30);
        daysPicker.setMinValue(0);

        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(23);

        minutesPicker.setMaxValue(59);
        minutesPicker.setMinValue(0);

        secondsPicker.setMaxValue(59);
        secondsPicker.setMinValue(0);

         /*
        day shift causes the hours shift etc
         */
        hoursPicker.setOnValueChangedListener((numberPicker, i, i1) -> onValueChangeListener(i, i1, 23, 23,2));

        minutesPicker.setOnValueChangedListener((numberPicker, i, i1) -> onValueChangeListener(i, i1, 59, 23, 1));

        secondsPicker.setOnValueChangedListener((numberPicker, i, i1) -> onValueChangeListener(i, i1, 59, 59, 0));

        pickerList.add(minutesPicker);
        pickerList.add(hoursPicker);
        pickerList.add(daysPicker);
    }


    private void increaseValue(int positionOfPicker, int maxValue){
        NumberPicker currentPicker = pickerList.get(positionOfPicker);
        if(currentPicker.getValue() == maxValue){
            try{
                increaseValue(positionOfPicker + 1, 23);
            }catch (ArrayIndexOutOfBoundsException ignore){

            }
        }
        currentPicker.setValue(currentPicker.getValue() + 1);
    }


    private void decreaseValue(int positionOfPicker){
        if(positionOfPicker < 3){
            NumberPicker currentPicker = pickerList.get(positionOfPicker);
            if(currentPicker.getValue() == 0){
                try{
                    decreaseValue(positionOfPicker + 1);
                }catch (ArrayIndexOutOfBoundsException ignore){

                }
            }
            currentPicker.setValue(currentPicker.getValue() - 1);
            if(currentPicker.getValue() < 0){ //for change days picker
                currentPicker.setValue(0);
            }
        }
    }

    private void onValueChangeListener(int i, int i1, int maxValue, int maxValueOfNextPicker, int positionOfPicker){
        if(i1 == 0 && i == maxValue){
            increaseValue(positionOfPicker, maxValueOfNextPicker);
        }else if(i == 0 && i1 == maxValue){
            decreaseValue(positionOfPicker);
        }
    }

    /*
    Set the time on the counters
    */
    private void changePickers(Counters counters){
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
    }

    private void createDialog(Context context, Counters counters){
        final Dialog dialog = new android.app.Dialog(context);
        dialog.setContentView(R.layout.dialog_counter);
        init(dialog);
        changePickers(counters);

        TextView accept = dialog.findViewById(R.id.accept);
        accept.setOnClickListener(v -> {
            counters.setNewValue(daysPicker.getValue(), hoursPicker.getValue(), minutesPicker.getValue(), secondsPicker.getValue());
            dialog.dismiss();
        });

        TextView discard = dialog.findViewById(R.id.discard);
        discard.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
