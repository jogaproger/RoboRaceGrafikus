/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Patricia
 */
public class KeyStates implements KeyListener {

	/**
	 * Maximum nagysagu keycode, amit meg tarolunk
	 */
	static final int MAXKC = 1024;
	
	/**
	 * Billentyuk allapotai virtual keyode alapjan
	 */
    private boolean states[] = new boolean[MAXKC+1];
    
    public enum Change{ NONE, PUSHED, RELEASED };

    /**
	 * Billentyuk valtozasai virtual keyode alapjan
	 */
    private Change changes[][] = new Change[2][MAXKC+1];
    /**
     * Ket buffer van:
     *  active - amibe a valtozasokat mentjuk
     *  a masik - amit a kliens olvas
     */
    private int active = 0;
 
    /**
     * keyTyped event megvalositasa
     * @param e Event 
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * keyPressed event megvalositasa
     * @param e Event 
     */
    @Override
    public void keyPressed(KeyEvent e) {
    	synchronized(this){
	    	int kc = e.getKeyCode();
	    	if( 0 <= kc && kc < MAXKC ){
	    		states[kc] = true;
	    		changes[active][kc] = Change.PUSHED;
	    	}
    	}
    }

    /**
     * keyReleased event megvalositasa
     * @param e Event 
     */
    @Override
    public void keyReleased(KeyEvent e) {
    	synchronized(this){
	    	int kc = e.getKeyCode();
	    	if( 0 <= kc && kc < MAXKC ){
	    		states[kc] = false;
				changes[active][kc] = Change.RELEASED;
		    }
    	}
    }
    
    /**
     * Lekeredezi az adott billentyu allapotat
     * @param kc A billentyu kodja ( KeyEvent.VK_ ... )
     * @return
     */
    public boolean getKeyState(int kc ){
    	if( 0 <= kc && kc < MAXKC )
    		return states[kc];
    	return false;
    }
    
    /**
     * Valtozasok lekerdezese az utolso pullChanges ota
     * @param kc A billentyu kodja ( KeyEvent.VK_ ... )
     * @return
     */
    public Change[] pullChanges(){
    	synchronized(this){
	    	active = 1-active;
	    	for( int i = 0 ; i < MAXKC ; i++ )
	    		changes[active][i] = Change.NONE;
    	}
    	return changes[1-active];
    }
    
}
