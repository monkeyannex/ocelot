package ocelot.object;

import ocelot.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.ArrayList;

public class StarField extends GameObject {
    
    private int starsMin = 500;
    private int starsMax = 1000;
    private int starsActual;
    // Used for randomly generating a number of stars
    private int starsRandom = starsMax = starsMin;
    
    private int sizeMax = 3;
    
    // The array that stores details about all the stars
    // 0 & 1 = x & y
    // 2 = size
    // 3 - alpha
    private double[][] starData;
    
    private ArrayList stars;
    private Random random;   
    
    public StarField(int iOBJECT_ID, String iNAME, String iTYPE, Engine iengine) {
        
        super(iOBJECT_ID, iengine);
        
        debug.console(3, iNAME + "(" + OBJECT_ID + "): Creating object...");
        
        NAME = iNAME;
        TYPE = iTYPE;
        
        VISIBLE = true;
        SOLID = false;
        
        random = new Random();
        stars = new ArrayList();
        
        starsActual = random.nextInt(starsRandom) + starsMin;
        
        // Initialise the array
        starData = new double[starsActual][4];
        
        // Generate the random attributes for each star
        for (int x = 0; x < starsActual; x++) {
            
            // set x and y position
            starData[x][0] = random.nextInt(mainframe.getWidth() + 1) + random.nextDouble();
            starData[x][1] = random.nextInt(mainframe.getHeight() + 1) + random.nextDouble();
            // set size
            starData[x][2] = random.nextInt(sizeMax) + random.nextDouble();
            // set alpha
            starData[x][3] = random.nextDouble();
            
            stars.add(new Rectangle2D.Double(starData[x][0], starData[x][1], starData[x][2], starData[x][2]));
            
        }
        
        debug.console(3, NAME + "(" + OBJECT_ID + "): Object created.");
        
    }
    
    public void draw(Graphics2D g) {
        
        for (int x = 0; x < starsActual; x++) {
            
            Color color = new Color(1f,1f,1f, (float)starData[x][3]);
            g.setColor(color);
            
            g.fill((Rectangle2D.Double)stars.get(x));
            
        }
        
    }
    
}
