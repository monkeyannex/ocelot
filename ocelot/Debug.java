package ocelot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Debug {
	
    // The level to which
	private int LEVEL;
    private int LEVEL_SPECIAL = 9;
	//Initialise the log file
	
	//Define level
	//0= lowest level, only fatal error messages
	//1= non-fatal error messages
	//2= warnings that can be ignored
	//3= info, log of classes called etc, event info.
    //4= everything
	//9= custom stuff 
    
    private MainFrame mainframe;
    private Engine engine;
	
	public Debug(int lvl){
		LEVEL = lvl;
		//Create log file with datetime appending to it
		//in the /logs folder
		
	}
	
	
	public void log(int lvl, String message){
		//if global level >= level
			//print to the log file
			//print object.method=||message
	}
	
	
	public void console(int lvl, String message){
        
        // Print the message if it matches the logging level
        if (lvl <= LEVEL) System.out.println(message);
        
        // Print the message anyway if it matches the special logging level
        if (LEVEL == LEVEL_SPECIAL) System.out.println(message);
		
	}
	
	
	public void screen(String message){
		
	}
    
    public void setMainFrame(MainFrame imainframe) {
        
        mainframe = imainframe;
        
    }
    
    public void setEngine(Engine iengine) {
        
        engine = iengine;
        
    }
    
    public void drawDebug(Graphics g) {
		
        g.setFont(new Font("default", Font.PLAIN, 10));
        
        int Y_SPACING = 15;
        int POS_X = 10;
        int POS_Y = 20;
        
        g.setColor(Color.white);
        
        // Draws window size details
        g.drawString("Window Size: " + mainframe.getWidth() + ", " + mainframe.getHeight(), POS_X, POS_Y);
        
        //Draw UPTIME
        g.drawString("UPTIME: " + engine.getUpTimeSimple() + " sec", POS_X, (POS_Y += Y_SPACING));
        
        //Draw FPS
        g.drawString("FPS: " + engine.getFPS(), POS_X, (POS_Y += Y_SPACING));
        
	}
}
