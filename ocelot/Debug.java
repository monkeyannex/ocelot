package ocelot;

public class Debug {
	
	Number level;
	//Initialise the log file
	
	//Define level
	//0= lowest level, only fatal error messages
	//1= non-fatal error messages
	//2= warnings that can be ignored
	//3= info, log of classes called etc, event info.
	//4= custom stuff 
	
	public Debug(Number lvl){
		level = lvl;
		//Create log file with datetime appending to it
		//in the /logs folder
		
	}
	
	
	public void log(Number level, String message){
		//if global level >= level
			//print to the log file
			//print object.method=||message
	}
	
	
	public void console(Number level, String message){
		
	}
	
	
	public void screen(String message){
		
	}	
}
