package ocelot.engine;

import ocelot.*;
import ocelot.object.*;
import ocelot.object.effects.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

/* DEFAULT PHASE */
// Displays some default stuff

public class Phase {
    
    /* GLOBAL OBJECTS */
    private Engine engine;
    private ocelot.Canvas canvas;
    private MainFrame mainframe;
    private Debug debug;
    private SimpleText phaseTitle;
    private Player player;
    private StarField starField;
    
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
        
        // Update the position of everything
        player.updatePos();
        
        detectCollision();
        
    }
    
    public void firstRun() {
        
        debug.console(3,"DEFAULT_PHASE: First run, initialising...");
        
        
        
        // create the starfield
        starField = new StarField(engine.nextObjectUID(), "Star Field", "BACKGROUND", engine);
        engine.addObject(starField);
        
        // create a Player object
        player = new Player(engine.nextObjectUID(), "Player 1", "PLAYER", engine);
        engine.addObject(player);
        
        // create a star
        engine.addObject(new Star(engine.nextObjectUID(), "Unknown Star", "STAR", engine));
        
        debug.console(3,"DEFAULT_PHASE: First run complete.");
        
        // Create the title text for the phase
        phaseTitle = new SimpleText(engine.nextObjectUID(), "Default Phase Title", "SIMPLE_TEXT", engine);
        phaseTitle.setFont("stylised");
        phaseTitle.setSize(50);
        engine.addObject(phaseTitle);
        
        // Setup the title text for the phase
        // Get Graphics2D for font positioning calculations
        phaseTitle.setVisable(true);
        phaseTitle.setSize(20);
        
        
        FIRST_RUN = false;
        
    }
    
    public void detectCollision() {
        
        ArrayList OBJECT_LIST = engine.getObjectList();
        
        if (OBJECT_LIST == null) return;
        
        for (int x = 0; x < OBJECT_LIST.size(); x++) {
        
            GameObject object = (GameObject)OBJECT_LIST.get(x);
        
            if (object.SOLID) {
                    
                object.detectCollision();
                
            }
        
        }
        
    }
    
    // allows code to run when a pressed key is released
    public void keyPressed(int key) {
        
        switch (key) {
            
            /* MOVEMENT */
            // KEY "w"
            case 87:
                player.startMovingY(-1);
                break;
            // KEY "a"
            case 65:
                player.startMovingX(-1);
                break;
            // KEY "s"
            case 83:
                player.startMovingY(1);
                break;
            // KEY "d"
            case 68:
                player.startMovingX(1);
                break;

            /* EFFECTS */
            // KEY "x"
            case 88:
                if (!player.onCooldownGlobal()) {
                    
                    //player.resetGlobalCooldown();
                    engine.addObject(new ParticleEmitter(engine.nextObjectUID(), "Particle Effect", "EFFECT", engine, player));
                                        
                }
                break;
            // KEY "z"
            case 90:
                if (!player.onCooldownGlobal()) {
                    
                    player.resetGlobalCooldown();
                    engine.addObject(new PlayerTeleport(engine.nextObjectUID(), "Teleport Effect", "EFFECT", engine, player));
                    
                }
                break;
            // KEY "c"
            case 67:
                if (!player.onCooldownGlobal()) {
                    
                    player.resetGlobalCooldown();
                    engine.addObject(new PlayerShockwave(engine.nextObjectUID(), "Shockwave Effect", "EFFECT", engine, player));
                    
                }
                break;
            
        }
        
    }
    
    // allows code to run when a pressed key is released
    public void keyReleased(int key) {
        
        switch (key) {
            
            // KEY "w"
            case 87:
                player.stopMovingY();
                break;
            // KEY "a"
            case 65:
                player.stopMovingX();
                break;
            // KEY "s"
            case 83:
                player.stopMovingY();
                break;
            // KEY "d"
            case 68:
                player.stopMovingX();
                break;
            
        }
        
    }
    
    // allows code to run when a pressed key is released
    public void keyTyped(int key) {
        
        switch (key) {
            
            
            
        }
        
    }

}
