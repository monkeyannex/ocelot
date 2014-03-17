package ocelot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MainFrame extends JFrame implements KeyListener {

    private static String TITLE = "Ocelot Engine";
    private static String VERSION = "0.1";

    private static int WIDTH = 900;
    private static int HEIGHT = 450;
    
    private boolean FULLSCREEN = false;
    
    private Debug debug;
    private Engine engine;
    private ocelot.Canvas canvas;
    
    public MainFrame(Debug idebug, Engine iengine) {
        
        // Initialise things
        debug = idebug;
        engine = iengine;
        
        debug.console(3,"MAIN_FRAME: Starting Game Window...");
        
        // Setup main frame properties
        setTitle(TITLE + " " + VERSION);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Debug
        debug.console(3, "MAIN_FRAME: " + TITLE + " " + VERSION);
        debug.console(3, "MAIN_FRAME: " + WIDTH + ", " + HEIGHT);
        
        
        // start the main drawing canvas that everything with be drawn onto
        canvas = new ocelot.Canvas(debug, engine, this);
        
        // Give the debug class everything it needs
        debug.setMainFrame(this);
        debug.setEngine(engine);
        // Give the engine class everything it needs
        engine.setMainFrame(this);
        engine.setCanvas(canvas);
                
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        // sets the actual drawing pane
        setContentPane(canvas);
        
        // starts the ability to accept kestrokes
        this.addKeyListener(this);
        
        pack();
        setVisible(true);
        validate();
        
        debug.console(3,"MAIN_FRAME: Game Window created.");
        
        // Enable fullscreen if needed
        if(FULLSCREEN) {
            
            // NOt sure why or how this works, but it enables fullscreen mode
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
            
        }
        
    }
    
    /* GLOBAL KEYSTROKES HANDLED HERE */
    /* PHASE SPECIFIC HANDLED IN PHASES */
    
    // allows code to run on a keypress
    public void keyPressed(KeyEvent evt) {
        
        int key = evt.getKeyCode();
        
        debug.console(3,"KEY_PRESSED: " + key);
        
        switch (key) {
            
            // 'TILDE'    
            case 192:
                canvas.toggleDebugLayer();                
                break;
                
            // Pass on the key to the running phase
            default:
                engine.runningPhase.keyPressed(key);
                break;
            
        }
        
    }
    
    // allows code to run when a pressed key is released
    public void keyReleased(KeyEvent evt) {
        
        int key = evt.getKeyCode();
        
        debug.console(3,"KEY_RELEASED: " + key);
        
        switch (key) {
                
            // Pass on the key to the running phase
            default:
                engine.runningPhase.keyReleased(key);
                break;
            
        }
        
    }
    
    // allows code to run when a key is typed
    public void keyTyped(KeyEvent evt) {
        
        int key = evt.getKeyCode();
        
        debug.console(3,"KEY_TYPED: " + key);
        
        switch (key) {
                
            // Pass on the key to the running phase
            default:
                engine.runningPhase.keyTyped(key);
                break;
            
        }
        
    }
    
    public String getTitle() {
        
        return TITLE;
        
    }
    
    public String getVersion() {
        
        return VERSION;
        
    }

}
