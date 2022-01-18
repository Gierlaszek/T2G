package app.T2G.WyświetlaczLCD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import app.T2G.R;

/**
 * View and controller layer combined
 */

public class LCDDisplay extends AppCompatActivity {

    /*
    Variables
     */
    EditText input;
    TextView output;
    TextView console;
    LinearLayout main;
    String text;
    SevenSegmentDisplay display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcddisplay);

        input = findViewById(R.id.input);
        input.setHint("Input number");

        InputFilter filter = (charSequence, start, end, spanned, dstart, dend) -> {
            for(int i = start; i < end; i++){
                if(charSequence.charAt(i) == ',' || charSequence.charAt(i) == '.' || charSequence.charAt(i) == '-' || charSequence.charAt(i) == ' '){
                    return "";
                }
            }
            return null;
        };

        input.setFilters(new InputFilter[]{ filter });

        output = findViewById(R.id.output);
        console = findViewById(R.id.consoleInfo);

        console.setVisibility(View.INVISIBLE);

        display = new SevenSegmentDisplay();

        main = findViewById(R.id.mainLCD);
        main.setOnClickListener(v -> {
            display();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(main.getWindowToken(), 0);
        });

        input.setOnEditorActionListener((textView, i, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                display();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(main.getWindowToken(), 0);
            }
            return false;
        });
    }

    private void display(){
        console.setVisibility(View.VISIBLE);
        text = input.getText().toString();
        if(!text.equals("")){
            StringBuilder outputStringFirstLine = new StringBuilder();
            StringBuilder outputStringSecondLine = new StringBuilder();
            StringBuilder outputStringThirdLine = new StringBuilder();
            for(int i = 0; i < text.length(); i++) {
                int x = Character.getNumericValue(text.charAt(i));
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