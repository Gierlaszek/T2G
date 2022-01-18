package app.T2G.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import app.T2G.R;
import app.T2G.utils.Utils;
import app.T2G.utils.SevenSegmentDisplay;

/**
 * View and controller layer combined
 */

public class LCDDisplay extends AppCompatActivity {

    /*
    Variables
     */
    private EditText input;
    private TextView output;
    private TextView console;
    private SevenSegmentDisplay display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcddisplay);

        input = findViewById(R.id.input);
        input.setFilters(new InputFilter[]{ createFilter() });

        output = findViewById(R.id.output);
        console = findViewById(R.id.consoleInfo);
        console.setVisibility(View.INVISIBLE);

        display = new SevenSegmentDisplay();

        LinearLayout main = findViewById(R.id.mainLCD);
        main.setOnClickListener(v -> {
            display();
            Utils.hideSoftKeyboard(this);
        });

        input.setOnEditorActionListener((textView, i, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                display();
                Utils.hideSoftKeyboard(this);
            }
            return false;
        });
    }

    private InputFilter createFilter(){
        return (charSequence, start, end, spanned, dstart, dend) -> {
            for(int i = start; i < end; i++){
                if(charSequence.charAt(i) == ',' || charSequence.charAt(i) == '.' || charSequence.charAt(i) == '-' || charSequence.charAt(i) == ' '){
                    return "";
                }
            }
            return null;
        };
    }

    private void display(){
        console.setVisibility(View.VISIBLE);
        String text = input.getText().toString();
        if(!text.equals("")){
            StringBuilder outputStringFirstLine = new StringBuilder();
            StringBuilder outputStringSecondLine = new StringBuilder();
            StringBuilder outputStringThirdLine = new StringBuilder();
            for(char c: text.toCharArray()) {
                int x = Character.getNumericValue(c);
                outputStringFirstLine.append(display.getSegment(x, 0));
                outputStringSecondLine.append(display.getSegment(x, 1));
                outputStringThirdLine.append(display.getSegment(x, 2));
            }
            output.setText(getString(R.string.lcd_output_text,
                    outputStringFirstLine.toString(),
                    outputStringSecondLine.toString(),
                    outputStringThirdLine.toString()));
            console.setText(R.string.lcd_console_correct);
            /*
            Display text in console
             */
            System.out.println(getString(R.string.lcd_output_text,
                    outputStringFirstLine.toString(),
                    outputStringSecondLine.toString(),
                    outputStringThirdLine.toString()));
        }else{
            output.setText("");
            console.setText(R.string.lcd_console_wrong);
        }
    }
}