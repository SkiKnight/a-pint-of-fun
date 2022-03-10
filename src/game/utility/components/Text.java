package game.utility.components;

import java.awt.Font;
import java.awt.Color;

public class Text extends Font{

    public int x,y;
    public String text;
    public Color color;
    public Text(String name, int style, int size) {
        super(name, style, size);
        text = "";
        x=y=0;
        color = new Color(255,255,255); //White
    }    
}
