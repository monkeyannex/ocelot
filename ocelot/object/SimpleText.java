package ocelot.object;

import ocelot.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/* Standard object to hold text */

public class SimpleText extends GameObject {
    
    public String text;
    public double posX;
    public double posY;
    public int size;
    public Font font;
    
    public float TEXT_ALPHA;
    public Color TEXT_COLOR;
    public float P_COLOR_RED = (float)(255.0 / 255.0);
    public float P_COLOR_GREEN = (float)(255.0 / 255.0);
    public float P_COLOR_BLUE = (float)(255.0 / 255.0);
    
    private String fontStandard = "Expressway Rg";
    private String fontStylised = "Zephyrean BRK";
    
    public String fontName;

    public SimpleText(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine) {
    
        super(iOBJECT_ID, iNAME, iTYPE, iengine);
        
        VISIBLE = true;
        SOLID = false;
        
        text = "";
        posX = -1000;
        posY = -1000;
        size = 20;
        
        TEXT_ALPHA = 1;
        TEXT_COLOR = new Color(P_COLOR_RED,P_COLOR_GREEN,P_COLOR_BLUE,TEXT_ALPHA);
        
        fontName = fontStandard;
        
        font = new Font(fontName, Font.PLAIN, size);
    
    }
    
    public void draw(Graphics2D g) {
        
        TEXT_COLOR = new Color(P_COLOR_RED,P_COLOR_GREEN,P_COLOR_BLUE,TEXT_ALPHA);
        
        g.setColor(TEXT_COLOR);
        
        font = new Font(fontName, Font.PLAIN, size);
        
        font.deriveFont((float)size);
        
        g.setFont(font);
        
        g.drawString(text, (int)posX, (int)posY);
        
    }
    
    public Font getFont() {
            
            return font;
        
    }
    
    public void setText(String itext) {
        
        text = itext;
        
    }
    
    public void setPosX(int iposX) {
        
        posX = iposX;
        
    }
    
    public void setPosY(int iposY) {
        
        posY = iposY;
        
    }
    
    public void setSize(int isize) {
        
        size = isize;
        
    }
    
    public void setFont(String style) {
        
        if(style.equals("stylised")) fontName = fontStylised;
        if(style.equals("standard")) fontName = fontStandard;
        
    }
    
    public void setColor(Color icolor) {
        
        TEXT_COLOR = icolor;
        
    }
    
    

}
