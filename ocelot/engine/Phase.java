package ocelot.engine;

import ocelot.*;
import ocelot.object.*;
import java.awt.*;
import java.awt.geom.*;

/* DEFAULT PHASE */
// Displays some default stuff

public class Phase {
    
    /* GLOBAL OBJECTS */
    private Engine engine;
    private ocelot.Canvas canvas;
    private MainFrame mainframe;
    private Debug debug;
    private SimpleText phaseTitle;
    
    /* GLOBAL VARIABLES */
    private String ENGINE_TITLE;
    private boolean FIRST_RUN;

    public Phase(Engine iengine) {
    
        engine = iengine;
        
        canvas = engine.getCanvas();
        mainframe = engine.getMainFrame();
        debug = engine.getDebug();
        
        ENGINE_TITLE = "";
        
        FIRST_RUN = true;
        
    debug.console(3,"DEFAULT_PHASE: Creating Phase...");
    
       
    
    // Create some objects for the phase
    // The title and 
    
    debug.console(3,"DEFAULT_PHASE: Phase created.");
    
    }
    
    public void execute() {
        
        if (FIRST_RUN) firstRun();
        
        // Make sure the title is positioned correctly
        Graphics2D g = canvas.getGraphics();
        Font font = phaseTitle.getFont();
        FontMetrics metrics = g.getFontMetrics(font);
        
        int text_width = metrics.stringWidth(ENGINE_TITLE);
        int text_height = metrics.getHeight();
        ENGINE_TITLE = mainframe.getTitle() + " " + mainframe.getVersion();
        
        phaseTitle.setPosX((mainframe.getWidth() / 2) - (text_width / 2));
        phaseTitle.setPosY((text_height / 2) + 20);
        
        phaseTitle.setText(ENGINE_TITLE);
    }
    
    public void firstRun() {
        
        debug.console(3,"DEFAULT_PHASE: First run, initialising...");
        
        
        
        // Create the title text for the phase
        phaseTitle = new SimpleText(engine.nextObjectUID(), "Default Phase Title", "SIMPLE_TEXT", engine);
        engine.addObject(phaseTitle);
        
        // Setup the title text for the phase
        // Get Graphics2D for font positioning calculations
        
        phaseTitle.setVisable(true);
        phaseTitle.setSize(20);
        
        debug.console(3,"DEFAULT_PHASE: First run complete.");
        
        
        FIRST_RUN = false;
        
    }

}
