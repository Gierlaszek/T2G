package app.T2G.WyświetlaczLCD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

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

    ArrayList<ArrayList<String>> outputExample = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcddisplay);

        input = findViewById(R.id.input);
        input.setHint("Input number");

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int start, int end, Spanned spanned, int dstart, int dend) {
                for(int i = start; i < end; i++){
                    if(charSequence.charAt(i) == ',' || charSequence.charAt(i) == '.' || charSequence.charAt(i) == '-' || charSequence.charAt(i) == ' '){
                        return "";
                    }
                }
                return null;
            }
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

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    display();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(main.getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    private void display(){
        console.setVisibility(View.VISIBLE);
        text = input.getText().toString();
        if(!text.equals("")){
            String outputStringFirstLine = "";
            String outputStringSecondLine = "";
            String outputStringThirdLine = "";
            for(int i = 0; i < text.length(); i++) {
                int x = Character.getNumericValue(text.charAt(i));
                outputStringFirstLine += display.getSegment(x, 0);
                outputStringSecondLine += display.getSegment(x, 1);
                outputStringThirdLine += display.getSegment(x, 2);
            }
            output.setText(outputStringFirstLine + "\n" + outputStringSecondLine + "\n" + outputStringThirdLine);
            console.setText(R.string.lcd_console_correct);
            System.out.println(outputStringFirstLine + "\n" + outputStringSecondLine + "\n" + outputStringThirdLine);
        }else{
            output.setText("");
            console.setText(R.string.lcd_console_wrong);
        }
    }
}