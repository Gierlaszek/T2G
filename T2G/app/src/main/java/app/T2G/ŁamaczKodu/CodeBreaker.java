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


public class CodeBreaker extends AppCompatActivity {

    private EditText input;
    private TextView output;
    private TextView info;
    private Switch mode;
    private String text;
    private LinearLayout main;
    private Button put;

    private HashMap<String, String> codeMap = new HashMap<String, String>();
    private Map<String, String> inverseMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codebreaker);
        codeMap.put("!", "a");
        codeMap.put(")", "b");
        codeMap.put("\"", "c");
        codeMap.put("(", "d");
        codeMap.put("£", "e");
        codeMap.put("*", "f");
        codeMap.put("%", "g");
        codeMap.put("&", "h");
        codeMap.put(">", "i");
        codeMap.put("<", "j");
        codeMap.put("@", "k");
        codeMap.put("a", "l");
        codeMap.put("b", "m");
        codeMap.put("c", "n");
        codeMap.put("d", "o");
        codeMap.put("e", "p");
        codeMap.put("f", "q");
        codeMap.put("g", "r");
        codeMap.put("h", "s");
        codeMap.put("i", "t");
        codeMap.put("j", "u");
        codeMap.put("k", "v");
        codeMap.put("l", "w");
        codeMap.put("m", "x");
        codeMap.put("n", "y");
        codeMap.put("o", "z");

        inverseMap = MapStream.of(codeMap).inverseMapping().collect();


        put = findViewById(R.id.put);
        input = findViewById(R.id.input);
        input.setText(R.string.exampleCode);
        output = findViewById(R.id.output);
        info = findViewById(R.id.consoleInfo);
        info.setText(R.string.infoCode);

        mode = findViewById(R.id.mode);
        mode.setChecked(true);
        main = findViewById(R.id.mainCode);

        put.setOnClickListener(v -> {
            input.setText(output.getText());
            mode.setChecked(!mode.isChecked());
            display();
        });

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
                if(codeMap.containsKey(String.valueOf(text.charAt(i)))){
                    outputString += codeMap.get(String.valueOf(text.charAt(i)));
                }else{
                    outputString += text.charAt(i);
                }
            }
        }else{
            System.out.println(inverseMap);
            for(int i = 0; i < text.length(); i++){
                if(inverseMap.containsKey(String.valueOf(text.charAt(i)))){
                    System.out.println("true");
                    outputString += inverseMap.get(String.valueOf(text.charAt(i)));
                }else{
                    outputString += text.charAt(i);
                }
            }
        }
        output.setText(outputString);
    }
}