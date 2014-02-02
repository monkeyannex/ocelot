package ocelot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import ocelot.object.*;

public class Canvas extends JComponent{

    private Graphics2D g;
    
    private Rectangle2D.Double bg;
    
    private Engine engine;
    private MainFrame mainframe;
    private Debug debug;
    
    boolean DEBUG_LAYER = true;
    
    public Canvas(Debug idebug, Engine iengine, MainFrame imainframe) {
        
        debug = idebug;
        engine = iengine;
        mainframe = imainframe;
        
        debug.console(3,"CANVAS: Drawing Canvas starting...");
        
        bg = new Rectangle2D.Double(0,0,mainframe.getWidth(),mainframe.getHeight());
        
        // Do stuff
        
        debug.console(3,"CANVAS: Drawing Canvas created.");
        
    }
    
    public void paint(Graphics gg) {
        
        // initialises the double buffering
        g = (Graphics2D) gg;
            
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        // sets the drawing colour to black, and draws a rectangle as the background
        // resizes the background to always take up the full window
        // always draws as big as the window
        g.setColor(Color.black);
        bg.setFrame(0,0,mainframe.getWidth(),mainframe.getHeight());
        g.fill(bg);
        
        // Main Drawing
        // This draws everything in OBJECT_LIST that is VISIBLE
        ArrayList OBJECT_LIST = engine.getObjectList();
        
        if (OBJECT_LIST == null) return;
        
        for (int x = 0; x < OBJECT_LIST.size(); x++) {
        
            GameObject object = (GameObject)OBJECT_LIST.get(x);
        
            if (object.VISIBLE) {
                    
                object.draw(g);
                
            }
        
        }
        
        // Last thing to draw is the Debug Layer if required
        if (DEBUG_LAYER) debug.drawDebug(g);
        
    }
    
    // Draws debug info
    // Anything drawn here will be drawn over the top of everything else
    private void debugLayer() {
        
        g.setFont(new Font("default", Font.PLAIN, 10));
        
        int Y_SPACING = 15;
        int POS_X = 10;
        int POS_Y = 20;
        
        g.setColor(Color.white);
        
        
            
    }
    
    public void toggleDebugLayer() {
        
        DEBUG_LAYER = !DEBUG_LAYER;
        
    }
    
    public Graphics2D getGraphics() {
        
        return g;
        
    }

}
