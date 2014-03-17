package ocelot;

public class GenericTimer {
    
    private double TIMER;
    private int TIMER_UID;
    private double LAST_UPDATE;
    
    private boolean RUNNING;
    
    public GenericTimer(int iTIMER_UID) {
        
        TIMER_UID = iTIMER_UID;        
        TIMER = 0;
        RUNNING = true;
        
        LAST_UPDATE = 0;
        
    }
    
    // Return the current time in seconds the timer has been running
    public double getTime() {
        
        return TIMER;
        
    }
    
    // standard updates for the timer
    public void updateTime(double itime) {
        
        if(RUNNING) {
            
            TIMER += itime;
            LAST_UPDATE = itime;
            
        }
        
    }
    
    // Reset the timer back to 0
    public void resetTime() {
        
        TIMER = 0;
        
    }
    
    // Used for added time to, or removing time from the timer
    public void adjustTime(double itime) {
        
        TIMER += itime;
        
    }
    
    // Find out if the timer is running
    public boolean isRunning() {
        
        return RUNNING;
        
    }
    
    // Pause/Unpause the timer
    public void togglePause() {
        
        RUNNING = !RUNNING;
        
    }
    
    public int getTimerUID() {
        
        return TIMER_UID;
        
    }
    
    public double getLastUpdate() {
        
        return LAST_UPDATE;
        
    }
    
}
