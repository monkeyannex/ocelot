package ocelot.object;

import ocelot.*;
import ocelot.object.effects.*;
import ocelot.object.hud.*;
import java.awt.*;
import java.awt.geom.*;

public class Player extends GameObject {
    
    private Rectangle2D.Double BODY;
    private double POS_X;
    private double POS_Y;
    private double SIZE_X;
    private double SIZE_Y;
    
    /* MOVEMENT VARIABLES */
    // Is the player moving
    // true means moving, false means stopped
    private boolean MOVEMENT_X;
    private boolean MOVEMENT_Y;
    // The max speed the player can move
    private double SPEED_MAX;
    // How fast the player decelerates from max speed
    private double SPEED_DEGEN;
    // The actual speed of the player
    private double SPEED_X;
    private double SPEED_Y;
    
    private GenericTimer COOLDOWN_GLOBAL;
    private double CD_GLOBAL = 1.0;
    

    public Player(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine) {

        super(iOBJECT_ID, iengine);
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): Creating object...");
        
        NAME = iNAME;
        TYPE = iTYPE;
        
        VISIBLE = true;
        SOLID = true;
        
        POS_X = 100.0;
        POS_Y = 100.0;
        
        SIZE_X = 25.0;
        SIZE_Y = 25.0;
        
        // Set the max speed to be a percentage of the screen width
        SPEED_MAX = (mainframe.getWidth() / 100.0) * 0.3;
        // Set the rate at which the movement speed will degenerate at(with respect to SPEED_MAX)
        SPEED_DEGEN = (SPEED_MAX / 100.0) * 10.0;
        
        SPEED_X = 0.0;
        SPEED_Y = 0.0;
        
        HEALTH_MAX = 100;
        HEALTH = HEALTH_MAX;
        
        BODY = new Rectangle2D.Double(POS_X, POS_Y, SIZE_X, SIZE_Y);
        HITBOX = new Rectangle2D.Double(POS_X, POS_Y, SIZE_X, SIZE_Y);
        
        COOLDOWN_GLOBAL = new GenericTimer(engine.nextObjectUID());
        engine.addTimer(COOLDOWN_GLOBAL);
        
        // create the player hud
        engine.addObject(new PlayerHUD(engine.nextObjectUID(), "Player HUD", "HUD", engine, this));
        
        debug.console(3, NAME + "(" + OBJECT_ID + "): Object created.");

    }
    
    public void updatePos() {
        
        if (SPEED_X == 0 && SPEED_Y == 0) return;
        
        POS_X += SPEED_X;
        POS_Y += SPEED_Y;
        
        updateSpeed();
        
    }
    
    public void updateSpeed() {
        
        debug.console(4, NAME + "(" + OBJECT_ID + "): Updating position.");
        debug.console(4, NAME + "(" + OBJECT_ID + "): Old positions: POS_X = " + POS_X + " POS_Y = " + POS_Y);
        
        // Adjust the speed of the X component
        // Making sure to check the direction of current movement - -ve or +ve
        if(SPEED_X < 0 && !MOVEMENT_X) {
            
            SPEED_X += SPEED_DEGEN;
            if(SPEED_X > 0) SPEED_X = 0;
            
        }
        if(SPEED_X > 0 && !MOVEMENT_X) {
            
            SPEED_X -= SPEED_DEGEN;
            if(SPEED_X < 0) SPEED_X = 0;
            
        }
        
        // Adjust the speed of the Y component
        // Making sure to check the direction of current movement - -ve or +ve
        if(SPEED_Y < 0 && !MOVEMENT_Y) {
            
            SPEED_Y += SPEED_DEGEN;
            if(SPEED_Y > 0) SPEED_Y = 0;
            
        }
        if(SPEED_Y > 0 && !MOVEMENT_Y) {
            
            SPEED_Y -= SPEED_DEGEN;
            if(SPEED_Y < 0) SPEED_Y = 0;
            
        }
        
        debug.console(4, NAME + "(" + OBJECT_ID + "): New positions: POS_X = " + POS_X + " POS_Y = " + POS_Y);
        
    }
    
    public void setPosX(double ipos) {
        
        POS_X = ipos;
        
    }
    
    public void setPosY(double ipos) {
        
        POS_Y = ipos;
        
    }
    
    public void startMovingX(int direction) {
        
        MOVEMENT_X = true;
        
        if (direction > 0) SPEED_X = SPEED_MAX;
        if (direction < 0) SPEED_X = -SPEED_MAX;
        
        
    }
    
    public void startMovingY(int direction) {
        
        MOVEMENT_Y = true;
        
        if (direction > 0) SPEED_Y = SPEED_MAX;
        if (direction < 0) SPEED_Y = -SPEED_MAX;
        
        
    }
    
    public void stopMovingX() {
        
        MOVEMENT_X = false;
        
    }
    
    public void stopMovingY() {
        
        MOVEMENT_Y = false;
        
    }
    
    public void setSizeX(double iSIZE_X) {
        
        SIZE_X = iSIZE_X;
        
    }
    
    public void setSizeY(double iSIZE_Y) {
        
        SIZE_Y = iSIZE_Y;
        
    }
    
    public void draw(Graphics2D g) {
        
        BODY.setFrame(POS_X, POS_Y, SIZE_X, SIZE_Y);
        g.setColor(Color.white);
        g.fill(BODY);
        
    }
    
    public void detectCollision() {
        
        
        
    }
    
    public boolean onCooldownGlobal() {
        
        if(COOLDOWN_GLOBAL.getTime() < CD_GLOBAL) return true;
        else return false;
        
    }
    
    public void resetGlobalCooldown() {
        
        COOLDOWN_GLOBAL.resetTime();
        
    }
    
    public void updateHitbox() {
        
        HITBOX.setFrame(POS_X, POS_Y, SIZE_X, SIZE_Y);
        
    }
    
    public Rectangle2D.Double getHitbox() {
        
        updateHitbox();
        return HITBOX;
        
    }
    
    public void adjustHealth(int amount, String source) {
        
        HEALTH += amount;
        
        if (HEALTH <= 0) {
            
            engine.addObject(new PlayerExplode(engine.nextObjectUID(), "Explode Effect", "EFFECT", engine, this));
            HEALTH = HEALTH_MAX;
            
        }
        //else engine.addObject(new FloatingText(engine.nextObjectUID(), "Floating Text", "HUD", engine, amount, source, this));
        
    }
    
    public double getPosX() {
        
        return POS_X;
        
    }
    
    public double getPosY() {
        
        return POS_Y;
        
    }
    
    public double getSizeX() {
        
        return SIZE_X;
        
    }
    
    public double getSizeY() {
        
        return SIZE_Y;
        
    }
    
    public double getSpeedX() {
        
        return SPEED_X;
        
    }
    
    public double getSpeedY() {
        
        return SPEED_Y;
        
    }
}
