package ocelot.object.effects;

import ocelot.*;
import ocelot.object.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class PlayerTeleport extends GameObject{
    
    private Rectangle2D.Double glow;    
    
    private GenericTimer timer;
    private Player player;
    
    private double EFFECT_LENGTH = 0.5;
    
    private double POS_X;
    private double POS_Y;
    private double SIZE_X;
    private double SIZE_Y;
    private double SPEED_X;
    private double SPEED_Y;
    
    private int DISTANCE_MAX = 150;
    private int DISTANCE_MIN = 50;
    
    private boolean TELEPORTED = false;
    
    private Color COLOR;
    private float COLOR_RED = (float)(157.0 / 255.0);
    private float COLOR_GREEN = (float)(255.0 / 255.0);
    private float COLOR_BLUE = (float)(0.0 / 255.0);
    private float ALPHA;
    private float THICKNESS = 5;
    
    public PlayerTeleport(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine, Player iplayer) {
        
        super(iOBJECT_ID, iengine);
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): Creating object...");
        
        NAME = iNAME;
        TYPE = iTYPE;
        
        VISIBLE = true;
        SOLID = false;
        
        player = iplayer;
        
        COLOR = COLOR = new Color(COLOR_RED,COLOR_GREEN,COLOR_BLUE,ALPHA);
        ALPHA = 0;
        
        timer = new GenericTimer(engine.nextObjectUID());
        engine.addTimer(timer);
        
        POS_X = player.getPosX();
        POS_Y = player.getPosY();
        SIZE_X = player.getSizeX();
        SIZE_Y = player.getSizeY();
        SPEED_X = 0;
        SPEED_Y = 0;
        glow = new Rectangle2D.Double(POS_X, POS_Y, SIZE_X, SIZE_Y);
        
        debug.console(3, NAME + "(" + OBJECT_ID + "): Object created.");
        
    }
    
    public void draw(Graphics2D g) {
        
        // Stop the effect if it has been around too long
        if (timer.getTime() > (EFFECT_LENGTH * 2)) {
                        
            engine.removeObject(engine.getObjectIndex(OBJECT_ID));
            engine.removeTimer(engine.getTimerIndex(timer.getTimerUID()));
            
            return;
            
        }
        
        // Update the position and size
        if (timer.getTime() >= EFFECT_LENGTH) {
            
            if (!TELEPORTED) {
                
                Random random = new Random();
                
                double X_OFFSET = random.nextInt((DISTANCE_MAX - DISTANCE_MIN) + DISTANCE_MIN);
                double Y_OFFSET = random.nextInt((DISTANCE_MAX - DISTANCE_MIN) + DISTANCE_MIN);
                
                // Randomly change the direction
                if(random.nextInt(100) < 50) X_OFFSET = -X_OFFSET;
                if(random.nextInt(100) < 50) Y_OFFSET = -Y_OFFSET;
            
                // Set the new player postition
                player.setPosX(player.getPosX() + X_OFFSET);
                player.setPosY(player.getPosY() + Y_OFFSET);
                
                SPEED_X = player.getSpeedX();
                SPEED_Y = player.getSpeedY();
                
                TELEPORTED = true;
            
            }
            
            POS_X += SPEED_X;
            POS_Y += SPEED_Y;
            
            calcColor(false);
            
        }
        else if (timer.getTime() < EFFECT_LENGTH) {
            
            POS_X = player.getPosX();
            POS_Y = player.getPosY();
            SIZE_X = player.getSizeX();
            SIZE_Y = player.getSizeY();
            
            calcColor(true); 
            
        }
        
        
        
        
        
        // Calc the color then set it
        //calcColor();        
        g.setColor(COLOR);
        
        // Set the thickness of the border
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(THICKNESS));
        
        glow.setFrame(POS_X, POS_Y, SIZE_X, SIZE_Y);
        g.draw(glow);
        
        // Set the thickness back to normal
        g.setStroke(oldStroke);
        
    }
    
    public void calcColor(boolean fadein) {
        
        double percent = timer.getTime() / EFFECT_LENGTH;
        
        
        if (fadein) ALPHA = 0 + (float)percent;
        if (!fadein) ALPHA = 1 - ((float)percent - (float)EFFECT_LENGTH);
        
        
        // Make sure that the values dont go out of range
        if(ALPHA > 1) ALPHA = 1;
        if(ALPHA < 0) ALPHA = 0;
        
        // Set the new colour
        COLOR = new Color(COLOR_RED,COLOR_GREEN,COLOR_BLUE,ALPHA);
        
        
        
    }
    
}
