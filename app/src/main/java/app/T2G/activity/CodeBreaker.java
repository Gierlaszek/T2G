package app.T2G.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import app.T2G.R;
import app.T2G.utils.HideKeyboard;
import app.T2G.utils.KeyWithAlphabet;

/**
 * View and controller layer combined
 */
public class CodeBreaker extends AppCompatActivity {

    /*
    Variables
     */
    private EditText input;
    private TextView output;
    private SwitchMaterial mode;
    private KeyWithAlphabet key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codebreaker);

        key = new KeyWithAlphabet();

        Button put = findViewById(R.id.put);
        input = findViewById(R.id.input);
        input.setText(R.string.exampleCode);
        output = findViewById(R.id.output);
        TextView info = findViewById(R.id.consoleInfo);
        info.setText(R.string.infoCode);

        /*
        Set encode or decode mode
         */
        mode = findViewById(R.id.mode);
        mode.setChecked(true);

        LinearLayout main = findViewById(R.id.mainCode);

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
            HideKeyboard.hideSoftKeyboard(this);
        });

        input.setOnEditorActionListener((textView, i, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                display();
                HideKeyboard.hideSoftKeyboard(this);
            }
            return false;
        });

        display();
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