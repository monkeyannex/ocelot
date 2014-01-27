import ocelot.*;

public class Start {
    
    private static int DEBUG_LVL = 3;
	
	public static void main(String[] args){
		
		//Read config into memory
			//If file exists, load into memory
			//else default file
		
		//Setup debug
			//accept debug level from command line
            
        Debug debug = new Debug(DEBUG_LVL);
		
		//Create Engine for a new thread
        Engine engine = new Engine(debug);
        Thread eThread = new Thread(engine);
        eThread.start();
		
		//Create drawing frame
		MainFrame mainframe = new MainFrame(debug, engine);
		
	}
}
