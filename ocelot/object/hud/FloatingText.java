package ocelot.object.hud;

import ocelot.*;
import ocelot.object.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/* Standard object to hold text */

public class FloatingText extends SimpleText {
    
    // How long the text displays
    private double textLifespan = 2.0;
    private double distance = 30.0;
    
    private Player player;
    private String source;
    private int health;
    
    private GenericTimer timer;

    public FloatingText(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine, int ihealth, String isource, Player iplayer) {
    
        super(iOBJECT_ID, iNAME, iTYPE, iengine);
        
        VISIBLE = true;
        SOLID = false;
        
        //timer = new GenericTimer(engine.nextObjectUID());
        //engine.addTimer(timer);
        
        health = ihealth;
        source = isource;        
        player = iplayer;
        
        posX = player.getPosX();
        posY = player.getPosY();
        
        text = "" + health;
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): " + health + "HP dmg from " + source + ".");
    
    }
    
    public void draw(Graphics2D g) {
        
        // stop the text if it has been around too long
        //if (timer.getTime() > textLifespan) {
            
            //engine.removeObject(engine.getObjectIndex(OBJECT_ID));
            //engine.removeTimer(engine.getTimerIndex(timer.getTimerUID()));
            
            //return;
            
        //}
        
        //double percent = timer.getLastUpdate() / textLifespan;
        
        //posY += distance * percent;
        
        if (health < 0) g.setColor(Color.red);
        else if (health > 0) g.setColor(Color.green);
        else g.setColor(Color.orange);
        
        g.setColor(TEXT_COLOR);
        
        font = new Font(fontName, Font.PLAIN, size);
        
        font.deriveFont((float)size);
        
        g.setFont(font);
        
        g.drawString(text, (int)posX, (int)posY);
        
    }

}
