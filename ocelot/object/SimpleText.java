package ocelot.object;

import ocelot.*;
import java.awt.*;
import java.awt.geom.*;

/* Standard object to hold text */

public class SimpleText extends GameObject {
    
    private String text;
    private int posX;
    private int posY;
    private int size;
    private Color color;
    private Font font;

    public SimpleText(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine) {
    
        super(iOBJECT_ID, iNAME, iTYPE, iengine);
        
        text = "";
        posX = -1000;
        posY = -1000;
        size = 16;
        color = Color.white;
        font = new Font("default", Font.BOLD, size);
    
    }
    
    public void draw(Graphics2D g) {
        
        g.setColor(color);
        
        font = font.deriveFont(size);
        
        g.setFont(font);
        
        g.drawString(text, posX, posY);
        
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
    
    public void setFont(Font ifont) {
        
        font = ifont;
        
    }
    
    public void setColor(Color icolor) {
        
        color = icolor;
        
    }
    
    

}
