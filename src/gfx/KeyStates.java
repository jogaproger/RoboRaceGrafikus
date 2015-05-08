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
    private boolean states[] = new boolean[MAXKC];
 
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
    	int kc = e.getKeyCode();
    	if( 0 <= kc && kc < 256 )
    		states[kc] = true;
    }

    /**
     * keyReleased event megvalositasa
     * @param e Event 
     */
    @Override
    public void keyReleased(KeyEvent e) {
    	int kc = e.getKeyCode();
    	if( 0 <= kc && kc < 256 )
    		states[kc] = false;
    }
    
    /**
     * Lekeredezi az adott billentyu allapotat
     * @param kc A billentyu kodja ( KeyEvent.VK_ ... )
     * @return
     */
    public boolean getKeyState(int kc ){
    	if( 0 <= kc && kc < 256 )
    		return states[kc];
    	return false;
    }
    
}
