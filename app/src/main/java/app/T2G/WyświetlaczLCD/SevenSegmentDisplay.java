package app.T2G.WyświetlaczLCD;

import java.util.ArrayList;

public class SevenSegmentDisplay {
    private final ArrayList<ArrayList<String>> sevenSegments = new ArrayList<>();

    public SevenSegmentDisplay(){
        initDisplay();
    }

    public String getSegment(int i, int line){
        return sevenSegments.get(i).get(line);
    }

    private void initDisplay(){
        sevenSegments.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" | | ");
                add(" |_| ");
            }
        });//0


        sevenSegments.add(new ArrayList<String>(){
            {
                add("    ");
                add("  | ");
                add("  | ");
            }
        }); //1


        sevenSegments.add(new ArrayList<String>(){
            {
                add("  _  ");
                add("  _| ");
                add(" |_  ");
            }
        }); //2


        sevenSegments.add(new ArrayList<String>(){
            {
                add(" _  ");
                add(" _| ");
                add(" _| ");
            }
        }); //3


        sevenSegments.add(new ArrayList<String>(){
            {
                add("     ");
                add(" |_| ");
                add("   | ");
            }
        }); //4


        sevenSegments.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" |_  ");
                add("  _| ");
            }
        }); //5


        sevenSegments.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" |_  ");
                add(" |_| ");
            }
        }); //6


        sevenSegments.add(new ArrayList<String>(){
            {
                add(" _  ");
                add("  | ");
                add("  | ");
            }
        }); //7


        sevenSegments.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" |_| ");
                add(" |_| ");
            }
        }); //8


        sevenSegments.add(new ArrayList<String>(){
            {
                add("  _  ");
                add(" |_| ");
                add("  _| ");
            }
        }); //9
    }

}
