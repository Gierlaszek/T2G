package app.T2G.ŁamaczKodu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.codepoetics.protonpack.maps.MapStream;

import java.util.HashMap;
import java.util.Map;

import app.T2G.R;

/**
 * View and controller layer combined
 */
public class CodeBreaker extends AppCompatActivity {

    /*
    Variables
     */
    private EditText input;
    private TextView output;
    private TextView info;
    private Switch mode;
    private String text;
    private LinearLayout main;
    private Button put;

    private KeyWithAlphabet key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codebreaker);

        key = new KeyWithAlphabet();

        put = findViewById(R.id.put);
        input = findViewById(R.id.input);
        input.setText(R.string.exampleCode);
        output = findViewById(R.id.output);
        info = findViewById(R.id.consoleInfo);
        info.setText(R.string.infoCode);

        /*
        Set encode or decode mode
         */
        mode = findViewById(R.id.mode);
        mode.setChecked(true);

        main = findViewById(R.id.mainCode);

        put.setOnClickListener(v -> {
            input.setText(output.getText());
            mode.setChecked(!mode.isChecked());
            display();
        });

        /*
        Hide keyboard
         */
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

        display();
    }

    private void display(){
        String outputString = "";
        text = input.getText().toString();
        boolean decode = mode.isChecked();

        if(decode){
            for(int i = 0; i < text.length(); i++){
                if(key.checkDecodeMapContainsKey(text.charAt(i))){
                    outputString += key.getDecodeChar(text.charAt(i));
                }else{
                    outputString += text.charAt(i);
                }
            }
        }else{
            for(int i = 0; i < text.length(); i++){
                if(key.checkEncodeMapContainsKey(text.charAt(i))){
                    outputString += key.getEncodeChar(text.charAt(i));
                }else{
                    outputString += text.charAt(i);
                }
            }
        }
        output.setText(outputString);
    }
}