package ocelot.object;

import ocelot.*;
import java.awt.*;
import java.awt.geom.*;

public class Player extends GameObject {
    
    private Rectangle2D.Double BODY;
    private double POS_X;
    private double POS_Y;
    private double SIZE_X;
    private double SIZE_Y;
    

    public Player(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine) {

        super(iOBJECT_ID, iengine);
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): Creating object...");
        
        NAME = iNAME;
        TYPE = iTYPE;
        
        VISIBLE = true;
        SOLID = true;
        
        POS_X = 100.0;
        POS_Y = 100.0;
        
        SIZE_X = 50.0;
        SIZE_Y = 50.0;
        
        BODY = new Rectangle2D.Double(POS_X, POS_Y, SIZE_X, SIZE_Y);
        
        debug.console(3, NAME + "(" + OBJECT_ID + "): Object created.");

    }
    
    public void draw(Graphics2D g) {
        
        g.setColor(Color.orange);
        g.fill(BODY);
        
    }

}
