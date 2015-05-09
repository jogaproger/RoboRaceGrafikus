package main;

import java.awt.event.KeyEvent;

import modell.Jatek;
import modell.Ranglista;
import gfx.KeyStates;
import gfx.Window;


public class Main {

    /**
     * Ennyi tick van egy masodperc alatt nem valtoztathato
     */
    static final int tickPerSecond = 30;

    Window window;
    int menupont;
    static final int MENUMERET = 3;
    

    public static int getTicksPerSecond() {
        return tickPerSecond;
    }
    
    
    Main(){
    	window = new Window(640, 480);
    	menupont = 0;
    }
    
    void draw(){
    	
    	
    	
    	window.swapBuffers();
    }

    void run(){

    	boolean exit = false;
    	KeyStates ks = window.getKeyStates();

    	while(!exit){
    		draw();
    		KeyStates.Change[] changes = ks.pullChanges();
    		
    		if( changes[KeyEvent.VK_UP] == KeyStates.Change.PUSHED )
    			menupont += MENUMERET-1;
    		if( changes[KeyEvent.VK_DOWN] == KeyStates.Change.PUSHED )
    			menupont ++;
    		menupont %= MENUMERET;
    		
    		if( changes[KeyEvent.VK_ENTER] == KeyStates.Change.PUSHED )
    		{
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
    			ks.pullChanges();
    		}
    		
    	}
    	
    }
    
    void ranglista(){
    	(new Ranglista()).megjelenit(window);
    }
   
    void jatek(){
    	String palya;
    	String[] nevek;
    	
    	// TODO felugro ablak, amin ki lehet valasztani a jatekosok nevet meg a palyat
    	// valahogy visszater a jatekosok nevevel meg a palyaval
    	// es azt itt meg kene varni
    	
    	Jatek j = new Jatek(palya, nevek);
    	j.futtat(120);
    }
    
    public static void main(String[] args) {
    	
    	new Main().run();
    }

}
