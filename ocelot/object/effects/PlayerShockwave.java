package ocelot.object.effects;

import ocelot.*;
import ocelot.object.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.ArrayList;

public class PlayerShockwave extends GameObject{
    
    private Player player;
    private GenericTimer timer;
    private ArrayList hitList;
    
    private Rectangle2D.Double FILL;
    private Rectangle2D.Double EDGE;
    
    private double SIZE;
    private double SIZE_MAX = 200.0;
    private double SPEED = 1.0;
    
    private int POWER_MIN = 5;
    private int POWER_MAX = 10;
    
    private double POS_X;
    private double POS_Y;
    private double POS_X_CENTER;
    private double POS_Y_CENTER;
    
        
    private Color COLOR_FILL;
    private Color COLOR_EDGE;
    private float COLOR_RED = (float)(255.0 / 255.0);
    private float COLOR_GREEN = (float)(57.0 / 255.0);
    private float COLOR_BLUE = (float)(3.0 / 255.0);
    private float ALPHA_FILL = (float)0.3;
    private float ALPHA_EDGE = (float)0.75;
    private float THICKNESS = 3;
    
    public PlayerShockwave(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine, Player iplayer) {
        
        super(iOBJECT_ID, iengine);
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): Creating object...");
        
        NAME = iNAME;
        TYPE = iTYPE;
        
        VISIBLE = true;
        SOLID = true;
        
        player = iplayer;
        
        hitList = new ArrayList();
        hitList.add(0);
        
        SIZE = 0;
        
        POS_X_CENTER = player.getPosX() - (SIZE / 2) + (player.getSizeX() / 2);
        POS_Y_CENTER = player.getPosY() - (SIZE / 2) + (player.getSizeY() / 2);
        
        FILL = new Rectangle2D.Double(POS_X, POS_Y, SIZE, SIZE);
        EDGE = new Rectangle2D.Double(POS_X, POS_Y, SIZE, SIZE);
        
        timer = new GenericTimer(engine.nextObjectUID());
        engine.addTimer(timer);
        
        debug.console(3, NAME + "(" + OBJECT_ID + "): Object created.");
        
    }
    
    public void draw(Graphics2D g) {
        
        // Stop the effect if it has been around too long
        if (timer.getTime() > SPEED) {
            
            engine.removeObject(engine.getObjectIndex(OBJECT_ID));
            engine.removeTimer(engine.getTimerIndex(timer.getTimerUID()));
            return;
            
        }
        
        double percent = (SIZE_MAX / SPEED) * timer.getLastUpdate();
        
        SIZE += percent;
        
        POS_X = POS_X_CENTER - (SIZE / 2);
        POS_Y = POS_Y_CENTER - (SIZE / 2);
        
        COLOR_FILL = new Color(COLOR_RED,COLOR_GREEN,COLOR_BLUE,ALPHA_FILL);
        COLOR_EDGE = new Color(COLOR_RED,COLOR_GREEN,COLOR_BLUE,ALPHA_EDGE);
        
        g.setColor(COLOR_FILL);
        
        FILL.setFrame(POS_X, POS_Y, SIZE, SIZE);
        g.fill(FILL);
        
        g.setColor(COLOR_EDGE);
        
        // Set the thickness of the border
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(THICKNESS));
        
        EDGE.setFrame(POS_X, POS_Y, SIZE, SIZE);
        g.draw(EDGE);
        
        // Set the thickness back to normal
        g.setStroke(oldStroke);
        
    }
    
    public void detectCollision() {
        
        ArrayList OBJECT_LIST = engine.getObjectList();
        
        if (OBJECT_LIST == null) return;
        
        for (int x = 0; x < OBJECT_LIST.size(); x++) {
        
            GameObject object = (GameObject)OBJECT_LIST.get(x);
            
            if (object.SOLID && object.getObjectID() != OBJECT_ID && object.getHitbox() != null && object.getObjectID() != player.getObjectID()) {
            
                //debug.console(3, NAME + "(" + OBJECT_ID + "): Testing for collision with " + object.getName());
                
                if (FILL.intersects(object.getHitbox())) {
                    
                    boolean alreadyHit = false;
                    
                    for (int y = 0; y < hitList.size(); y++) {
                    
                        if (object.getObjectID() == (int)hitList.get(y)) alreadyHit = true;
                    
                    }
                    
                    if (!alreadyHit) {
                        
                        //debug.console(3, NAME + "(" + OBJECT_ID + "): Collision with " + object.getName());
                        hitList.add(object.getObjectID());
                        object.adjustHealth(-5, NAME);
                        
                    }
                    
                }
            
            }
            
        }
        
    }
    
}
