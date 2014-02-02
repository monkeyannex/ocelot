package ocelot.object;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import ocelot.*;

public class GameObject {
    
    public String TYPE = "EMPTY";
    public String NAME = "UN-NAMED";
    public int OBJECT_ID = 0;
    
    public boolean VISIBLE = false;
    public boolean SOLID = false;
    
    public ocelot.Canvas canvas;
    public MainFrame mainframe;
    public Engine engine;
    public Debug debug;
    
    public GameObject(int iOBJECT_ID, Engine iengine) {        
        
        OBJECT_ID = iOBJECT_ID;
        engine = iengine;
        
        // Get the other things the object will need
        debug = engine.getDebug();
        mainframe = engine.getMainFrame();
        canvas = engine.getCanvas();
    
    }
    
    public GameObject(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine) {        
        
        super();
        
        NAME = iNAME;
        TYPE = iTYPE;        
    
    }
    
    public boolean detectCollision() {
        
        debug.console(2, NAME + "(" + OBJECT_ID + ")" + ": Cannot detect collision, object is empty.");
        return false;
        
    }
    
    public void draw(Graphics2D g) {
        
        debug.console(2, NAME + "(" + OBJECT_ID + ")" + ": Cannot draw, object is empty.");
        
    }
    
    // Return the objects type
    public String getType() {
        
        return TYPE;
        
    }
    
    // Return the objects name
    public String getName() {
        
        return NAME;
        
    }
    
    // Return the objects unique ID
    public int getObjectID() {
        
        return OBJECT_ID;
        
    }
    
    // Return SOLID state
    public boolean isSolid() {
        
        if(SOLID) return true;
        return false;
        
    }
    
    // Return VISIBLE state
    public boolean isVisable() {
        
        if(VISIBLE) return true;
        return false;
        
    }
    
    // Set the VISIBLE state
    public void setVisable(boolean state) {
        
        VISIBLE = state;
        
    }
    
    // Set the SOLID state
    public void setSolid(boolean state) {
        
        SOLID = state;
        
    }
    
}
