package app.T2G.WyświetlaczLCD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.T2G.R;


public class LCDDisplay extends AppCompatActivity {

    EditText input;
    TextView output;
    TextView console;
    LinearLayout main;
    String text;

    ArrayList<ArrayList<String>> outputExample = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcddisplay);

        input = findViewById(R.id.input);
        input.setHint("Input number");
        output = findViewById(R.id.output);
        console = findViewById(R.id.consoleInfo);

        outputExample.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" | | ");
                add(" |_| ");
            }
        });//0


        outputExample.add(new ArrayList<String>(){
            {
                add("    ");
                add("  | ");
                add("  | ");
            }
        }); //1


        outputExample.add(new ArrayList<String>(){
            {
                add("  _  ");
                add("  _| ");
                add(" |_  ");
            }
        }); //2


        outputExample.add(new ArrayList<String>(){
            {
                add(" _  ");
                add(" _| ");
                add(" _| ");
            }
        }); //3


        outputExample.add(new ArrayList<String>(){
            {
                add("     ");
                add(" |_| ");
                add("   | ");
            }
        }); //4


        outputExample.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" |_  ");
                add("  _| ");
            }
        }); //5


        outputExample.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" |_  ");
                add(" |_| ");
            }
        }); //6


        outputExample.add(new ArrayList<String>(){
            {
                add(" _  ");
                add("  | ");
                add("  | ");
            }
        }); //7


        outputExample.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" |_| ");
                add(" |_| ");
            }
        }); //8


        outputExample.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" |_| ");
                add("  _| ");
            }
        }); //9

        console.setVisibility(View.INVISIBLE);

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
                outputStringFirstLine += outputExample.get(x).get(0);
                outputStringSecondLine += outputExample.get(x).get(1);
                outputStringThirdLine += outputExample.get(x).get(2);
            }
            output.setText(outputStringFirstLine + "\n" + outputStringSecondLine + "\n" + outputStringThirdLine);
            console.setText(R.string.lcd_console_correct);
            System.out.println(outputStringFirstLine + "\n" + outputStringSecondLine + "\n" + outputStringThirdLine);
        }else{
            output.setText("");
            System.out.println("Input number!!");
            console.setText(R.string.lcd_console_wrong);
        }
    }
}