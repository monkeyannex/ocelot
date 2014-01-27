package ocelot;

/* IMPORTED CLASSES */
import java.util.ArrayList;
import ocelot.engine.*;

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
    
    // The number of loops the engine has executed
    private long LOOP_COUNT = 0;
    
    /* GLOBAL OBJECTS */
    private Debug debug;
    private Canvas canvas;
    private MainFrame mainframe;    
    private Phase runningPhase;

    // Constructor
    public Engine(Debug idebug) {
    
        // Setup the things the engine needs
        debug = idebug;
        
        debug.console(3,"ENGINE: Starting Engine...");
        
        /* SETUP THE ENGINE */
        
        // Make sure the engine it set to running status
        RUNNING = true;
        
        // Initialise the appropriate ArrayLists
        fps = new ArrayList();    
        
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
            
            // Calculate fps
            fpsCalc(LOOP_UPDATE);
            
            debug.console(4,"ENGINE: " + LOOP_COUNT + " loops, " + FPS_CURRENT + " FPS");
            
            
            // Execute main engine code
            debug.console(4,"ENGINE: Running...");
            
            // Initiate first startup
            if (STARTUP) firstRun();
            
            
            
            
            
            
            
            
            
            
            
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
    
    public void setMainFrame(MainFrame imainframe) {
        
        mainframe = imainframe;
        
    }
    
    public void setCanvas(Canvas icanvas) {
        
        canvas = icanvas;
        
    }

}
