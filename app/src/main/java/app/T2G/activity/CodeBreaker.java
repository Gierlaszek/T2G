package app.T2G.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import app.T2G.R;
import app.T2G.utils.Utils;
import app.T2G.utils.KeyWithAlphabet;

/**
 * View and controller layer combined
 */
public class CodeBreaker extends AppCompatActivity {

    /*
    Variables
     */
    private Button put;
    private EditText input;
    private TextView output;
    private TextView info;
    private SwitchMaterial mode;
    private KeyWithAlphabet key;
    private LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codebreaker);

        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        info = findViewById(R.id.consoleInfo);
        put = findViewById(R.id.put);
        main = findViewById(R.id.mainCode);

        init();

        key = new KeyWithAlphabet();
        display();
    }

    private void init(){
        input.setText(R.string.exampleCode);
        info.setText(R.string.infoCode);

        put.setOnClickListener(v -> {
            input.setText(output.getText());
            mode.setChecked(!mode.isChecked());
            display();
        });

        main.setOnClickListener(v -> {
            display();
            Utils.hideSoftKeyboard(this);
        });

        /*
        Set encode or decode mode
         */
        mode = findViewById(R.id.mode);
        mode.setChecked(true);

        input.setOnEditorActionListener((textView, i, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                display();
                Utils.hideSoftKeyboard(this);
            }
            return false;
        });
    }

    private void display(){
        StringBuilder outputString = new StringBuilder();
        String text = input.getText().toString();
        boolean decode = mode.isChecked();

        for(char c: text.toCharArray()){
            if(key.checkDecodeMapContainsKey(c) || key.checkEncodeMapContainsKey(c)){
                outputString.append(decode ? key.getDecodeChar(c) : key.getEncodeChar(c));
            }else{
                outputString.append(c);
            }
        }
        output.setText(outputString.toString());
    }
}