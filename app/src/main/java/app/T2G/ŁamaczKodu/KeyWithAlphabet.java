package app.T2G.ŁamaczKodu;

import com.codepoetics.protonpack.maps.MapStream;

import java.util.HashMap;
import java.util.Map;

/**
 * Key with assigned alphabet
 */

public class KeyWithAlphabet {
    private HashMap<String, String> codeMap = new HashMap<String, String>();
    private Map<String, String> inverseMap;

    public KeyWithAlphabet(){
        initKey();
        initInverseMap();
    }

    public boolean checkDecodeMapContainsKey(char key){
        return codeMap.containsKey(String.valueOf(key));
    }

    public boolean checkEncodeMapContainsKey(char key){
        return inverseMap.containsKey(String.valueOf(key));
    }

    public String getEncodeChar(char key){
        return inverseMap.get(String.valueOf(key));
    }

    public String getDecodeChar(char key){
        return codeMap.get(String.valueOf(key));
    }

    private void initKey(){
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
    }

    /*
    Inverse map to decode words
    */
    private void initInverseMap(){
        inverseMap = MapStream.of(codeMap).inverseMapping().collect();
    }

}
