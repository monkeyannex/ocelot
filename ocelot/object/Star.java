package ocelot.object;

import ocelot.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Star extends GameObject {
    
    // Setup the main part of the star
    private Rectangle2D.Double star;
    private double starPosX;
    private double starPosY;
    private double starSize;
    private float starColorR = (float)(255.0 / 255.0);
    private float starColorG = (float)(255.0 / 255.0);
    private float starColorB = (float)(0 / 255.0);
    private float starAlpha = 1;
    private Color starColor = new Color(starColorR, starColorG, starColorB, starAlpha);
    
    // Setup the stars corona
    private Rectangle2D.Double corona;
    private double coronaPosX;
    private double coronaPosY;
    private double coronaSize;
    private float coronaColorR = (float)(255.0 / 255.0);
    private float coronaColorG = (float)(255.0 / 255.0);
    private float coronaColorB = (float)(0 / 255.0);
    private float coronaAlpha;
    private float coronaAlphaBase = 0.15f;
    private float coronaAlphaLeeway = 0.3f;
    private Color coronaColor = new Color(coronaColorR, coronaColorG, coronaColorB, coronaAlpha);
    private GenericTimer pulseTimer;
    private GenericTimer hitTimer;
    private double pulseLength = 5.0;
    
    // any object inside this box gets damaged
    private Rectangle2D.Double dmgBox;
    
    private double particleDistance;
    
    private ArrayList hitList;
    private double hitFreq = 1.0;
    
    public Star(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine) {
        
        super(iOBJECT_ID, iengine);
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): Creating object...");
        
        NAME = iNAME;
        TYPE = iTYPE;
        
        hitList = new ArrayList();
        
        VISIBLE = true;
        SOLID = true;
        
        // Setup the star
        starPosX = 600.0;
        starPosY = 200.0;
        starSize = 200.0;
        star = new Rectangle2D.Double(starPosX, starPosY, starSize, starSize);
        HITBOX = new Rectangle2D.Double(starPosX, starPosY, starSize, starSize);
        
        // Setup the corona
        coronaSize = starSize + ((starSize / 100.0) * 20.0);
        coronaPosX = starPosX - ((coronaSize - starSize) / 2);
        coronaPosY = starPosY - ((coronaSize - starSize) / 2);
        corona = new Rectangle2D.Double(coronaPosX, coronaPosY, coronaSize, coronaSize);
        pulseTimer = new GenericTimer(engine.nextObjectUID());
        engine.addTimer(pulseTimer);
        coronaAlpha = coronaAlphaBase;
        
        hitTimer = new GenericTimer(engine.nextObjectUID());
        engine.addTimer(hitTimer);
        
        HEALTH_MAX = 1000000000;
        HEALTH = HEALTH_MAX;
        
        debug.console(3, NAME + "(" + OBJECT_ID + "): Object created.");
        
    }
    
    public void draw(Graphics2D g) {
        
        // adjust the colours of the various drawn parts
        calcColor();
        
        // draw the stars corona
        g.setColor(coronaColor);
        g.fill(corona);
        
        // draw the main part of the star
        g.setColor(starColor);
        g.fill(star);
        
        g.setFont(new Font("default", Font.PLAIN, 10));
        g.setColor(Color.white);
        int HP_POS_X = (int)starPosX - 20;
        int HP_POS_Y = (int)starPosY - 20;
        g.drawString("HP: " + HEALTH, HP_POS_X, HP_POS_Y);
        
    }
    
    public void calcColor() {
        
        double percent = pulseTimer.getLastUpdate() / pulseLength;
        float change = (float)coronaAlphaLeeway * (float)percent;
        
        //debug.console(3, NAME + "(" + OBJECT_ID + "): coronaAlpha: " + coronaAlpha);
        
        //if (pulseTimer.getTime() >=  pulseLength * 2.0) pulseTimer.resetTime();
        //else if (pulseTimer.getTime() >= pulseLength) coronaAlpha += (float)percent;
        //else coronaAlpha -= (float)percent;
        
        //float coronaAlphaAdjustment = (float)pulseTimer.getLastUpdate() / coronaAlphaLeeway;
        
        if (pulseTimer.getTime() >=  pulseLength * 2.0) pulseTimer.resetTime();
        else if (pulseTimer.getTime() >= pulseLength) coronaAlpha += change;
        else coronaAlpha -= change;
        
        if(coronaAlpha > coronaAlphaBase + coronaAlphaLeeway) coronaAlpha = coronaAlphaBase + coronaAlphaLeeway;
        if(coronaAlpha < coronaAlphaBase) coronaAlpha = coronaAlphaBase;
        
        coronaColor = new Color(coronaColorR, coronaColorG, coronaColorB, coronaAlpha);
        
    }
    
    public void detectCollision() {
        
        if (hitTimer.getTime() > hitFreq) {
            
            hitTimer.resetTime();
            hitList.clear();
            hitList.add(0);
            
        }
        
        ArrayList OBJECT_LIST = engine.getObjectList();
        
        if (OBJECT_LIST == null) return;
        
        for (int x = 0; x < OBJECT_LIST.size(); x++) {
        
            GameObject object = (GameObject)OBJECT_LIST.get(x);
            
            if (object.SOLID && object.getObjectID() != OBJECT_ID && object.getHitbox() != null) {
            
                //debug.console(3, NAME + "(" + OBJECT_ID + "): Testing for collision with " + object.getName());
                
                if (corona.intersects(object.getHitbox())) {
                    
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
    
    public void updateHitbox() {
        
        HITBOX.setFrame(starPosX, starPosY, starSize, starSize);
        
    }
    
    public Rectangle2D.Double getHitbox() {
        
        updateHitbox();
        return HITBOX;
        
    }
    
}
