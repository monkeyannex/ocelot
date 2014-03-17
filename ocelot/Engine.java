package ocelot;

/* IMPORTED CLASSES */
import java.util.ArrayList;
import ocelot.engine.*;
import ocelot.object.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Engine implements Runnable {
    
    private boolean RUNNING;
    private boolean STARTUP;
    
    /* FPS CALC VARIABLES */
    private ArrayList fps;
    private long FPS_DESIRED = 60;      // Sets the desired frame frate
    private double FPS_CURRENT = 0;     // Holds the current frame rate    
    
    /* TIMER VAIRABLES */
    private long DESIRED_SLEEP_TIME = 1000000000 / FPS_DESIRED; // Sets the desired sleep time.
    private long LOOP_START;        // The system time at the start of the engine loop
    private long LOOP_UPDATE;       // The time it took to complete the last loop
    private long LOOP_LAST;         // The system time at the start of the last loop
    private double ENGINE_UPTIME;   // How long the engine has been running for
    
    /* OBJECT HANDLING VARIABLES */
    private static int OBJECT_UID = 1000;   // Tracks the unique identifying number of an object
    private ArrayList OBJECT_LIST;          // Holds all the objects in the game
    private ArrayList TIMER_LIST;           // Stores any running timers so they can all up updated each cycle
    
    
    // The number of loops the engine has executed
    private long LOOP_COUNT = 0;
    
    /* GLOBAL OBJECTS */
    private Debug debug;
    private ocelot.Canvas canvas;
    private MainFrame mainframe;    
    public Phase runningPhase;
    private Phase defaultPhase;
    
    /* FONTS USED */
    private Font fontStandard = null;
    private Font fontStylised = null;

    // Constructor
    public Engine(Debug idebug) {
    
        // Setup the things the engine needs
        debug = idebug;
        
        debug.console(3,"ENGINE: Starting Engine...");
        
        /* SETUP THE ENGINE */
        
        // Make sure the engine it set to running status
        RUNNING = true;
        
        // Set the engine for first time running
        STARTUP = true;
        
        // Initialise the appropriate ArrayLists
        fps = new ArrayList();
        OBJECT_LIST = new ArrayList(); 
        TIMER_LIST = new ArrayList();
        
        // Setup the fonts to use
        try {
        
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            
            // Font 1
            File fontFile = new File("fonts/zephyrean.brk.ttf");                    
            fontStylised = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            ge.registerFont(fontStylised);
            
            // Font 2
            fontFile = new File("fonts/expressway-free.regular.ttf");
            fontStandard = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            ge.registerFont(fontStandard);
            
            // This code will print out all the fonts that can be used by the system.
            // Used for debug only
            //for (String fontz:ge.getAvailableFontFamilyNames()) System.out.println(fontz);
        
        }
        catch(FontFormatException | IOException e) {
            
            debug.console(2,"ENGINE: Stylised font error: " + e + ".");
            
        }
        
        debug.console(3,"ENGINE: Engine started.");
    
    }
    
    // The main engine loop
    public void run() {
        
        // Begin recording loop times
        LOOP_LAST = System.nanoTime();
        
        while(RUNNING) {
            
            // Calc how long the last loop took to execute
            // Calculate how long the last loop took to complete
            LOOP_START = System.nanoTime();
            LOOP_UPDATE = LOOP_START - LOOP_LAST;
            LOOP_LAST = LOOP_START;
            
            // Update all running timers at once
            // Possible have an ArrayList of timers running
            
            // Update the total uptime of the running engine
            // Convert it to double for readability
            ENGINE_UPTIME += (double)LOOP_UPDATE / 1000000000;
            
            // Update any running timers
            timersUpdate((double)LOOP_UPDATE / 1000000000);
            
            // Calculate fps
            fpsCalc(LOOP_UPDATE);
            
            debug.console(4,"ENGINE: " + LOOP_COUNT + " loops, " + FPS_CURRENT + " FPS");
            
            
            // Execute main engine code
            debug.console(4,"ENGINE: Running...");
            
            // Initiate first startup, but only after a second, which allows everything to be properly setup
            if (STARTUP && ENGINE_UPTIME > 1.0) firstRun();
            
            // Process the running phase
            if (runningPhase != null) runningPhase.execute();
            
            
            
            // Repaint the canvas if the Canvas is not null
            if(canvas != null) canvas.repaint();
            else debug.console(2,"WARNING: Cannot draw canvas; it has not been initialised yet.");
            
            // Calc the amount of time the engine needs to sleep for to maintain FPS_DESIRED
            // Or simply start the next loop if it took too long to execute
            long LOOP_TIME = System.nanoTime() - LOOP_START;
            long SLEEP_TIME = 0;
                        
            if(LOOP_TIME < DESIRED_SLEEP_TIME) {
                   
                try {
                    
                    // calculates the amount of time the engine needs to sleep for in milliseconds
                    SLEEP_TIME = (DESIRED_SLEEP_TIME - LOOP_TIME) / 1000000;
                    
                    Thread.sleep(SLEEP_TIME);
                } 
                catch (InterruptedException ie) {
                    System.out.println(ie);
                }
            
            }
            
            // Increment loop count
            LOOP_COUNT++;
            
        }
        
    }
    
    // Code to run for initial engine setup
    public void firstRun() {
        
        // Setup a the running phase.
        // In this case run the default phase
        
        runningPhase = new Phase(this);
        
        STARTUP = false;
        
    }
    
    // FPS calculator
    private void fpsCalc(long iLOOP_UPDATE) {
        
        long TIME = 0;
        
        // Add the previous frame run time to the array
        fps.add(iLOOP_UPDATE);
        
        // Calc the total run time of all the frames in the array
        for(int x = 0; x < fps.size(); x++) {
            
            TIME += (long)fps.get(x);
            
        }
        
        // Calc the average frames
        long FRAME_AVG = TIME / fps.size();
        
        // Convert to seconds to get true FPS
        FPS_CURRENT = 1000000000 / FRAME_AVG;
        
        // Remove any frames older than a second
        //  This makes sure that the value we get is only based on the last second
        if(TIME >= 1000000000) {
            
            fps.remove(0);
            
        }
        
    }
    
    public double getFPS() {
        
        return FPS_CURRENT;
        
    }
    
    public void timersUpdate(double iLOOP_UPDATE) {
        
        // Convert to a double for ease of use
        //iLOOP_UPDATE = (double)LOOP_UPDATE / 1000000000;
        
        // dont do anything unless
        if (TIMER_LIST == null) return;
        
        for (int x = 0; x < TIMER_LIST.size(); x++) {
        
            GenericTimer timer = (GenericTimer)TIMER_LIST.get(x);
        
            if (timer.isRunning()) timer.updateTime(iLOOP_UPDATE);
        
        }
        
    }
    
    // Add a timer to the list of running timers
    public void addTimer(Object iobject) {
        
        TIMER_LIST.add(iobject);
        
        return;
        
    }
    
    // Removes a timer based on its index
    public void removeTimer(int index) {
        
        TIMER_LIST.remove(index);
        TIMER_LIST.trimToSize();
        
    }
    
    // Returns the position of the timer based on its UID
    public int getTimerIndex(int iTIMER_UID) {
        
        for (int x = 0; x < TIMER_LIST.size(); x++) {
        
            GenericTimer timer = (GenericTimer)TIMER_LIST.get(x);
        
            if (timer.getTimerUID() == iTIMER_UID) return x;
        
        }
        
        // if the object doesnt exist, return a huge number
        // not sure to get around this in a nicer fashion
        // will deal with it later
        return 999999999;
        
    }
    
    // Return the total running time in a simple way X.XX
    // NOT FOR ACCURATE CALCULATIONS
    public double getUpTimeSimple() {
        
        double UPTIME_SIMPLE = ENGINE_UPTIME;
        
        UPTIME_SIMPLE = Math.round(UPTIME_SIMPLE * 100);
        UPTIME_SIMPLE = UPTIME_SIMPLE / 100;
        
        return UPTIME_SIMPLE;
        
    }
    
    // Return the total running time
    public double getUpTime() {
        
        return ENGINE_UPTIME;
        
    }
    
    // Add an object to the list of game objects
    public void addObject(Object iobject) {
        
        OBJECT_LIST.add(iobject);
        
        return;
        
    }
    
    // Removes an object based on its index
    public void removeObject(int index) {
        
        OBJECT_LIST.remove(index);
        OBJECT_LIST.trimToSize();
        
    }
    
    // Increase the unique object ID number then return it.
    public int nextObjectUID() {
        
        OBJECT_UID++;
        
        return OBJECT_UID;
        
    }
    
    // Rturn the list of game objects
    public ArrayList getObjectList() {
        
        return OBJECT_LIST;
        
    }
    
    // Returns the position in the Game Object list via its OID
    public int getObjectIndex(int iOBJECT_UID) {
        
        for (int x = 0; x < OBJECT_LIST.size(); x++) {
        
            GameObject object = (GameObject)OBJECT_LIST.get(x);
        
            if (object.getObjectID() == iOBJECT_UID) return x;
        
        }
        
        // if the object doesnt exist, return a huge number
        // not sure to get around this in a nicer fashion
        // will deal with it later
        return 999999999;
        
    }
    
    // Returns the position in the Game Object list via its NAME
    public int getObjectIndex(String n) {
        
        for (int x = 0; x < OBJECT_LIST.size(); x++) {
        
            GameObject object = (GameObject)OBJECT_LIST.get(x);
        
            if (object.getName().equals(n)) return x;
        
        }
        
        // if the object doesnt exist, return a huge number
        // not sure to get around this in a nicer fashion
        // will deal with it later
        return 999999999;
        
    }
    
    public void setMainFrame(MainFrame imainframe) {
        
        mainframe = imainframe;
        
    }
    
    public void setCanvas(ocelot.Canvas icanvas) {
        
        canvas = icanvas;
        
    }

    public Font getStylisedFont() {
        
        //if (ifont.equals("stylised")) return fontStylised;
        //if (ifont.equals("standard")) return fontStandard;
        if (fontStylised == null) debug.console(3,"ENGINE: Styleised font is null.");
        
        // always return the standard font unless a correct match is made
        return fontStylised;
        
    }

    public Debug getDebug() {
        
        return debug;
        
    }
    public ocelot.Canvas getCanvas() {
        
        return canvas;
        
    }
    public MainFrame getMainFrame() {
        
        return mainframe;
        
    }
}
