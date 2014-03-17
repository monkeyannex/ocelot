package ocelot.object.effects;

import ocelot.*;
import ocelot.object.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.geom.*;

public class PlayerExplode extends GameObject{
    
    private GenericTimer timer;
    private ArrayList particles;
    private Player player;
    
    private double POS_X;
    private double POS_Y;
    // Stores the inherited speed values
    private double SPEED_X;
    private double SPEED_Y;    
    private double DIR_PERCENT_X = 50;
    private double DIR_PERCENT_Y = 50;
    private double SIZE;
    // Power indicates how the max distance the particles should be able to travel
    private double POWER_MAX = 100.0;
    private int PARTICLE_NUMBER = 50;
    private float PARTICLE_ALPHA;
    private Color PARTICLE_COLOR;
    private float P_COLOR_RED = (float)(255.0 / 255.0);
    private float P_COLOR_GREEN = (float)(0.0 / 255.0);
    private float P_COLOR_BLUE = (float)(50.0 / 255.0);
    private int CYCLES = 1;
    // How long(in seconds) the effect should last
    private double EFFECT_LENGTH = 2.0;
    
    private double[][] p = new double[PARTICLE_NUMBER][4];
    
    public PlayerExplode(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine, Player iplayer) {
        
        super(iOBJECT_ID, iengine);
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): Creating object...");
        
        NAME = iNAME;
        TYPE = iTYPE;
        
        VISIBLE = true;
        SOLID = false;
        
        timer = new GenericTimer(engine.nextObjectUID());
        engine.addTimer(timer);
        
        particles = new ArrayList();
        player = iplayer;
        
        // Setup a random generator for things further down
        Random random = new Random();
        
        //POS_X = player.getPosX() + (player.getSizeX() / 2) - (SIZE / 2);
        //POS_Y = player.getPosY() + (player.getSizeY() / 2) - (SIZE / 2);
        
        SPEED_X = player.getSpeedX();
        SPEED_Y = player.getSpeedY();
        
        //if (SPEED_X < 0) DIR_PERCENT_X = 5;
        //if (SPEED_X > 0) DIR_PERCENT_X = 95;
        //if (SPEED_Y < 0) DIR_PERCENT_Y = 5;
        //if (SPEED_Y < 0) DIR_PERCENT_Y = 95;
        
        SIZE = 5.0;
        
        PARTICLE_ALPHA = 1;
        PARTICLE_COLOR = new Color(P_COLOR_RED,P_COLOR_GREEN,P_COLOR_BLUE,PARTICLE_ALPHA);
        
        int ROW = 0;
        int COLUMN = 0;
        for (int x = 0; x < PARTICLE_NUMBER; x++) {
            
            p[x][0] = player.getPosX() + (SIZE * COLUMN);
            p[x][1] = player.getPosY() + (SIZE * ROW);
            p[x][2] = random.nextDouble();
            p[x][3] = random.nextDouble();
            
            // Randomly reverse the x and y direction travelled
            if ((random.nextInt(100) + 1) > DIR_PERCENT_X) p[x][2] = -p[x][2];
            if ((random.nextInt(100) + 1) > DIR_PERCENT_Y) p[x][3] = -p[x][3];
            
            particles.add(new Rectangle2D.Double(p[x][0], p[x][1], SIZE, SIZE));
            
            COLUMN++;
            if (COLUMN > 5) {
                
                COLUMN = 0;
                ROW++;
                
                if (ROW > 5) ROW = 0;
                
            }
            
        }
        
        // Make the player disappear
        player.setSizeX(0);
        player.setSizeY(0);
        
        debug.console(3, NAME + "(" + OBJECT_ID + "): Object created.");
        
    }
    
    public void draw(Graphics2D g) {
        
        // Stop the effect if it has been around too long
        if (timer.getTime() > EFFECT_LENGTH) {
            
            engine.removeObject(engine.getObjectIndex(OBJECT_ID));
            engine.removeTimer(engine.getTimerIndex(timer.getTimerUID()));
            
            // Make the player reappear - this is for debug only until a respawn mechanic is impletmented
            player.setSizeX(25.0);
            player.setSizeY(25.0);
            
            return;
            
        }
        
        for (int x = 0; x < PARTICLE_NUMBER; x++) {
            
            Rectangle2D particle = (Rectangle2D)particles.get(x);
            
            p[x][0] += p[x][2];
            p[x][1] += p[x][3];
            
            double P_POS_X = p[x][0] + (SPEED_X);
            double P_POS_Y = p[x][1] + (SPEED_Y);
            
            particle.setFrame(P_POS_X, P_POS_Y, SIZE, SIZE);
            
            // Recalc the required color
            calcColor();
            
            g.setColor(PARTICLE_COLOR);
            g.fill(particle);
            
        }
        
        CYCLES++;
        
    }
    
    public void calcColor() {
        
        double percent = timer.getTime() / EFFECT_LENGTH;
        
        PARTICLE_ALPHA = 1 - (float)percent;
        
        // Make sure that the values dont go out of range
        if(PARTICLE_ALPHA > 1) PARTICLE_ALPHA = 1;
        if(PARTICLE_ALPHA < 0) PARTICLE_ALPHA = 0;
        
        // Set the new colour
        PARTICLE_COLOR = new Color(P_COLOR_RED,P_COLOR_GREEN,P_COLOR_BLUE,PARTICLE_ALPHA);
        
        
        
    }
    
}
