package ocelot.object.hud;

import ocelot.*;
import ocelot.object.*;
import java.awt.*;
import java.awt.geom.*;

public class PlayerHUD extends GameObject{
    
    private Player player;
    
    private Rectangle2D.Double HB_OUTLINE;
    private Rectangle2D.Double HB_FILL;
    
    private double HB_POS_X;
    private double HB_POS_Y;
    
    private double HB_OL_WIDTH;
    
    private double HB_HEIGHT;
    private double HB_WIDTH;
    
    private float HB_OUTLINE_THICKNESS = 3;
    
    public PlayerHUD(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine, Player iplayer) {

        super(iOBJECT_ID, iengine);
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): Creating object...");
        
        NAME = iNAME;
        TYPE = iTYPE;
        
        VISIBLE = true;
        SOLID = false;
        
        player = iplayer;
        
        HB_OL_WIDTH = 100.0;
        
        HB_HEIGHT = 15.0;
        HB_WIDTH = HB_OL_WIDTH;
        
        HB_POS_X = (canvas.getWidth() / 2) - (HB_WIDTH / 2);
        HB_POS_Y = (canvas.getHeight() - 20) - HB_HEIGHT;
        
        HB_WIDTH = HB_OL_WIDTH * (player.getHealth() / player.getMaxHealth());
        
        HB_FILL = new Rectangle2D.Double(HB_POS_X, HB_POS_Y, HB_WIDTH, HB_HEIGHT);
        HB_OUTLINE = new Rectangle2D.Double(HB_POS_X, HB_POS_Y, HB_OL_WIDTH, HB_HEIGHT);
        
        debug.console(3, NAME + "(" + OBJECT_ID + "): Object created.");
        
    }
    
    public void draw(Graphics2D g) {
        
        // Draw the Health Bar
        // Calc how much health to show
        double HP_PERCENT = (double)player.getHealth() / (double)player.getMaxHealth();
        
        HB_WIDTH = HB_OL_WIDTH * HP_PERCENT;
        
        g.setColor(Color.red);
        
        HB_FILL.setFrame(HB_POS_X, HB_POS_Y, HB_WIDTH, HB_HEIGHT);
        g.fill(HB_FILL);
        
        g.setColor(Color.red);
        
        // Set the thickness of the border
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(HB_OUTLINE_THICKNESS));
        
        HB_OUTLINE.setFrame(HB_POS_X, HB_POS_Y, HB_OL_WIDTH, HB_HEIGHT);
        g.draw(HB_OUTLINE);
        
        // Set the thickness back to normal
        g.setStroke(oldStroke);
        
    }
    
}
