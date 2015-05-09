package gfx;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.WindowEvent;

public class Window extends JFrame {

	/** Generated serial version UID */
	private static final long serialVersionUID = 5760767427394988819L;
	
	private JPanel panel;
	private BufferedImage backbuffer;
	private KeyStates keyStates;

	public Window(int width, int height){

    	setTitle("RoboRace");

    	setResizable(false);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	
    	panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));
		add(panel);
		this.addKeyListener( keyStates = new KeyStates() );
		pack();
		
		backbuffer = new BufferedImage(
				width, height,
				BufferedImage.TYPE_INT_RGB);
		
	}
	
	public KeyStates getKeyStates(){
		return keyStates;		
	}
	
	public Graphics getBackbufferGraphics(){
		if( backbuffer == null )
			throw new NullPointerException("backbuffer");
		return backbuffer.getGraphics();
	}
	
	public void swapBuffers(){
		panel.getGraphics().drawImage(backbuffer, 0, 0, null);
	}

	public void close() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));		
	}
}
