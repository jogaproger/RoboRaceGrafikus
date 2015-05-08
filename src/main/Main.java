package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gfx.Window;


public class Main {

    /**
     * Ennyi tick van egy masodperc alatt, jatek kozben nem valtoztathato
     */
    static int tickPerSecond = 60;

    public static int getTicksPerSecond() {
        return tickPerSecond;
    }
    
    Window window;
    int menupont;
    static final int MENUMERET = 3;
    
    
    Main(){
    	window = new Window(640, 480);
    	menupont = 0;
    	
    	
    }
    
    void draw(){
    	
    	
    	window.swapBuffers();
    }

    void run(){

    	boolean exit = false;

    	while(!exit){
    		draw();
    		if( fel )
    			menupont += MENUMERET-1;
    		if( le )
    			menupont ++;
    		menupont %= MENUMERET;
    		
    		if( enter )
    			switch( menupont )
    			{
					case 0: 
						jatek(); 
						break;
					case 1: 
						ranglista(); 
						break;
					default: 
						exit = true; 
						break;
    			}
    		
    	}
    	
    	
    }
    
    void ranglista(){
    	new Ranglista().megjelenit();
    }
   
    void jatek(){
    //	new Jatek...().megjelenit(frame);
    }
    
    public static void main(String[] args) {
    	
    	new Main().run();
    }

}
